import { Component, OnInit, Input } from '@angular/core';
import { ProductsService } from './../../products.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})


export class CardComponent implements OnInit {
  value: string = "";//"Spring Tool Suite 4 makes it easy to get started. A direct and easy-to-use integration of the Spring Initializr and the famous Spring Guides allows you";
  label: string = "";
  help: string = "https://unip.br/";
  products: any[] = [];
  cart: any[] = [];

  constructor(private productsService: ProductsService) { }

  ngOnInit(): void {
    this.productsService.read().subscribe(res => this.products = res);
  }

  addCart(id: any, productName: string, img: any, brand: any) {
    // var cartCounter = document.querySelector('.cart-count') as any;
    // cartCounter.innerText = Number(cartCounter.innerText) + 1;

    this.productsService.readStockById(id).subscribe(res => {
      if (res[0].stock == 0) {
        return this.showAlert("Aviso!", "Produto não está disponível no estoque.", "warning");
      }
    });

    if (JSON.parse(localStorage.getItem("cartObj")!).length != 0) {
      this.cart = JSON.parse(localStorage.getItem("cartObj")!);
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

  showAlert(title: any, text: any, icon: any) {
    Swal.fire({
      icon: icon,
      title: title,
      text: text
    })
  }

}


