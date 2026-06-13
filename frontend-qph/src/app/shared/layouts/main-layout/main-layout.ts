import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AppBar } from '@/shared/components/app-bar/app-bar';
import { AuthService } from '@/core/services/auth';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [RouterOutlet, AppBar],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss',
})
export class MainLayout {
  readonly #auth = inject(AuthService);

  navItems = [
    { label: 'Productos', route: '/products', icon: 'pi pi-box', exact: false },
    { label: 'Ventas', route: '/sales', icon: 'pi pi-shopping-cart', exact: false },
    { label: 'Usuarios', route: '/users', icon: 'pi pi-users', exact: false },
  ];

  onLogout(): void {
    this.#auth.logout();
  }
}
