import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Sale } from '@/shared/models/sale.model';
import { SaleService } from '@/core/services/sale';
import { AuthService } from '@/core/services/auth';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-sale-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, ConfirmDialogModule, ToastModule],
  providers: [ConfirmationService, MessageService],
  templateUrl: './sale-list.html',
  styleUrl: './sale-list.scss',
})
export class SaleListComponent implements OnInit {
  private saleService = inject(SaleService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private confirmationService = inject(ConfirmationService);
  private messageService = inject(MessageService);

  sales: Sale[] = [];
  loading = false;
  showMine = false;

  ngOnInit(): void {
    this.loadSales();
  }

  loadSales(): void {
    this.loading = true;
    const request = this.showMine
      ? this.saleService.getByUser()
      : this.saleService.getAll();

    request.subscribe({
      next: (data) => {
        this.sales = data;
        this.loading = false;
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar las ventas',
        });
        this.loading = false;
      },
    });
  }

  private getUserIdFromToken(): number {
    const token = this.authService.getToken();
    if (!token) return 0;
    try {
      const payload = JSON.parse(atob(token));
      return payload.userId || 0;
    } catch {
      return 0;
    }
  }

  toggleFilter(): void {
    this.showMine = !this.showMine;
    this.loadSales();
  }

  goToCreate(): void {
    this.router.navigate(['/sales/new']);
  }

  confirmDelete(sale: Sale): void {
    this.confirmationService.confirm({
      message: `¿Estás seguro de eliminar la venta #${sale.id}?`,
      header: 'Confirmar eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.deleteSale(sale.id!),
    });
  }

  private deleteSale(id: number): void {
    this.saleService.delete(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: 'Venta eliminada correctamente',
        });
        this.loadSales();
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudo eliminar la venta',
        });
      },
    });
  }
}
