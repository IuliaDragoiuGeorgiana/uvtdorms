import { Component } from '@angular/core';
import { DormService } from '../../services/dorm.service';
import { DormDto } from '../../interfaces/dorm-dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DormAdministratorDto } from '../../interfaces/dorm-administrator-dto';
import { DormAdministratorDetailsService } from '../../services/dorm-administrator-details.service';

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

  public addNewDormForm: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    administratorEmail: new FormControl(''),
  });

  constructor(
    private dormService: DormService,
    private dormAdministratorsService: DormAdministratorDetailsService
  ) {}

  ngOnInit(): void {
    this.dormService.getDorms().subscribe({
      next: (dorms: DormDto[]) => {
        this.dorms = dorms;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
      },
    });

    this.dormAdministratorsService.getAvailableDormAdministrators().subscribe({
      next: (administrators: DormAdministratorDto[]) => {
        this.availableAdministrators = administrators;
        console.log(this.availableAdministrators);
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
      administratorEmail: this.addNewDormForm.controls['administratorEmail'].value,
    };

    this.dormService.addDorm(newDorm).subscribe({
      next: () => {
        this.dorms.push(newDorm);
        this.isAddNewDormDialogVisible = false;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  get addNewFormNameRequiredError(): boolean {
    return this.addNewDormForm.controls['name'].hasError('required');
  }

  get addNewFormAddressRequiredError(): boolean {
    return this.addNewDormForm.controls['address'].hasError('required');
  }
}
