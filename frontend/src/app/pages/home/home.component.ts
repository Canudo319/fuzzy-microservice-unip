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
    var latitude = localStorage.getItem("lat");
    var longitude = localStorage.getItem("long");
    this.productsService.read().subscribe(res => {
      this.allItems = res;
      this.allItems.forEach((obj) => {
        this.productsService.readBestItens(obj.id, latitude != null ? Number(latitude) : 0, longitude != null ? Number(longitude) : 0).subscribe(res => {
          document.querySelector(".po-row")?.querySelectorAll('[id^=product]').forEach((obj) => {
            let id = obj.id.split("_")[1].trim();
            if (id == res[0].stock.shoppingItem.id && res[0].stock.shoppingItem.name) {
              this.fuzzies.push(res);
            }
          });
        });
      });
      console.log(this.fuzzies);
    });
    this.productsService.read().subscribe(res => this.products = res);



  }

  addCart(id: any, productName: string, img: any, brand: any) {
    var cartCounter = document.querySelector('.cart-count') as any;
    cartCounter.innerText = Number(cartCounter.innerText) + 1;



    var supplier: any[] = [];
    var supplierFinal: any[] = [];
    this.productsService.readStockById(id).subscribe(res => {
      for (var i = 0; i < res.length; i++) {
        if (res[i].stock == 0) {
          this.showAlert("Aviso!", "Esse produto n??o est?? mais dispon??vel no estoque.", "warning");
        }
        supplierFinal.push({ name: res[i].supplier.name, shoppingItemId: res[i].shoppingItem.id, supplierId: res[i].id })
      }

      if (JSON.parse(localStorage.getItem('cartObj')!).length != 0) {
        this.cart = JSON.parse(localStorage.getItem('cartObj')!);
        this.cart.push(
          {
            "id": id,
            "productName": productName,
            "brand": brand,
            "image": img,
            "supplier": supplierFinal
          }
        );

        this.cart = this.cart.filter((value, index, self) =>
          index === self.findIndex((t) => (
            t.id === value.id && t.img === value.img
          ))
        )

      } else {
        this.cart = [];
        this.cart.push(
          {
            "id": id,
            "productName": productName,
            "brand": brand,
            "image": img,
            "supplier": supplierFinal
          }
        );
      }

      let strObj = JSON.stringify(this.cart);
      this.cart.forEach((obj) => {
        localStorage.setItem('cartObj', strObj);
      });

      this.showAlert("Sucesso!", "Produto adicionado ao carrinho.", "success");

    })
  }

  readonly literals: PoPageDynamicSearchLiterals = {
    filterConfirmLabel: 'Aplicar',
    filterTitle: 'Filtro avan??ado',
    quickSearchLabel: 'Valor pesquisado:'
  };

  private onClickRemoveAllDisclaimer() {
    alert("Uepa");
  }

  public readonly actions: Array<PoPageAction> = [
    {
      label: 'Usu??rio', icon: 'po-icon po-icon-user',
      action: this.showUserInfoSwal.bind(this)
    },
    {
      label: 'GitHub', url: 'https://github.com/Canudo319/fuzzy-microservice-unip', icon: 'po-icon po-icon-social-github'
    },

    {
      label: 'Melhor op????o', icon: 'po-icon po-icon-ok',
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
          '<b>Seja bem-vindo! </b>' + localStorage.getItem("username") + " " + localStorage.getItem("userSurname") +
          '<p>Abaixo est??o suas informa????es cadastrais do endere??o: </p> ' +
          '<p>Latitude: ' + localStorage.getItem("lat") + '</p>' +
          '<p>Longitude: ' + localStorage.getItem("long") + '</p>',
      })
    } else {
      Swal.fire({
        icon: 'warning',
        html:
          '<b>Aten????o!</b> ' +
          '<p>Edite o seu perfil para realizar a filtragem dos produtos de maneira correta!</p> '
      })
    }
  }


  showSubtitleInfoSwal() {
    Swal.fire({
      icon: 'info',
      html:
        '<b>Legenda: </b><br><br>' +
        '<div style="display: inline-block;">' +
        '<div style="display: flex; margin-top: 10%; align-items: center;">' +
        '<span class="dot" style="height: 25px; width: 25px; background-color:#00630c; border-radius: 50%; display: inline-block; margin-right: 2%; "></span><small> Melhor op????o</small><br>' +
        '</div>' +
        '<div style="display: flex; margin-top: 10%; align-items: center;">' +
        '<span class="dot" style="height: 25px; width: 25px; background-color:#26c739; border-radius: 50%; display: inline-block; margin-right: 2%; "></span><small> Boa op????o</small><br>' +
        '</div>' +
        '<div style="display: flex; margin-top: 10%; align-items: center;">' +
        '<span class="dot" style="height: 25px; width: 25px; background-color:#c1c92a; border-radius: 50%; display: inline-block; margin-right: 2%; "></span><small> Op????o mediana</small><br>' +
        '</div>' +
        '<div style="display: flex; margin-top: 10%; align-items: center;">' +
        '<span class="dot" style="height: 25px; width: 25px; background-color:#bd5f1c; border-radius: 50%; display: inline-block; margin-right: 2%; "></span><small> Op????o ruim</small><br>' +
        '</div>' +
        '<div style="display: flex; margin-top: 10%; align-items: center;">' +
        '<span class="dot" style="height: 25px; width: 25px; background-color:#bf0000; border-radius: 50%; display: inline-block; margin-right: 2%; "></span><small> N??o recomendada</small><br>' +
        '</div>' +
        '</div>'
    })
  }

  showAlert(title: any, text: any, icon: any) {
    Swal.fire({
      icon: icon,
      title: title,
      text: text
    })
  }

  readBestItems() {
    var latitude = localStorage.getItem("lat");
    var longitude = localStorage.getItem("long");
    this.fuzzies = [];
    this.allItems.forEach((obj, index) => {

      this.productsService.readBestItens(obj.id, latitude != null ? Number(latitude) : 0, longitude != null ? Number(longitude) : 0).subscribe(res => {
        if (obj.id == res[0].stock.shoppingItem.id && res[0].stock.shoppingItem.name) {
          this.fuzzies.push(res);
          this.showAlert("Sucesso!", "Filtro aplicado com sucesso.", "success");
        }
      });
    });
  }

  readCheapestItems() {
    this.fuzzies = [];
    this.allItems.forEach((obj, index) => {

      this.productsService.readCheapestItems(obj.id).subscribe(res => {
        if (obj.id == res[0].stock.shoppingItem.id && res[0].stock.shoppingItem.name) {
          this.fuzzies.push(res);
          this.showAlert("Sucesso!", "Filtro aplicado com sucesso.", "success");
        }
      })
    });
  }

  readNearestSupplier() {
    var latitude = localStorage.getItem("lat");
    var longitude = localStorage.getItem("long");
    this.fuzzies = [];
    this.allItems.forEach((obj, index) => {

      this.productsService.readNearestSupplier(obj.id, latitude != null ? Number(latitude) : 0, longitude != null ? Number(longitude) : 0).subscribe(res => {
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