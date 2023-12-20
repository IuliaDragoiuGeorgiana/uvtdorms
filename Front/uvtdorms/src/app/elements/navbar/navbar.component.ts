import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  homePath: string = '/home';
  laundryAppointmentsPath = '/laundry-appointments';

  constructor(private router: Router){}

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}
