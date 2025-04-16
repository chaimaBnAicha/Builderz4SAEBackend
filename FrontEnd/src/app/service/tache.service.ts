import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, of } from 'rxjs';
import { tap, catchError, map, delay } from 'rxjs/operators';



declare const pdfMake: any;



const BASIC_URL = "http://localhost:8081/api"; // Backend URL
export interface Tache {
  id: number;
  titre: string;
  nom: string;
  description: string;
  statut: 'A_FAIRE' | 'EN_COURS' | 'TERMINEE';
  priorite: 'BASSE' | 'MOYENNE' | 'HAUTE';
  dateDebut: Date;
  dateFin: Date;
  projet: {
    id: number;
    nom?: string;
  };
  responsable: {
    id: number;
    email?: string;
  };
}

interface TableLayoutFunction {
  (i: number, node: any): number | string;
}

interface FooterFunction {
  (currentPage: number, pageCount: number): {
    text: string;
    alignment: string;
    margin: number[];
  };
}

interface TacheStats {
  totalTaches: number;
  parStatut: { [key: string]: number };
  parPriorite: { [key: string]: number };
  tauxCompletion: number;
  delaiMoyen: number;
  performanceParJour: { date: string; completed: number }[];
}

interface TaskAnalysis {
  steps: string[];
  complexity: 'Simple' | 'Moyenne' | 'Complexe';
  estimatedDuration: number; // en heures
  keywords: string[];
  suggestions: string[];
}

@Injectable({
  providedIn: 'root'
})
export class TacheService {
  private refreshNeeded = new Subject<void>();

  get refreshNeeded$() {
    return this.refreshNeeded.asObservable();
  }

  constructor(private http: HttpClient) {}

  postTache(tache: any): Observable<any> {
    return this.http.post(`${BASIC_URL}/TachePost`, tache);
  }

  acceptTask(taskId: number): Observable<any> {
    return this.http.put(`${BASIC_URL}/tasks/${taskId}/accept`, {}); // Ajout d'un body vide
  }
  changeStatus(id: number): Observable<any> {
    return this.http.put(`${BASIC_URL}/api/taches/${id}/change-status`, {});
  }
  

  declineTask(taskId: number): Observable<any> {
    return this.http.put(`${BASIC_URL}/tasks/${taskId}/decline`, {}); // Ajout d'un body vide
  }

