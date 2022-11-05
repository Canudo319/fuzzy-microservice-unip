import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PoMenuItem } from '@po-ui/ng-components';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private router: Router) { }
  title = 'my-new-app';
  readonly sidemenu: Array<PoMenuItem> = [

    {
      label: 'Home',
      action: () => {
        this.router.navigate(['home']);
      },
      icon: 'po-icon-home',
      shortLabel: 'Home',
    },
    {
      label: 'Carrinho',
      action: () => {
        this.router.navigate(['cart']);
      },
      icon: 'po-icon po-icon-cart',
      shortLabel: 'Carrinho'
    },
    {
      label: 'Perfil',
      action: () => {
        this.router.navigate(['user-settings']);
      },
      icon: 'po-icon po-icon-user',
      shortLabel: 'Perfil'
    },
  ];
}
