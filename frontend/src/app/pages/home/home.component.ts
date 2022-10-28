import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/products.service';

import { PoPageAction } from '@po-ui/ng-components';

import { PoPageDynamicSearchLiterals, PoPageDynamicSearchFilters } from '@po-ui/ng-templates';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private productsService: ProductsService) { }

  value: string = "";//"Spring Tool Suite 4 makes it easy to get started. A direct and easy-to-use integration of the Spring Initializr and the famous Spring Guides allows you";
  label: string = "";
  help: string = "https://unip.br/";
  fuzzies: any[] = [];
  products: any[] = [];
  allItems: any[] = [];
  cart: any[] = [];

  ngOnInit(): void {
    this.productsService.read().subscribe(res => {
      this.allItems = res;
      this.allItems.forEach((obj) => {

        this.productsService.readBestItens(obj.id, 0, 0).subscribe(res => {
          document.querySelector(".po-row")?.querySelectorAll('[id^=product]').forEach((obj) => {
            let id = obj.id.split("_")[1].trim();
            if (id == res[0].stock.shoppingItem.id && res[0].stock.shoppingItem.name) {
              // const element = document.querySelector<HTMLElement>("#product_" + id)!;
              // element.style.backgroundColor = "green";
              this.fuzzies.push(res);
            }
          })

        });
      });
      console.log(this.fuzzies);
    });
    this.productsService.read().subscribe(res => this.products = res);



  }

  addCart(id: any, productName: string, img: any, brand: any) {
    var cartCounter = document.querySelector('.cart-count') as any;
    cartCounter.innerText = Number(cartCounter.innerText) + 1;
    if (this.cart.length != 0) {

      this.cart.push(
        {
          "id": id,
          "productName": productName,
          "brand": brand,
          "image": img
        }
      );

      this.cart = this.cart.filter((value, index, self) =>
        index === self.findIndex((t) => (
          t.id === value.id && t.img === value.img
        ))
      )

    } else {
      this.cart.push(
        {
          "id": id,
          "productName": productName,
          "brand": brand,
          "image": img
        }
      );
    }

    let strObj = JSON.stringify(this.cart);
    this.cart.forEach((obj) => {
      localStorage.setItem('cartObj', strObj);
    });
  }

  readonly literals: PoPageDynamicSearchLiterals = {
    filterConfirmLabel: 'Aplicar',
    filterTitle: 'Filtro avançado',
    quickSearchLabel: 'Valor pesquisado:'
  };



  onQuickSearch(filter: any) {
    filter ? this.products = this.searchItems({ name: filter }) : this.products! = this.productsService.resetFilters(this.allItems);
  }

  searchItems(filter: any) {
    return this.productsService.filter(filter, this.allItems);
  }

}