import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError, tap } from 'rxjs';
import { Offer, TypeOffer, OfferStatus } from '../Models/offer.model';
import { map } from 'rxjs/operators';

// Interface for raw data from backend
interface RawOffer {
  id_offer: number;
  title?: string;        // lowercase from backend
  description?: string;  // lowercase from backend
  start_Date?: string;   // mixed case from backend
  end_Date?: string;     // mixed case from backend
  typeoffer?: string;    // lowercase from backend
  status?: string;       // lowercase from backend
  user_id?: number;
}

// Update the interface to match backend format
interface UpdateOfferDto {
  id_offer: number;
  user: {
    id: number;
  };
  description: string;
  title: string;
  status: string;
  start_Date: string;
  typeoffer: string;
  end_Date: string;
}

@Injectable({
  providedIn: 'root'
})
export class OfferServiceService {
  private apiUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) { }

  // Create - Add a new offer
  createOffer(offer: Offer): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    const formattedOffer = {
      user: {
        id: 1
      },
      description: offer.Description.toLowerCase(),
      title: offer.Title.toLowerCase(),
      status: "ACTIVE",
      start_Date: new Date(offer.Start_Date as string).toISOString(),
      typeoffer: offer.Typeoffer.toString(),
      end_Date: new Date(offer.End_Date as string).toISOString(),
      sendEmail: true
    };

    console.log('Sending formatted offer:', JSON.stringify(formattedOffer, null, 2));

    return this.http.post<any>(`${this.apiUrl}/add-Offer`, formattedOffer, { headers }).pipe(
      tap(response => console.log('Offer created successfully:', response)),
      catchError(error => {
        console.error('Error creating offer:', error);
        return throwError(() => error);
      })
    );
  }
/*
  // Read - Get all offers
  getAllOffers(): Observable<Offer[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    return this.http.get<RawOffer[]>(`${this.apiUrl}/retrieve-all-Offers`, { headers }).pipe(
      map(offers => {
        console.log('Raw offers from backend:', JSON.stringify(offers, null, 2));
        return offers.map(rawOffer => {
          try {
            // Parse dates properly
            const startDate = rawOffer.start_Date ? new Date(rawOffer.start_Date) : null;
            const endDate = rawOffer.end_Date ? new Date(rawOffer.end_Date) : null;

            // Convert type and status strings to enums
            const offerType = rawOffer.typeoffer as TypeOffer || TypeOffer.Insurance;
            const offerStatus = (rawOffer.status?.toUpperCase() as OfferStatus) || OfferStatus.ACTIVE;

            const processedOffer: Offer = {
              id_offer: rawOffer.id_offer,
              Title: rawOffer.title || '',
              Description: rawOffer.description || '',
              Start_Date: startDate,
              End_Date: endDate,
              Typeoffer: offerType,
              Status: offerStatus,
              user_id: rawOffer.user_id || 0
            };
            console.log('Processed offer:', processedOffer);
            return processedOffer;
          } catch (error) {
            console.error('Error processing offer:', error);
            return null;
          }
        }).filter(offer => offer !== null) as Offer[];
      }),
      catchError(error => {
        console.error('Error fetching offers:', error);
        return throwError(() => error);
      })
    );
  }*/

  // Read - Get offer by ID
  getOfferById(id: number): Observable<Offer> {
    return this.http.get<RawOffer>(`${this.apiUrl}/retrieve-Offer/${id}`).pipe(
      map(rawOffer => ({
        id_offer: rawOffer.id_offer,
        Title: rawOffer.title || '',
        Description: rawOffer.description || '',
        Start_Date: rawOffer.start_Date ? new Date(rawOffer.start_Date) : null,
        End_Date: rawOffer.end_Date ? new Date(rawOffer.end_Date) : null,
        Typeoffer: rawOffer.typeoffer as TypeOffer || TypeOffer.Insurance,
        Status: rawOffer.status as OfferStatus || OfferStatus.ACTIVE,
        user_id: rawOffer.user_id || 0
      }))
    );
  }

  getAllOffers(): Observable<Offer[]> {
    return this.http.get<any[]>(`${this.apiUrl}/retrieve-all-Offers`).pipe(
      tap(response => console.log('Raw response:', response)),
      map(offers => {
        if (!Array.isArray(offers)) {
          console.error('Expected array of offers, got:', offers);
          return [];
        }
        return offers.map(offer => ({
          id_offer: offer.id_offer || 0,
          Title: offer.title || '',
          Description: offer.description || '',
          Start_Date: offer.start_Date ? new Date(offer.start_Date) : null,
          End_Date: offer.end_Date ? new Date(offer.end_Date) : null,
          Typeoffer: offer.typeoffer || 'UNKNOWN',
          Status: offer.status || 'ACTIVE',
          user_id: offer.user_id || 0
        }));
      })
    );
  }

  updateOffer(id: number, offer: UpdateOfferDto): Observable<any> {
    return this.http.put(`${this.apiUrl}/modify-Offer`, offer);
  }

  deleteOffer(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/remove-Offer/${id}`);
  }
}