import { Component } from '@angular/core';
import { Toast } from 'primeng/toast';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { AppBar } from '@/shared/components/app-bar/app-bar';

@Component({
  selector: 'app-list-layout',
  imports: [Toast, ConfirmDialog, AppBar],
  templateUrl: './list-layout.html',
  styleUrl: './list-layout.scss',
})
export class ListLayout {}
