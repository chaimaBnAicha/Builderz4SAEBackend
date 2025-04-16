import { Component, OnInit } from '@angular/core';
import { OfferServiceService } from '../../Services/offer-service.service';
import { Router } from '@angular/router';
import { Offer, TypeOffer, OfferStatus } from '../../Models/offer.model';

type FilterKeys = 'title' | 'description' | 'startDate' | 'endDate' | 'type' | 'status';

@Component({
  selector: 'app-getoffer',
  templateUrl: './getoffer.component.html',
  styleUrls: ['./getoffer.component.css']
})
export class GetofferComponent implements OnInit {
  offers: Offer[] = [];
  filteredOffers: Offer[] = [];
  currentPage = 1;
  pageSize = 5;
  isLoading = false;
  error: string | null = null;
  Math = Math;
  OfferStatus = OfferStatus;

  public filters: Record<FilterKeys, string> = {
    title: '',
    description: '',
    startDate: '',
    endDate: '',
    type: '',
    status: ''
  };

  constructor(
    private offerService: OfferServiceService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadOffers();
  }

  get totalPages(): number {
    return Math.ceil(this.filteredOffers.length / this.pageSize);
  }

  getPages(): number[] {
    const pages: number[] = [];
    const maxVisiblePages = 5;
    let startPage = Math.max(1, this.currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(this.totalPages, startPage + maxVisiblePages - 1);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(1, endPage - maxVisiblePages + 1);
    }

    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }
    return pages;
  }

  onPageChange(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  editOffer(id: number | undefined): void {
    if (id !== undefined) {
      this.router.navigate(['/admin/editoffer', id]);
    }
  }

  deleteOffer(id: number | undefined): void {
    if (id !== undefined && confirm('Are you sure you want to delete this offer?')) {
      this.offerService.deleteOffer(id).subscribe({
        next: () => {
          this.loadOffers();
        },
        error: (err) => {
          console.error('Error deleting offer:', err);
          this.error = 'Failed to delete offer';
        }
      });
    }
  }

  get paginatedOffers(): Offer[] {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    return this.filteredOffers.slice(start, end);
  }

  navigateToAddOffer(): void {
    this.router.navigate(['/admin/addoffer']);
  }

  onSearch(field: FilterKeys, event: any): void {
    const value = event.target.value;
    this.filters[field] = value;
    this.applyFilters();
  }

  applyFilters(): void {
    this.filteredOffers = this.offers.filter(offer => {
      const titleMatch = !this.filters.title || 
        offer.Title?.toLowerCase().includes(this.filters.title.toLowerCase());
      const descriptionMatch = !this.filters.description || 
        offer.Description?.toLowerCase().includes(this.filters.description.toLowerCase());
      const startDateMatch = !this.filters.startDate || 
        (offer.Start_Date && new Date(offer.Start_Date) >= new Date(this.filters.startDate));
      const endDateMatch = !this.filters.endDate || 
        (offer.End_Date && new Date(offer.End_Date) <= new Date(this.filters.endDate));
      const typeMatch = !this.filters.type || offer.Typeoffer === this.filters.type;
      const statusMatch = !this.filters.status || offer.Status === this.filters.status;

      return titleMatch && descriptionMatch && startDateMatch && 
             endDateMatch && typeMatch && statusMatch;
    });
  }

  loadOffers(): void {
    this.isLoading = true;
    this.offerService.getAllOffers().subscribe({
      next: (data) => {
        this.offers = data;
        this.filteredOffers = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading offers:', error);
        this.isLoading = false;
      }
    });
  }
}