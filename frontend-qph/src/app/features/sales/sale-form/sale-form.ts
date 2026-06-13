import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SaleService } from '@/core/services/sale';
import { ProductService } from '@/core/services/product';
import { Product } from '@/shared/models/product.model';

import { InputNumberModule } from 'primeng/inputnumber';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-sale-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputNumberModule,
    SelectModule,
    ButtonModule,
    ToastModule,
  ],
  providers: [MessageService],
  templateUrl: './sale-form.html',
  styleUrl: './sale-form.scss',
})
export class SaleFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private saleService = inject(SaleService);
  private productService = inject(ProductService);
  private router = inject(Router);
  private messageService = inject(MessageService);

  products: Product[] = [];

  form = this.fb.group({
    productId: [null as number | null, Validators.required],
    quantity: [null as number | null, [Validators.required, Validators.min(1)]],
  });

  submitting = false;

  ngOnInit(): void {
    this.productService.getAll().subscribe({
      next: (data) => (this.products = data),
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting = true;
    const { productId, quantity } = this.form.value as { productId: number; quantity: number };

    this.saleService.create({ productId, quantity }).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: 'Venta registrada correctamente',
        });
        setTimeout(() => this.router.navigate(['/sales']), 1500);
      },
      error: (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: err.error?.error ?? 'Error al registrar la venta',
        });
        this.submitting = false;
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/sales']);
  }
}
