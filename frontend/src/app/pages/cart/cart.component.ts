import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartItems: any[] = [];

  constructor() { }

  ngOnInit(): void {
    this.getCartItems();
  }

  getCartItems() {
    var ls = localStorage.getItem('cartObj');
    if (ls) {
      var objLs = JSON.parse(ls);
      objLs.forEach((element: any) => {
        this.cartItems.push(element)
      });
      console.log("Tetse ")
      console.log(this.cartItems);
    }
  }

  addQuantity(field: any) {

  }

  decQuantity(field: any) {

  }

}
