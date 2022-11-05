import { Component, OnInit } from '@angular/core';

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
  }

  savePref() {

    alert(this.lat);

    localStorage.setItem("lat", this.lat);
    localStorage.setItem("long", this.long);
    localStorage.setItem("username", this.name);
    localStorage.setItem("userSurname", this.surname);

  }

}
