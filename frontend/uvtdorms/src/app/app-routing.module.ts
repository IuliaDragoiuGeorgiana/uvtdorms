import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LaundryAppointmentsPageComponent } from './pages/laundry-appointments-page/laundry-appointments-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';

const routes: Routes = [
  {path: 'laundry-appointments', component: LaundryAppointmentsPageComponent},
  {path:'login', component: LoginPageComponent},

  {path: '', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
