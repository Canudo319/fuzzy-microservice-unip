import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { PoPageDynamicSearchModule } from '@po-ui/ng-templates';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { PoModule } from '@po-ui/ng-components';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CartComponent } from './pages/cart/cart.component';
import { CardComponent } from './components/card/card.component';
import { UserSettingsComponent } from './pages/user-settings/user-settings.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CartComponent,
    UserSettingsComponent,
    CardComponent,
  ],
  imports: [
    BrowserModule,
    PoModule,
    FormsModule,
    ReactiveFormsModule,
    PoPageDynamicSearchModule,
    RouterModule.forRoot([
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
      { path: 'cart', component: CartComponent },
      { path: 'user-settings', component: UserSettingsComponent },

    ])
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
