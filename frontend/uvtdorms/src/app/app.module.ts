import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './root/app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LaundryAppointmentsPageComponent } from './pages/laundry-appointments-page/laundry-appointments-page.component';
import { NavbarComponent } from './elements/navbar/navbar.component';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatChipsModule } from '@angular/material/chips';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatGridListModule } from '@angular/material/grid-list';
import {
  HttpClientModule,
  provideHttpClient,
  withFetch,
} from '@angular/common/http';
import { MatTabsModule } from '@angular/material/tabs';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { MatMenuModule } from '@angular/material/menu';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';

import {
  MatDialogTitle,
  MatDialogContent,
  MatDialogActions,
  MatDialogClose,
  MatDialogModule,
} from '@angular/material/dialog';
import { RegisterErrorDialogComponent } from './elements/dialogs/register/register-error-dialog/register-error-dialog.component';
import { RegisterConfirmDialogComponent } from './elements/dialogs/register/register-confirm-dialog/register-confirm-dialog.component';
import { RegisterRequestDetailsPopupComponent } from './elements/dialogs/studentsAdministration/register-request-details-popup/register-request-details-popup.component';
import { StudentsAdministationPageComponent } from './pages/students-administation-page/students-administation-page.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { EditRoomNumberDialogComponent } from './elements/dialogs/studentsAdministration/edit-room-number-dialog/edit-room-number-dialog.component';

// PrimeNG
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { NewRegisterRequestDialogComponent } from './elements/dialogs/new-register-request-dialog/new-register-request-dialog.component';
import { RemoveStudentFromDormDialogComponent } from './elements/dialogs/remove-student-from-dorm-dialog/remove-student-from-dorm-dialog.component';
import { ProfilePageDisplayStudentDetailsComponent } from './pages/profile-page/profile-page-display-student-details/profile-page-display-student-details.component';
import { ProfilePageDisplayDormAdministratorComponent } from './pages/profile-page/profile-page-display-dorm-administrator/profile-page-display-dorm-administrator.component';
import { EditPhoneNumberDialogComponent } from './elements/dialogs/edit-phone-number-dialog/edit-phone-number-dialog.component';
import { ChangePasswordDialogComponent } from './elements/dialogs/change-password-dialog/change-password-dialog.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { DormMachinesPageComponent } from './pages/dorm-machines-page/dorm-machines-page.component';
import { ToolbarModule } from 'primeng/toolbar';
import { StepperModule } from 'primeng/stepper';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { FieldsetModule } from 'primeng/fieldset';
import { CardModule } from 'primeng/card';
import { LoadingScreenComponent } from './elements/loading-screen/loading-screen.component';
import { KeepLaundryAppointmentPageComponent } from './pages/keep-laundry-appointment-page/keep-laundry-appointment-page.component';
import { ForgotPasswordPageComponent } from './pages/forgot-password-page/forgot-password-page.component';
import { ChangeForgottenPasswordPageComponent } from './pages/change-forgotten-password-page/change-forgotten-password-page.component';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { PasswordModule } from 'primeng/password';
import { TicketsPageComponent } from './pages/tickets-page/tickets-page.component';
import { CheckboxModule } from 'primeng/checkbox';
import { EditorModule } from 'primeng/editor';
import { TicketsAdministrationPageComponent } from './pages/tickets-administration-page/tickets-administration-page.component';
import { AllDormsPageComponent } from './pages/all-dorms-page/all-dorms-page.component';
import { DormAdministratorsPageComponent } from './pages/dorm-administrators-page/dorm-administrators-page.component';
import { RoomsAdministrationPageComponent } from './pages/rooms-administration-page/rooms-administration-page.component';
import { EvenimentsPageComponent } from './pages/eveniments-page/eveniments-page.component';
import { PanelModule } from 'primeng/panel';
import { CalendarModule } from 'primeng/calendar';
import { InputSwitchModule } from 'primeng/inputswitch';

@NgModule({
  declarations: [
    AppComponent,
    LaundryAppointmentsPageComponent,
    NavbarComponent,
    LoginPageComponent,
    ProfilePageComponent,
    NotFoundPageComponent,
    RegisterErrorDialogComponent,
    RegisterConfirmDialogComponent,
    StudentsAdministationPageComponent,
    RegisterRequestDetailsPopupComponent,
    EditRoomNumberDialogComponent,
    NewRegisterRequestDialogComponent,
    RemoveStudentFromDormDialogComponent,
    ProfilePageDisplayStudentDetailsComponent,
    ProfilePageDisplayDormAdministratorComponent,
    EditPhoneNumberDialogComponent,
    ChangePasswordDialogComponent,
    DormMachinesPageComponent,
    LoadingScreenComponent,
    KeepLaundryAppointmentPageComponent,
    ForgotPasswordPageComponent,
    ChangeForgottenPasswordPageComponent,
    TicketsPageComponent,
    TicketsAdministrationPageComponent,
    AllDormsPageComponent,
    DormAdministratorsPageComponent,
    RoomsAdministrationPageComponent,
    EvenimentsPageComponent,
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
    MatGridListModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatTabsModule,
    MatMenuModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatDialogModule,
    MatTableModule,
    MatPaginatorModule,

    // PrimeNG
    TableModule,
    TagModule,
    ButtonModule,
    DialogModule,
    ConfirmDialogModule,
    ToastModule,
    ToolbarModule,
    StepperModule,
    CascadeSelectModule,
    DropdownModule,
    InputTextModule,
    FloatLabelModule,
    ProgressSpinnerModule,
    FieldsetModule,
    CardModule,
    IconFieldModule,
    InputIconModule,
    PasswordModule,
    CheckboxModule,
    EditorModule,
    PanelModule,
    CalendarModule,
    InputSwitchModule,
  ],
  providers: [
    provideHttpClient(withFetch()),
    ConfirmationService,
    MessageService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
