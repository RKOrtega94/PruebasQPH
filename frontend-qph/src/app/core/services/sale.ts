import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sale } from '@/shared/models/sale.model';

export interface CreateSaleRequest {
  productId: number;
  quantity: number;
}

export interface UpdateSaleRequest {
  productId: number;
  quantity: number;
  date: string;
}

@Injectable({ providedIn: 'root' })
export class SaleService {
  private readonly apiUrl = 'http://localhost:8080/api/sales';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Sale[]> {
    return this.http.get<Sale[]>(this.apiUrl);
  }

  getByUser(): Observable<Sale[]> {
    return this.http.get<Sale[]>(`${this.apiUrl}/me`);
  }

  getById(id: number): Observable<Sale> {
    return this.http.get<Sale>(`${this.apiUrl}/${id}`);
  }

  create(data: CreateSaleRequest): Observable<Sale> {
    return this.http.post<Sale>(this.apiUrl, data);
  }

  update(id: number, data: UpdateSaleRequest): Observable<Sale> {
    return this.http.put<Sale>(`${this.apiUrl}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
