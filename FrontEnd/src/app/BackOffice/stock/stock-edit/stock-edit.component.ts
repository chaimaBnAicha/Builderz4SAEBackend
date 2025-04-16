// Update these import statements
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StockService } from '../../../service/stock.service';
import { NotificationService } from '../../../service/notification.service';
import { Injectable } from '@angular/core';
import { StockCategory } from  '../stock.model';
import {  Stock as ServiceStock } from '../../../service/stock.service';
import { Stock } from '../stock.model';

import { FormsModule } from '@angular/forms';
@Injectable({
  providedIn: 'root'
})
@Component({
  selector: 'app-stock-edit',
  templateUrl: './stock-edit.component.html',
  styleUrls: ['./stock-edit.component.css']
})
export class StockEditComponent implements OnInit {
  stock: Stock | null = null;
  categories: StockCategory[] = ['MATERIALS', 'TOOLS', 'ELECTRICAL_PLUMBING'];
  formErrors = {
    name: '',
    quantity: '',
    unitPrice: '',
    description: ''
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private stockService: StockService,
    private notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadStock(Number(id));
    }
  }

  validateName(name: string): boolean {
    if (!name || name.trim().length === 0) {
      this.formErrors.name = 'Le nom est requis';
      return false;
    }
    if (name.trim().length < 3) {
      this.formErrors.name = 'Le nom doit contenir au moins 3 caractères';
      return false;
    }
    this.formErrors.name = '';
    return true;
  }

  validateQuantity(quantity: number): boolean {
    if (quantity === null || quantity === undefined) {
      this.formErrors.quantity = 'La quantité est requise';
      return false;
    }
    if (quantity < 0) {
      this.formErrors.quantity = 'La quantité ne peut pas être négative';
      return false;
    }
    this.formErrors.quantity = '';
    return true;
  }

  validateUnitPrice(price: number): boolean {
    if (price === null || price === undefined) {
      this.formErrors.unitPrice = 'Le prix est requis';
      return false;
    }
    if (price <= 0) {
      this.formErrors.unitPrice = 'Le prix doit être supérieur à 0';
      return false;
    }
    this.formErrors.unitPrice = '';
    return true;
  }

  validateDescription(description: string): boolean {
    if (!description || description.trim().length === 0) {
      this.formErrors.description = 'La description est requise';
      return false;
    }
    if (description.trim().length < 10) {
      this.formErrors.description = 'La description doit contenir au moins 10 caractères';
      return false;
    }
    this.formErrors.description = '';
    return true;
  }

  loadStock(id: number): void {
    this.stockService.getStockById(id).subscribe({
      next: (data) => {
        this.stock = {
          ...data,
          bills: data.bills || []
        };
      },
      error: (error) => {
        this.notificationService.showError('Erreur lors du chargement du stock');
        console.error(error);
      }
    });
  }

  // Update method signatures with proper types
  onDataReceived(data: Stock): void {
    this.stock = {
      ...data,
      bills: data.bills || []
    };
  }

 

  updateStock(): void {
    if (this.stock) {
      const stockData: ServiceStock = {
        id_stock: this.stock.id_stock,
        name: this.stock.name,
        quantity: this.stock.quantity,
        unitPrice: this.stock.unitPrice,
        description: this.stock.description,
        category: this.stock.category,
        bills: this.stock.bills || []
      };

      this.stockService.updateStock(stockData.id_stock, stockData).subscribe(
        (updatedStock: ServiceStock) => {
          this.notificationService.showSuccess('Stock mis à jour avec succès');
          this.router.navigate(['/admin/edit/:id']);
        },
        (error: any) => {
          this.handleError(error);
        }
      );
    } else {
      this.notificationService.showError('Aucun stock à mettre à jour');
    }
  }

  cancel(): void {
    this.router.navigate(['/admin/stock']);
  }

  private handleError(error: any): void {
    console.error('Error:', error);
    this.notificationService.showError('Erreur lors de la mise à jour du stock');
  }
}