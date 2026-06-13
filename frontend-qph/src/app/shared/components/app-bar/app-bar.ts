import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Button } from 'primeng/button';
import { RouterLink, RouterLinkActive } from '@angular/router';

export interface NavItem {
  label: string;
  route: string;
  icon: string;
  exact?: boolean;
}

@Component({
  selector: 'app-bar',
  imports: [Button, RouterLink, RouterLinkActive],
  templateUrl: './app-bar.html',
  styleUrl: './app-bar.scss',
})
export class AppBar {
  @Input() navItems: NavItem[] = [];
  @Output() logout = new EventEmitter<void>();
}
