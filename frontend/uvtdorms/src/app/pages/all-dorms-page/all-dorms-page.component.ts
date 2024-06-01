import { Component } from '@angular/core';
import { DormService } from '../../services/dorm.service';
import { DormDto } from '../../interfaces/dorm-dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DormAdministratorDto } from '../../interfaces/dorm-administrator-dto';
import { DormAdministratorDetailsService } from '../../services/dorm-administrator-details.service';
import { UpdateDormAdministratorDto } from '../../interfaces/update-dorm-administrator-dto';
import { DormId } from '../../interfaces/dorm-id';
import {
  ConfirmationService,
  MessageService,
  OverlayListenerOptions,
  OverlayOptions,
} from 'primeng/api';

@Component({
  selector: 'app-all-dorms-page',
  templateUrl: './all-dorms-page.component.html',
  styleUrl: './all-dorms-page.component.css',
})
export class AllDormsPageComponent {
  public dorms: DormDto[] = [];
  public defaultAvailableAdministrators: DormAdministratorDto[] = [
    { name: 'No administrator', email: '' },
  ];
  public availableAdministrators: DormAdministratorDto[] = [];
  public loading: boolean = true;
  public isAddNewDormDialogVisible: boolean = false;

  public editDormNewAdministratorEmail: string = '';

  public addNewDormForm: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    administratorEmail: new FormControl(''),
  });

  constructor(
    private dormService: DormService,
    private dormAdministratorsService: DormAdministratorDetailsService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.updateDormsList();
    this.updateAvailableDormAdministratorsList();
  }

  private updateAvailableDormAdministratorsList(): void {
    this.loading = true;
    this.dormAdministratorsService.getAvailableDormAdministrators().subscribe({
      next: (administrators: DormAdministratorDto[]) => {
        this.availableAdministrators = administrators;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  private updateDormsList(): void {
    this.loading = true;
    this.dormService.getDorms().subscribe({
      next: (dorms: DormDto[]) => {
        this.dorms = dorms;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  public submitAddNewDormForm(): void {
    this.addNewDormForm.markAllAsTouched();
    this.addNewDormForm.markAsDirty();
    this.addNewDormForm.updateValueAndValidity();
    if (this.addNewDormForm.invalid) {
      return;
    }

    let newDorm: DormDto = {
      name: this.addNewDormForm.controls['name'].value,
      address: this.addNewDormForm.controls['address'].value,
      administratorEmail:
        this.addNewDormForm.controls['administratorEmail'].value,
      administratorName: '',
      id: '',
    };

    this.dormService.addDorm(newDorm).subscribe({
      next: () => {
        this.dorms.push(newDorm);
        this.availableAdministrators = this.availableAdministrators.filter(
          (admin) => admin.email !== newDorm.administratorEmail
        );
        this.addNewDormForm.reset();
        this.isAddNewDormDialogVisible = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Dorm added successfully',
        });
        this.updateDormsList();
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  public getAvailableAdministratorsForEditingDorm(dorm: DormDto): any[] {
    if (dorm.administratorEmail === '' || dorm.administratorEmail === null) {
      return this.defaultAvailableAdministrators.concat(
        this.availableAdministrators
      );
    }

    return this.defaultAvailableAdministrators
      .concat(this.availableAdministrators)
      .concat({ name: dorm.administratorName, email: dorm.administratorEmail });
  }

  public editDorm(dorm: DormDto): void {
    this.editDormNewAdministratorEmail = dorm.administratorEmail ?? '';
  }

  public saveEditedDorm(dormDto: DormDto): void {
    let updateDormAdministratorDto: UpdateDormAdministratorDto = {
      dormId: dormDto.id,
      administratorEmail: this.editDormNewAdministratorEmail,
    };

    this.dormService
      .updateDormAdministrator(updateDormAdministratorDto)
      .subscribe({
        next: () => {
          dormDto.administratorEmail = this.editDormNewAdministratorEmail;
          dormDto.administratorName =
            this.availableAdministrators.find(
              (admin) => admin.email === this.editDormNewAdministratorEmail
            )?.name ?? '';
          this.updateAvailableDormAdministratorsList();
        },
        error: (error) => {
          console.error(error);
        },
      });
  }

  public deleteDorm(dorm: DormDto): void {
    this.confirmationService.confirm({
      header: 'Are you sure?',
      message: 'You will permanently delete this dorm.',
      accept: () => {
        let dormId: DormId = { id: dorm.id };
        this.dormService.deleteDorm(dormId).subscribe({
          next: () => {
            this.dorms = this.dorms.filter((d) => d.id !== dorm.id);
            this.updateAvailableDormAdministratorsList();
            this.messageService.add({
              severity: 'info',
              summary: 'Confirmation',
              detail: 'You have deleted the dorm',
              life: 3000,
            });
          },
          error: (error) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Failed to delete the dorm: ' + error.error.message,
            });
            console.error(error);
          },
        });
      },
      reject: () => {},
    });
  }

  get addNewFormNameRequiredError(): boolean {
    return this.addNewDormForm.controls['name'].hasError('required');
  }

  get addNewFormAddressRequiredError(): boolean {
    return this.addNewDormForm.controls['address'].hasError('required');
  }

  getOverlayOptions(): OverlayOptions {
    return {
      listener: (event: Event, options?: OverlayListenerOptions) => {
        if (options?.type === 'scroll') {
          return false;
        }
        return options?.valid;
      },
    };
  }
}
