import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EvenementService {
  private apiUrl = '/api/evenements';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  create(e: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, e);
  }

  update(id: number, e: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, e);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  verifierDisponibilite(id: number, nombrePlaces: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/${id}/disponibilite?nombrePlaces=${nombrePlaces}`);
  }
}