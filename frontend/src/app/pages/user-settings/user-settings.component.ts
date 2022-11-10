import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {
  lat: any = 0;
  long: any = 0;
  name: any;
  surname: any;


  constructor() { }

  ngOnInit(): void {
    this.getUserPrefs();
  }

  savePref() {
    localStorage.setItem("lat", this.lat);
    localStorage.setItem("long", this.long);
    localStorage.setItem("username", this.name);
    localStorage.setItem("userSurname", this.surname);

    this.showAlert("Sucesso!", "Preferências salvas com sucesso.", "success");
  }

  getUserPrefs() {
    if (localStorage.getItem("lat") != null &&
      localStorage.getItem("long") != null &&
      localStorage.getItem("username") != null &&
      localStorage.getItem("userSurname") != null) {
      this.lat = localStorage.getItem("lat");
      this.long = localStorage.getItem("long");
      this.name = localStorage.getItem("username");
      this.surname = localStorage.getItem("userSurname");
    }
  }

  showAlert(title: any, text: any, icon: any) {
    Swal.fire({
      icon: icon,
      title: title,
      text: text
    })
  }

}
