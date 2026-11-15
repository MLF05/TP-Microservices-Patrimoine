import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ReservationService {
  private apiUrl = '/api/reservations';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  create(r: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, r);
  }

  confirmer(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/confirmer`, {});
  }

  annuler(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/annuler`, {});
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}