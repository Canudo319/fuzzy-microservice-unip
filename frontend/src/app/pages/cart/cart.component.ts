import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/products.service';
import Swal from 'sweetalert2';
import { Products } from './../../products.model';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartItems: any[] = [];
  suppliers: any[] = [];

  constructor(private productsService: ProductsService) { }

  ngOnInit(): void {
    this.getCartItems();
    let strCartObj = JSON.stringify(localStorage.getItem("cartObj"));
    console.log(strCartObj)
  }

  getCartItems() {
    var ls = localStorage.getItem('cartObj');
    if (ls) {
      var objLs = JSON.parse(ls);
      objLs.forEach((element: any, index: any) => {
        this.cartItems.push(element);
        this.cartItems[index].supplier.forEach((obj: any, indexObj: any) => {
          console.log("this.suppliers");
          console.log(this.suppliers);
          this.suppliers.push({ label: `${element.supplier[indexObj].supplierId} - ${element.supplier[indexObj].name}`, value: `${element.supplier[indexObj].name}` });
        });
      });
      console.log("Tetse ")
    }
  }

  delItem(index: any) {
    var cartItem = document.querySelector('#delete_' + index) as any;
    this.cartItems.forEach((obj) => {
      if (obj.id == index) {
        this.cartItems = this.cartItems.filter(item => item.id !== obj.id);
      }
      localStorage.setItem("cartObj", JSON.stringify(this.cartItems));
    })
  }

  addQuantity(index: any) {
    var cartCounter = document.querySelector('#quantidadeCart_' + index) as any;
    cartCounter.value = Number(cartCounter.value) + 1;
    this.productsService.readStockById(index).subscribe(res => {
      if (Number(cartCounter.value) > res[0].stock) {
        cartCounter.value = Number(cartCounter.value) - 1;
        this.showAlert("Aviso!", "Essa quantidade não está disponível no estoque.", "warning");
      }
    });
  }

  decQuantity(index: any) {
    var cartCounter = document.querySelector('#quantidadeCart_' + index) as any;
    cartCounter.value = Number(cartCounter.value) - 1;
    if (Number(cartCounter.value) < 1) {
      cartCounter.value = 1;
    }
  }

  showAlert(title: any, text: any, icon: any) {
    Swal.fire({
      icon: icon,
      title: title,
      text: text
    })
  }

  saveOrder() {

  }

}
