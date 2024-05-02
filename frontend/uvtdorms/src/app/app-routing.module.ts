import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LaundryAppointmentsPageComponent } from './pages/laundry-appointments-page/laundry-appointments-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { StudentsAdministationPageComponent } from './pages/students-administation-page/students-administation-page.component';
import { DormMachinesPageComponent } from './pages/dorm-machines-page/dorm-machines-page.component';

const routes: Routes = [
  { path: 'laundry-appointments', component: LaundryAppointmentsPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'profile', component: ProfilePageComponent },
  { path: 'not-found', component: NotFoundPageComponent },
  {
    path: 'students-administration',
    component: StudentsAdministationPageComponent,
  },
  {
    path: 'dorm-machines-administration',
    component: DormMachinesPageComponent,
  },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