  getAllTache(): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${BASIC_URL}/Taches`).pipe(
      tap(() => console.log('Données rafraîchies')),
      catchError(error => {
        console.error('Erreur lors du chargement des tâches:', error);
        throw error;
      })
    );
  }

  getTacheById(id: number): Observable<any> {
    return this.http.get(`${BASIC_URL}/Tache/${id}`);
  }

  updateTache(id: number, tache: any): Observable<any> {
    return this.http.put(`${BASIC_URL}/Taches/${id}`, tache);
  }

  deleteTache(id: number): Observable<any> {
    return this.http.delete(`${BASIC_URL}/TacheDelete/${id}`);
  }


  getTachesByStatut(statut: string): Observable<any[]> {
    return this.http.get<any[]>(`${BASIC_URL}/taches/statut/${statut}`);
  }

  // Mettre à jour le statut d'une tâche
  /*updateStatut(id: number, statut: string): Observable<any> {
    return this.http.put(`${BASIC_URL}/tasks/${id}/statut`, { statut }); // Correction de l'URL
  }*/
 // Remplacer updateTaches par :
updateStatut(id: number, statut: string): Observable<Tache> {
  return this.http.put<Tache>(`${BASIC_URL}/api/taches/${id}/statut`, { statut });
}

// Supprimer la méthode updateTaches
  updateTaches(statut: string, taches: any[]): Observable<any> {
    // Ici tu envoies les tâches de chaque statut à la base de données
    return this.http.put(`${BASIC_URL}/Taches/statut/${statut}`, taches);
  }
  
  respondToTask(taskId: number, response: 'oui' | 'non'): Observable<any> {
    console.log(`Envoi de la réponse ${response} pour la tâche ${taskId}`);
    return this.http.put(`${BASIC_URL}/tasks/${taskId}/respond/${response}`, {}).pipe(
      tap(response => {
        console.log('Réponse du serveur:', response);
        this.refreshNeeded.next();
      }),
      catchError(error => {
        console.error('Erreur lors de la réponse à la tâche:', error);
        throw error;
      })
    );
  }

  // Nouvelle méthode pour marquer une tâche comme terminée
  markTaskAsDone(taskId: number): Observable<Tache> {
    const url = `${BASIC_URL}/tasks/${taskId}/done`;
    console.log('Envoi de la requête vers:', url);

    return this.http.put<Tache>(url, {}).pipe(
      tap(response => {
        console.log('Réponse du serveur:', response);
        this.triggerRefresh();
      }),
      catchError(error => {
        console.error('Erreur lors de la requête:', error);
        throw error;
      })
    );
  }

  // Ajouter cette méthode pour forcer le rafraîchissement
  refreshTasks(): Observable<Tache[]> {
    return this.getAllTache().pipe(
      tap(() => console.log('Données rafraîchies')),
      catchError(error => {
        console.error('Erreur lors du rafraîchissement des tâches:', error);
        throw error;
      }),
      map((data: any[]) => data as Tache[])
    );
  }

  public triggerRefresh() {
    this.refreshNeeded.next();
  }



  searchTaches(query: string): Observable<Tache[]> {
    if (!query.trim()) {
        return this.getAllTache();
    }
    return this.http.get<Tache[]>(`${BASIC_URL}/taches/search`, {
        params: { query: query.trim() }
    }).pipe(
        map(taches => this.sortSearchResults(taches, query)),
        catchError(error => {
            console.error('Erreur lors de la recherche:', error);
            return of([]);
        })
    );
  }

  private sortSearchResults(taches: Tache[], query: string): Tache[] {
    const queryLower = query.toLowerCase();
    return taches.sort((a, b) => {
        // Priorité plus élevée pour les correspondances exactes dans le titre
        const titleMatchA = a.nom.toLowerCase().includes(queryLower);
        const titleMatchB = b.nom.toLowerCase().includes(queryLower);
        
        if (titleMatchA && !titleMatchB) return -1;
        if (!titleMatchA && titleMatchB) return 1;
        
        // Ensuite, priorité pour les correspondances dans la description
        const descMatchA = a.description.toLowerCase().includes(queryLower);
        const descMatchB = b.description.toLowerCase().includes(queryLower);
        
        if (descMatchA && !descMatchB) return -1;
        if (!descMatchA && descMatchB) return 1;
        
        return 0;
    });
  }

  generatePDF(tache: Tache) {
    const statusColors = {
      'A_FAIRE': '#ff4444',
      'EN_COURS': '#ffbb33',
      'TERMINEE': '#00C851'
    };

    const priorityColors = {
      'HAUTE': '#ff4444',
      'MOYENNE': '#ffbb33',
      'BASSE': '#00C851'
    };

    const docDefinition = {
      pageSize: 'A4',
      pageMargins: [40, 60, 40, 60],
      header: {
        text: 'Détails de la Tâche',
        alignment: 'center',
        margin: [0, 20, 0, 20],
        fontSize: 25,
        bold: true,
        color: '#2196F3'
      },
      content: [
        {
          table: {
            headerRows: 0,
            widths: ['*', '*'],
            body: [
              [
                {
                  text: [
                    { text: 'ID: ', bold: true },
                    { text: tache.id.toString() }
                  ]
                },
                {
                  text: [
                    { text: 'Statut: ', bold: true },
                    { 
                      text: tache.statut,
                      color: statusColors[tache.statut]
                    }
                  ]
                }
              ],
              [
                {
                  text: [
                    { text: 'Titre: ', bold: true },
                    { text: tache.titre || tache.nom }
                  ],
                  colSpan: 2
                },
                {}
              ],
              [
                {
                  text: [
                    { text: 'Description: ', bold: true },
                    { text: tache.description }
                  ],
                  colSpan: 2
                },
                {}
              ],
              [
                {
                  text: [
                    { text: 'Priorité: ', bold: true },
                    { 
                      text: tache.priorite,
                      color: priorityColors[tache.priorite]
                    }
                  ],
                  colSpan: 2
                },
                {}
              ],
              [
                {
                  text: [
                    { text: 'Date de début: ', bold: true },
                    { text: new Date(tache.dateDebut).toLocaleDateString() }
                  ]
                },
                {
                  text: [
                    { text: 'Date de fin: ', bold: true },
                    { text: new Date(tache.dateFin).toLocaleDateString() }
                  ]
                }
              ]
            ]
          },
          layout: {
            hLineWidth: ((i: number, node: any): number => 0.5) as TableLayoutFunction,
            vLineWidth: ((i: number, node: any): number => 0.5) as TableLayoutFunction,
            hLineColor: ((i: number, node: any): string => '#aaa') as TableLayoutFunction,
            vLineColor: ((i: number, node: any): string => '#aaa') as TableLayoutFunction,
            paddingLeft: ((i: number, node: any): number => 10) as TableLayoutFunction,
            paddingRight: ((i: number, node: any): number => 10) as TableLayoutFunction,
            paddingTop: ((i: number, node: any): number => 10) as TableLayoutFunction,
            paddingBottom: ((i: number, node: any): number => 10) as TableLayoutFunction
          }
        },
        {
          text: 'Informations complémentaires',
          style: 'subheader',
          margin: [0, 20, 0, 10]
        },
        {
          table: {
            headerRows: 0,
            widths: ['*', '*'],
            body: [
              [
                {
                  text: [
                    { text: 'Projet ID: ', bold: true },
                    { text: tache.projet?.id.toString() }
                  ]
                },
                {
                  text: [
                    { text: 'Responsable ID: ', bold: true },
                    { text: tache.responsable?.id.toString() }
                  ]
                }
              ]
            ]
          },
          layout: 'lightHorizontalLines'
        }
      ],
      styles: {
        header: {
          fontSize: 22,
          bold: true,
          color: '#2196F3',
          margin: [0, 0, 0, 20]
        },
        subheader: {
          fontSize: 16,
          bold: true,
          color: '#666666',
          margin: [0, 10, 0, 5]
        }
      },
      defaultStyle: {
        fontSize: 12,
        lineHeight: 1.5
      },
      footer: ((currentPage: number, pageCount: number) => {
        return {
          text: `Page ${currentPage.toString()} sur ${pageCount}`,
          alignment: 'center',
          margin: [0, 30, 0, 0]
        };
      }) as FooterFunction
    };

    try {
      const pdfDocGenerator = pdfMake.createPdf(docDefinition);
      pdfDocGenerator.download(`Tache_${tache.id}_${new Date().toISOString().split('T')[0]}.pdf`);
    } catch (error) {
      console.error('Erreur lors de la génération du PDF:', error);
    }
  }

  // Méthode pour calculer les statistiques
  getTaskStatistics(): Observable<TacheStats> {
    return this.getAllTache().pipe(
      map(taches => {
        const stats: TacheStats = {
          totalTaches: taches.length,
          parStatut: {
            'A_FAIRE': 0,
            'EN_COURS': 0,
            'TERMINEE': 0
          },
          parPriorite: {
            'BASSE': 0,
            'MOYENNE': 0,
            'HAUTE': 0
          },
          tauxCompletion: 0,
          delaiMoyen: 0,
          performanceParJour: []
        };

        // Calcul des statistiques
        taches.forEach(tache => {
          // Comptage par statut
          stats.parStatut[tache.statut]++;
          
          // Comptage par priorité
          stats.parPriorite[tache.priorite]++;

          // Calcul du délai moyen pour les tâches terminées
          if (tache.statut === 'TERMINEE') {
            const debut = new Date(tache.dateDebut).getTime();
            const fin = new Date(tache.dateFin).getTime();
            stats.delaiMoyen += (fin - debut) / (1000 * 60 * 60 * 24); // en jours
          }
        });

        // Calcul du taux de complétion
        stats.tauxCompletion = (stats.parStatut['TERMINEE'] / stats.totalTaches) * 100;
        
        // Calcul du délai moyen final
        if (stats.parStatut['TERMINEE'] > 0) {
          stats.delaiMoyen /= stats.parStatut['TERMINEE'];
        }

        // Analyse de la performance par jour
        const performanceMap = new Map<string, number>();
        taches
          .filter(t => t.statut === 'TERMINEE')
          .forEach(t => {
            const dateStr = new Date(t.dateFin).toISOString().split('T')[0];
            performanceMap.set(dateStr, (performanceMap.get(dateStr) || 0) + 1);
          });

        stats.performanceParJour = Array.from(performanceMap.entries())
          .map(([date, completed]) => ({ date, completed }))
          .sort((a, b) => a.date.localeCompare(b.date));

        return stats;
      })
    );
  }

  // Méthode pour prédire la date de fin d'une tâche
  predictTaskCompletion(tache: Tache): Observable<Date> {
    return this.getTaskStatistics().pipe(
      map(stats => {
        const delaiMoyen = stats.delaiMoyen;
        const priorityFactor = {
          'HAUTE': 0.8,
          'MOYENNE': 1,
          'BASSE': 1.2
        };
        
        const dateDebut = new Date(tache.dateDebut);
        const delaiPredit = delaiMoyen * priorityFactor[tache.priorite];
        
        const dateFinPredite = new Date(dateDebut);
        dateFinPredite.setDate(dateFinPredite.getDate() + Math.round(delaiPredit));
        
        return dateFinPredite;
      })
    );
  }

  // Méthode pour suggérer une priorité basée sur l'analyse
  suggestTaskPriority(description: string): Observable<'HAUTE' | 'MOYENNE' | 'BASSE'> {
    const urgentKeywords = ['urgent', 'immédiat', 'critique', 'important'];
    const lowPriorityKeywords = ['optionnel', 'plus tard', 'quand possible'];
    
    return this.getAllTache().pipe(
      map(taches => {
        const descriptionLower = description.toLowerCase();
        
        // Vérification des mots-clés d'urgence
        if (urgentKeywords.some(keyword => descriptionLower.includes(keyword))) {
          return 'HAUTE';
        }
        
        // Vérification des mots-clés de basse priorité
        if (lowPriorityKeywords.some(keyword => descriptionLower.includes(keyword))) {
          return 'BASSE';
        }
        
        // Analyse des tâches similaires
        const similarTasks = taches.filter(t => 
          this.calculateSimilarity(t.description, description) > 0.5
        );
        
        if (similarTasks.length > 0) {
          const priorityCounts = similarTasks.reduce((acc, task) => {
            acc[task.priorite] = (acc[task.priorite] || 0) + 1;
            return acc;
          }, {} as Record<string, number>);
          
          const maxPriority = Object.entries(priorityCounts)
            .reduce((a, b) => a[1] > b[1] ? a : b)[0] as 'HAUTE' | 'MOYENNE' | 'BASSE';
          
          return maxPriority;
        }
        
        return 'MOYENNE';
      })
    );
  }

  // Méthode utilitaire pour calculer la similarité entre deux textes
  private calculateSimilarity(text1: string, text2: string): number {
    const words1 = text1.toLowerCase().split(/\s+/);
    const words2 = text2.toLowerCase().split(/\s+/);
    
    const intersection = words1.filter(word => words2.includes(word));
    const union = new Set([...words1, ...words2]);
    
    return intersection.length / union.size;
  }

  analyzeTaskDescription(description: string): TaskAnalysis {
    const lines = description.split(/[.\n]/); // Sépare par points ou retours à la ligne
    const stepIndicators = [
      'étape', 'step', 'puis', 'ensuite', 'après', 'finally', 
      'premièrement', 'deuxièmement', 'enfin', 'dabord', 'd\'abord',
      '1.', '2.', '3.', '-', '•'
    ];

    const technicalKeywords = [
      'développer', 'coder', 'implémenter', 'tester', 'débugger',
      'configurer', 'installer', 'déployer', 'optimiser', 'analyser'
    ];

    // Extraction des étapes
    const steps = lines
      .map(line => line.trim())
      .filter(line => line.length > 0)
      .filter(line => 
        stepIndicators.some(indicator => 
          line.toLowerCase().includes(indicator) ||
          /^\d+\./.test(line) || // Lignes commençant par un nombre suivi d'un point
          /^[-•]/.test(line)     // Lignes commençant par - ou •
        )
      );

    // Analyse de la complexité
    const complexityScore = this.calculateComplexityScore(description);
    const complexity: 'Simple' | 'Moyenne' | 'Complexe' = 
      complexityScore < 3 ? 'Simple' :
      complexityScore < 6 ? 'Moyenne' : 'Complexe';

    // Extraction des mots-clés techniques
    const keywords = technicalKeywords
      .filter(keyword => description.toLowerCase().includes(keyword));

    // Estimation de la durée basée sur la complexité et le nombre d'étapes
    const estimatedDuration = this.estimateTaskDuration(steps.length, complexity);

    // Génération de suggestions
    const suggestions = this.generateTaskSuggestions(steps, complexity, keywords);

    return {
      steps,
      complexity,
      estimatedDuration,
      keywords,
      suggestions
    };
  }

  private calculateComplexityScore(description: string): number {
    let score = 0;
    
    // Facteurs de complexité
    const complexityFactors = {
      length: description.length > 500 ? 2 : description.length > 200 ? 1 : 0,
      technicalTerms: [
        'api', 'database', 'integration', 'security', 'performance',
        'optimization', 'architecture', 'infrastructure', 'deployment'
      ],
      dependencies: [
        'dépend', 'require', 'besoin', 'nécessite', 'après que',
        'condition', 'si et seulement si', 'uniquement si'
      ],
      urgencyTerms: [
        'urgent', 'critique', 'important', 'prioritaire', 'immédiat'
      ]
    };

    // Analyse des termes techniques
    score += complexityFactors.technicalTerms
      .filter(term => description.toLowerCase().includes(term))
      .length;

    // Analyse des dépendances
    score += complexityFactors.dependencies
      .filter(term => description.toLowerCase().includes(term))
      .length * 1.5;

    // Analyse de l'urgence
    score += complexityFactors.urgencyTerms
      .filter(term => description.toLowerCase().includes(term))
      .length;

    // Ajout du score de longueur
    score += complexityFactors.length;

    return score;
  }

  private estimateTaskDuration(stepsCount: number, complexity: 'Simple' | 'Moyenne' | 'Complexe'): number {
    const baseTimePerStep = {
      'Simple': 1,    // 1 heure par étape
      'Moyenne': 2,   // 2 heures par étape
      'Complexe': 4   // 4 heures par étape
    };

    return stepsCount * baseTimePerStep[complexity];
  }

  private generateTaskSuggestions(
    steps: string[], 
    complexity: 'Simple' | 'Moyenne' | 'Complexe',
    keywords: string[]
  ): string[] {
    const suggestions: string[] = [];

    // Suggestions basées sur le nombre d'étapes
    if (steps.length === 0) {
      suggestions.push("📝 Considérez décomposer cette tâche en étapes distinctes pour une meilleure organisation");
    } else if (steps.length > 7) {
      suggestions.push("⚠️ Cette tâche contient beaucoup d'étapes. Envisagez de la diviser en sous-tâches");
    }

    // Suggestions basées sur la complexité
    if (complexity === 'Complexe') {
      suggestions.push("🔍 Tâche complexe détectée - Prévoyez des points de contrôle réguliers");
      suggestions.push("👥 Considérez d'impliquer plusieurs membres de l'équipe");
    }

    // Suggestions basées sur les mots-clés techniques
    if (keywords.includes('tester')) {
      suggestions.push("✅ N'oubliez pas d'inclure des tests unitaires");
    }
    if (keywords.includes('déployer')) {
      suggestions.push("🚀 Préparez une checklist de déploiement");
    }

    return suggestions;
  }
}
