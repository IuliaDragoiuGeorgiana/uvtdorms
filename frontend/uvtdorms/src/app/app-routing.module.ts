import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LaundryAppointmentsPageComponent } from './pages/laundry-appointments-page/laundry-appointments-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { StudentsAdministationPageComponent } from './pages/students-administation-page/students-administation-page.component';
import { DormMachinesPageComponent } from './pages/dorm-machines-page/dorm-machines-page.component';
import { KeepLaundryAppointmentPageComponent } from './pages/keep-laundry-appointment-page/keep-laundry-appointment-page.component';
import { ForgotPasswordPageComponent } from './pages/forgot-password-page/forgot-password-page.component';
import { ChangeForgottenPasswordPageComponent } from './pages/change-forgotten-password-page/change-forgotten-password-page.component';
import { TicketsPageComponent } from './pages/tickets-page/tickets-page.component';
import { TicketsAdministrationPageComponent } from './pages/tickets-administration-page/tickets-administration-page.component';
import { AllDormsPageComponent } from './pages/all-dorms-page/all-dorms-page.component';
import { DormAdministratorsPageComponent } from './pages/dorm-administrators-page/dorm-administrators-page.component';
import { RoomsAdministrationPageComponent } from './pages/rooms-administration-page/rooms-administration-page.component';
import { EvenimentsPageComponent } from './pages/eveniments-page/eveniments-page.component';

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
  {
    path: 'keep-laundry-appointment/:id',
    component: KeepLaundryAppointmentPageComponent,
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordPageComponent,
  },
  {
    path: 'reset-password/:token',
    component: ChangeForgottenPasswordPageComponent,
  },

  {
    path: 'tickets',
    component: TicketsPageComponent,
  },

  {
    path: 'tickets-administration',
    component: TicketsAdministrationPageComponent,
  },

  {
    path: 'dorms-administration',
    component: AllDormsPageComponent,
  },

  {
    path: 'dorm-administrators',
    component: DormAdministratorsPageComponent,
  },

  {
    path: 'rooms-administration',
    component: RoomsAdministrationPageComponent,
  },

  {
    path: 'eveniments',
    component: EvenimentsPageComponent,
  },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
