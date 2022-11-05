import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/products.service';

import { PoCheckboxGroupOption, PoPageAction, PoSelectOption, PoFieldModule, PoSwitchComponent, PoRadioGroupOption } from '@po-ui/ng-components';

import { PoPageDynamicSearchLiterals, PoPageDynamicSearchFilters } from '@po-ui/ng-templates';
import Swal from 'sweetalert2';


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

  private onClickRemoveAllDisclaimer() {
    alert("Uepa");
  }

  public readonly actions: Array<PoPageAction> = [
    {
      label: 'Usuário', icon: 'po-icon po-icon-user',
      action: this.showUserInfoSwal.bind(this)
    },
    {
      label: 'GitHub', url: 'https://github.com/Canudo319/fuzzy-microservice-unip', icon: 'po-icon po-icon-social-github'
    },

    {
      label: 'Melhor opção', icon: 'po-icon po-icon-ok',
      action: this.readBestItems.bind(this)
    },
    {
      label: 'Mais barato', icon: 'po-icon po-icon-payment',
      action: this.readCheapestItems.bind(this)
    },
    {
      label: 'Mais perto', icon: 'po-icon po-icon-truck',
      action: this.readNearestSupplier.bind(this)
    }
  ];

  private statusOptions: Array<PoRadioGroupOption> = [{ value: 'disabled', label: 'Disabled' }];;
  public readonly filters: Array<PoPageDynamicSearchFilters> = [
    { property: 'hireStatus', label: 'Filtro', options: this.statusOptions },
  ];

  showUserInfoSwal() {
    if (localStorage.getItem("lat") != null && localStorage.getItem("long") != null) {
      Swal.fire({
        icon: 'info',
        html:
          '<b>Seja bem-vindo!</b> GUILHERME ROCHA' +
          '<p>Abaixo estão suas informações cadastrais do endereço: </p> ' +
          '<p>R. Catatau e Zé Colmeia, Nº 12</p>',
      })
    } else {
      Swal.fire({
        icon: 'warning',
        html:
          '<b>Atenção!</b> ' +
          '<p>Edite o seu perfil para realizar a filtragem dos produtos de maneira correta!</p> '
      })
    }
  }

  showAlert(title: any, text: any, icon: any) {
    Swal.fire({
      icon: icon,
      title: title,
      text: text
    })
  }

  readBestItems() {
    this.fuzzies = [];
    this.allItems.forEach((obj) => {

      this.productsService.readBestItens(obj.id, 0, 0).subscribe(res => {
        if (obj.id == res[0].stock.shoppingItem.id && res[0].stock.shoppingItem.name) {
          this.fuzzies.push(res);
          this.showAlert("Sucesso!", "Filtro aplicado com sucesso.", "success");
        }
      })
    });
  }

  readCheapestItems() {
    this.fuzzies = [];
    this.allItems.forEach((obj) => {

      this.productsService.readCheapestItems(obj.id).subscribe(res => {
        if (obj.id == res[0].stock.shoppingItem.id && res[0].stock.shoppingItem.name) {
          this.fuzzies.push(res);
          this.showAlert("Sucesso!", "Filtro aplicado com sucesso.", "success");
        }
      })
    });
  }

  readNearestSupplier() {
    this.fuzzies = [];
    this.allItems.forEach((obj) => {

      this.productsService.readNearestSupplier(obj.id, 0, 0).subscribe(res => {
        if (obj.id == res[0].stock.shoppingItem.id && res[0].stock.shoppingItem.name) {
          this.fuzzies.push(res);
          this.showAlert("Sucesso!", "Filtro aplicado com sucesso.", "success");
        }
      })
    });
  }

  onQuickSearch(filter: any) {
    filter ? this.products = this.searchItems({ name: filter }) : this.products! = this.productsService.resetFilters(this.allItems);
  }

  onAdvancedSearch(filter: any) {
    filter ? this.products = this.searchItems({ name: filter }) : this.products! = this.productsService.resetFilters(this.allItems);
  }

  searchItems(filter: any) {
    return this.productsService.filter(filter, this.allItems);
  }

}