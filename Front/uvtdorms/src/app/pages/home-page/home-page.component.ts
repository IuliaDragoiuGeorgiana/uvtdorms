import { Component } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {
  MatSlideToggleModule,
  _MatSlideToggleRequiredValidatorModule,
} from '@angular/material/slide-toggle';
import {FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    MatSlideToggleModule,
    _MatSlideToggleRequiredValidatorModule,
    MatButtonModule,
    FormsModule
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  toggleValue: boolean = false;

  onToggleChange() {
    console.log('Toggle changed:', this.toggleValue);
    this.toggleValue = !this.toggleValue;
  }
}
