import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './root/app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { LaundryAppointmentsPageComponent } from './pages/laundry-appointments-page/laundry-appointments-page.component';
import { NavbarComponent } from './elements/navbar/navbar.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCheckboxModule} from '@angular/material/checkbox'
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatCardModule} from '@angular/material/card';
import {MatNativeDateModule} from '@angular/material/core';
import {MatChipsModule} from '@angular/material/chips';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    LaundryAppointmentsPageComponent,
    NavbarComponent,
    HomePageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSlideToggleModule,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatCardModule,
    MatNativeDateModule,
    MatChipsModule,
    FormsModule,
  ],
  providers: [
    // provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
