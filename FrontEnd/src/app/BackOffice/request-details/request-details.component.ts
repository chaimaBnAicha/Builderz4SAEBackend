import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RequestService } from 'src/app/service/request.service';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-request-details',
  templateUrl: './request-details.component.html',
  styleUrls: ['./request-details.component.css']
})
export class RequestDetailsComponent implements OnInit {
  requestDetails: any;
  userInfo: any;
  map!: google.maps.Map;
  markers: any[] = []; // Stocker les marqueurs de la carte

  @ViewChild('pdfContent', { static: false }) pdfContent!: ElementRef; // Référence à l'élément HTML

  constructor(
    private route: ActivatedRoute,
    private requestService: RequestService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id_projet = this.route.snapshot.paramMap.get('id_projet');
    if (id_projet) {
      this.loadRequestDetails(Number(id_projet));
      this.loadUserInfo();
    }
  }

  loadRequestDetails(id_projet: number): void {
    this.requestService.getRequestDetails(id_projet).subscribe((data) => {
      this.requestDetails = data;
      if (this.requestDetails?.geographic_location) {
        this.loadMap(this.requestDetails.geographic_location);
      }
    });
  }

  loadUserInfo(): void {
    this.requestService.getUserInfo().subscribe((data) => {
      this.userInfo = data;
    });
  }

  loadMap(location: string): void {
    if (!location) {
      console.error('La localisation est vide');
      return;
    }

    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({ address: location }, (results, status) => {
      if (status === 'OK' && results[0]) {
        const mapOptions: google.maps.MapOptions = {
          center: results[0].geometry.location,
          zoom: 15
        };

        const mapElement = document.getElementById('map');
        if (mapElement) {
          this.map = new google.maps.Map(mapElement, mapOptions);

          const marker = new google.maps.Marker({
            position: results[0].geometry.location,
            map: this.map,
            title: location,
            icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
          });

          this.markers.push({ id_projet: this.requestDetails.id_projet, marker });
        } else {
          console.error("L'élément de la carte est introuvable");
        }
      } else {
        console.error('La géolocalisation a échoué : ' + status);
      }
    });
  }

  updateStatus(id_projet: number, status: string): void {
    const updateFn = status === 'Approved' ? this.requestService.approveRequest : this.requestService.rejectRequest;
    updateFn.call(this.requestService, id_projet).subscribe(() => {
      this.loadRequestDetails(id_projet);
      this.updateMarkerColor(id_projet, status === 'Approved' ? 'green' : 'red');
    });
  }

  updateMarkerColor(id_projet: number, color: string): void {
    const markerData = this.markers.find(m => m.id_projet === id_projet);
    if (markerData) {
      markerData.marker.setIcon(`http://maps.google.com/mapfiles/ms/icons/${color}-dot.png`);
    }
  }

  goBack(): void {
    this.router.navigate(['/admin/project-manager']);
  }

  downloadPDF(): void {
    const element = this.pdfContent.nativeElement;
  
    // Masquer les boutons avant de générer le PDF
    const buttons = document.querySelectorAll('button');
    buttons.forEach((button) => {
      button.style.display = 'none'; // Utilisez display: none pour masquer les boutons
    });
  
    // Masquer la carte avant de générer le PDF
    const mapElement = document.getElementById('map');
    if (mapElement) {
      mapElement.style.display = 'none';
    }
  
    html2canvas(element).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4');
      const imgWidth = 190;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;
      
      // Ajouter l'image du contenu
      pdf.addImage(imgData, 'PNG', 10, 10, imgWidth, imgHeight);
  
      // Ajouter le mot "Signature" en bas à droite
      const pageHeight = pdf.internal.pageSize.height;
      const margin = 10;
      const signatureText = "Signature";
      pdf.text(signatureText, pdf.internal.pageSize.width - margin - pdf.getTextWidth(signatureText), pageHeight - margin);
  
      // Enregistrer le PDF
      pdf.save('request-details.pdf');
  
      // Réafficher les éléments masqués
      buttons.forEach((button) => {
        button.style.display = 'inline-block'; // Réafficher les boutons
      });
  
      if (mapElement) {
        mapElement.style.display = 'block'; // Réafficher la carte
      }
    });
  }
  
  
}
