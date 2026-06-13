import { Routes } from '@angular/router';

export const SALE_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./sale-list/sale-list').then(m => m.SaleListComponent)
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./sale-form/sale-form').then(m => m.SaleFormComponent)
  },
];
