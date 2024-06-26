import { Component } from '@angular/core';
import { WashingMachine } from '../../interfaces/washing-machine';
import { WashingMachineService } from '../../services/washing-machine.service';
import { DormAdministratorDetailsService } from '../../services/dorm-administrator-details.service';
import { DryerService } from '../../services/dryer.service';
import { Dryer } from '../../interfaces/dryer';
import { AppointmentService } from '../../services/appointment.service';
import { LaundryAppointmentDto } from '../../interfaces/laundry-appointment-dto';
import { MachineType } from '../../enums/machine-type';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AvailableDryerDto } from '../../interfaces/available-dryer-dto';
import { AvailableWashingMachineDto } from '../../interfaces/available-washing-machine-dto';
import { NewMachineDto } from '../../interfaces/new-machine-dto';
import {
  StatusMachine,
  convertStringStatusMachineToEnum,
} from '../../enums/status-machine';
import { TranslateService } from '@ngx-translate/core';
import { OverlayListenerOptions, OverlayOptions } from 'primeng/api';

@Component({
  selector: 'app-dorm-machines-page',
  templateUrl: './dorm-machines-page.component.html',
  styleUrl: './dorm-machines-page.component.css',
})
export class DormMachinesPageComponent {
  public isLoadingScreenVisible: boolean = false;
  private dormId!: string;
  public washingMachines!: WashingMachine[];
  public dryers!: Dryer[];
  public addSomething: boolean = false;
  public submitted: boolean = false;
  public washingMachine: WashingMachine = {
    id: '',
    name: '',
    isAvailable: true,
    associatedDryerId: '',
    weeklyAppointments: null,
    statusMachine: StatusMachine.FUNCTIONAL,
  };

  constructor(
    private dormAdministratorDetailsService: DormAdministratorDetailsService,
    private washingMachineService: WashingMachineService,
    private dryerService: DryerService,
    private laundryAppointmentService: AppointmentService,
    private translate: TranslateService
  ) {
    this.showLoadingScreen();
    this.dormAdministratorDetailsService.getAdministratedDormId().subscribe({
      next: (dormIdDto) => {
        this.dormId = dormIdDto.id;
        this.getWashingMachines();
        this.getDryers();
        this.hideLoadingScreen();
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private showLoadingScreen(): void {
    this.isLoadingScreenVisible = true;
  }

  private hideLoadingScreen(): void {
    this.isLoadingScreenVisible = false;
  }

  public machineType = MachineType;
  public selectedMachineType: MachineType | null = null;

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

  private initialAvailableWashingMachines: AvailableWashingMachineDto[] = [
    {
      id: '',
      name: this.translate.instant(
        'dormMachine.initialAvailableWashingMachine.name'
      ),
    },
  ];
  private availableWashingMachines: AvailableWashingMachineDto[] = [
    this.initialAvailableWashingMachines[0],
  ];

  private initialAvailableDryers: AvailableDryerDto[] = [
    {
      id: '',
      name: this.translate.instant('dormMachine.initialAvailableDryer.name'),
    },
  ];
  private availableDryers: AvailableDryerDto[] = [
    this.initialAvailableDryers[0],
  ];

  private selectedMachineAvailablePair:
    | AvailableDryerDto
    | AvailableWashingMachineDto
    | null = null;

  public newMachineForm = new FormGroup({
    name: new FormControl('', Validators.required),
    associatedDryerOrWashingMachineId: new FormControl(''),
  });

  public listedMachinesType: MachineType = MachineType.WASHING_MACHINE;
  public listableMachineTypes = [
    {
      type: MachineType.WASHING_MACHINE,
      name: this.translate.instant(
        'dormMachine.listableMachine.WashingMachine'
      ),
    },
    {
      type: MachineType.DRYER,
      name: this.translate.instant('dormMachine.listableMachine.Dryer'),
    },
  ];

  public isEditDialogVisible: boolean = false;
  public machineStatusOptions = [
    {
      label: this.translate.instant(
        'dormMachine.machineStatus.label.functional'
      ),
      value: StatusMachine.FUNCTIONAL,
    },
    {
      label: this.translate.instant('dormMachine.machineStatus.label.broken'),
      value: StatusMachine.BROKEN,
    },
  ];
  public newMachineStatus: StatusMachine = StatusMachine.FUNCTIONAL;
  private editedMachine: WashingMachine | Dryer | null = null;
  public newAssociatedMachine: string = '';
  public newMachineName: string = '';

  private getWashingMachines(): void {
    this.showLoadingScreen();
    this.washingMachineService
      .getWashingMachinesFromDorm(this.dormId)
      .subscribe({
        next: (washingMachines) => {
          this.washingMachines = washingMachines;
          this.hideLoadingScreen();
        },
        error(err) {
          console.error(err);
        },
      });
  }

  private getDryers(): void {
    this.showLoadingScreen();
    this.dryerService.getDryerFromDorm(this.dormId).subscribe({
      next: (dryers) => {
        this.dryers = dryers;
        this.hideLoadingScreen();
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private getAvailableWashingMachine(): void {
    this.availableWashingMachines = [this.initialAvailableWashingMachines[0]];
    this.washingMachineService.getAvailableWashingMachine().subscribe({
      next: (washingMachines) => {
        this.availableWashingMachines = [
          this.initialAvailableWashingMachines[0],
          ...washingMachines,
        ];
        if (this.selectedMachineAvailablePair !== null) {
          this.availableWashingMachines.push(this.selectedMachineAvailablePair);
        }
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private getAvailableDryer(): void {
    this.dryerService.getAvailableDryer().subscribe({
      next: (dryers) => {
        this.availableDryers = [this.initialAvailableDryers[0], ...dryers];
        if (this.selectedMachineAvailablePair !== null) {
          this.availableDryers.push(this.selectedMachineAvailablePair);
        }
      },
      error(err) {
        console.error(err);
      },
    });
  }

  public isWashingMachineAvailable(washingMachine: WashingMachine): boolean {
    return washingMachine.isAvailable;
  }

  public isDryerAvailable(dryer: Dryer): boolean {
    return dryer.isAvailable;
  }

  private getWashingMachineStatus(washingMachine: WashingMachine): string {
    if (!this.isWashingMachineFunctional(washingMachine.statusMachine)) {
      return this.translate.instant('dormMachine.wmStatus.notAvailable');
    }

    let associatedDryer = this.dryers?.find(
      (dryer) => dryer.id === washingMachine.associatedDryerId
    );

    if (associatedDryer === undefined) {
      return this.translate.instant('dormMachine.wmStatus.noAssociatedDryer');
    }

    if (!associatedDryer?.isAvailable) {
      return this.translate.instant('dormMachine.wmStatus.dryerNotAvailable');
    }

    return this.translate.instant('dormMachine.wmStatus.available');
  }

  private getDryerStatus(dryer: Dryer): string {
    if (!dryer.isAvailable) {
      return this.translate.instant('dormMachine.dStatus.notAvailable');
    }

    let associatedWashingMachine = this.washingMachines.find(
      (washingMachine) => washingMachine.associatedDryerId === dryer.id
    );

    if (associatedWashingMachine === undefined) {
      return this.translate.instant(
        'dormMachine.dStatus.noAssociatedWashingMachine'
      );
    }

    if (!associatedWashingMachine?.isAvailable) {
      return this.translate.instant(
        'dormMachine.dStatus.washingMachineNotAvailable'
      );
    }

    return this.translate.instant('dormMachine.dStatus.available');
  }

  public getStatus(machine: any): string {
    if (this.isWashingMachineLabelingSelected()) {
      return this.getWashingMachineStatus(machine);
    } else if (this.isDryerLabelingSelected()) {
      return this.getDryerStatus(machine);
    }
    return '';
  }

  private isWashingMachineFunctional(status: any) {
    if (status === 'FUNCTIONAL') {
      return true;
    }
    return false;
  }

  private getWashingMachineStatusSeverity(
    washingMachine: WashingMachine
  ): string {
    if (!this.isWashingMachineFunctional(washingMachine.statusMachine)) {
      return 'danger';
    }

    let associatedDryer = this.dryers?.find(
      (dryer) => dryer.id === washingMachine.associatedDryerId
    );

    if (associatedDryer === undefined) {
      return 'warning';
    }

    if (!associatedDryer?.isAvailable) {
      return 'warning';
    }

    return 'success';
  }

  private getDryerStatusSeverity(dryer: Dryer): string {
    if (!dryer.isAvailable) {
      return 'danger';
    }

    let associatedWashingMachine = this.washingMachines.find(
      (washingMachine) => washingMachine.associatedDryerId === dryer.id
    );

    if (associatedWashingMachine === undefined) {
      return 'warning';
    }

    if (!associatedWashingMachine?.isAvailable) {
      return 'warning';
    }

    return 'success';
  }

  public getStatusSeverity(machine: any): string {
    if (this.isWashingMachineLabelingSelected()) {
      return this.getWashingMachineStatusSeverity(machine);
    } else if (this.isDryerLabelingSelected()) {
      return this.getDryerStatusSeverity(machine);
    }
    return '';
  }

  public getAssociatedDryerName(washingMachine: WashingMachine): string {
    let associatedDryer = this.dryers?.find(
      (dryer) => dryer.id === washingMachine.associatedDryerId
    );
    return (
      associatedDryer?.name ??
      this.translate.instant('dormMachine.wmStatus.noAssociatedDryer')
    );
  }

  public getAssociatedWashingMachineName(dryer: Dryer): string {
    let associatedWashingMachine = this.washingMachines.find(
      (washingMachine) => washingMachine.associatedDryerId === dryer.id
    );
    return (
      associatedWashingMachine?.name ??
      this.translate.instant('dormMachine.dStatus.noAssociatedWashingMachine')
    );
  }

  private getWeeklyAppointmentsForWashingMachine(
    washingMachine: WashingMachine
  ): void {
    if (washingMachine.weeklyAppointments != null) return;
    this.laundryAppointmentService
      .getWeeklyAppointmentsForDormForWashingMachine(washingMachine.id)
      .subscribe({
        next: (appointments) => {
          washingMachine.weeklyAppointments = appointments;
        },
        error(err) {
          console.error(err);
        },
      });
  }

  private getWeeklyAppointmentsForDryer(dryer: Dryer): void {
    if (dryer.weeklyAppointments != null) return;
    this.laundryAppointmentService
      .getWeeklyAppointmentsForDormForDryer(dryer.id)
      .subscribe({
        next: (appointments) => {
          dryer.weeklyAppointments = appointments;
        },
        error(err) {
          console.error(err);
        },
      });
  }

  public getWeeklyAppointments(machine: any): LaundryAppointmentDto[] {
    if (this.isWashingMachineLabelingSelected()) {
      this.getWeeklyAppointmentsForWashingMachine(machine);
    } else if (this.isDryerLabelingSelected()) {
      this.getWeeklyAppointmentsForDryer(machine);
    }

    return machine.weeklyAppointments ?? [];
  }

  public formatDate(date: any[]): string {
    return (
      date[0] +
      '-' +
      (date[1] < 10 ? '0' + date[1] : date[1]) +
      '-' +
      (date[2] < 10 ? '0' + date[2] : date[2]) +
      ' ' +
      (date[3] < 10 ? '0' + date[3] : date[3]) +
      ':' +
      (date[4] < 10 ? '0' + date[4] : date[4])
    );
  }

  public openNew(): void {
    this.addSomething = true;
    this.getAvailableDryer();
    this.getAvailableWashingMachine();
  }

  public hideDialog(): void {
    this.addSomething = false;
  }

  public saveSomething(): void {
    this.submitted = true;
    this.addSomething = false;
  }

  public selectMachine(machineType: MachineType): void {
    this.selectedMachineType = machineType;

    if (this.isDryerSelected()) {
      this.getAvailableWashingMachine();
    } else if (this.isWashingMachineSelected()) {
      this.getAvailableDryer();
    }
  }

  public isDryerSelected(): boolean {
    return this.machineType.DRYER === this.selectedMachineType;
  }

  public isWashingMachineSelected(): boolean {
    return this.machineType.WASHING_MACHINE === this.selectedMachineType;
  }

  public getAvailableMachines(): any[] {
    if (this.isDryerSelected()) {
      return this.availableWashingMachines;
    } else if (this.isWashingMachineSelected()) {
      return this.availableDryers;
    }
    return [];
  }

  public getAvailableMachinesForEditing(): any[] {
    if (this.isDryerLabelingSelected()) {
      return this.availableWashingMachines;
    } else if (this.isWashingMachineLabelingSelected()) {
      return this.availableDryers;
    }

    return [];
  }

  public getAssociatedMachineName(): string {
    for (let machine of this.getAvailableMachines()) {
      if (
        machine.id ===
        this.newMachineForm.value.associatedDryerOrWashingMachineId
      ) {
        return machine.name;
      }
    }

    return '';
  }

  public submitNewMachineForm() {
    if (this.newMachineForm.invalid) return;
    let newMachineDto: NewMachineDto = {
      name: this.newMachineForm.value.name!,
      associatedDryerOrWashingMachineId:
        this.newMachineForm.value.associatedDryerOrWashingMachineId!,
    };
    if (this.isDryerSelected()) {
      this.dryerService.createDryer(newMachineDto).subscribe({
        next: () => {
          window.location.reload();
        },
        error(err) {
          console.error(err);
        },
      });
    } else if (this.isWashingMachineSelected()) {
      this.washingMachineService.createWashingMachine(newMachineDto).subscribe({
        next: () => {
          window.location.reload();
        },
        error(err) {
          console.error(err);
        },
      });
    }
  }

  public isWashingMachineLabelingSelected(): boolean {
    return this.listedMachinesType === MachineType.WASHING_MACHINE;
  }

  public isDryerLabelingSelected(): boolean {
    return this.listedMachinesType === MachineType.DRYER;
  }

  public editMachine(machine: any): void {
    this.isEditDialogVisible = true;

    this.newMachineStatus = convertStringStatusMachineToEnum(
      machine.statusMachine
    )!;

    this.editedMachine = machine;
    this.newMachineName = machine.name;

    if (this.isWashingMachineLabelingSelected()) {
      this.getAvailableDryer();
      if (
        machine.associatedDryerId !== null &&
        machine.associatedDryerId !== ''
      ) {
        this.selectedMachineAvailablePair = {
          id: machine.associatedDryerId,
          name: this.getAssociatedDryerName(machine),
        };
      } else {
        this.selectedMachineAvailablePair = null;
      }
      this.newAssociatedMachine =
        machine.associatedDryerId !== null ? machine.associatedDryerId : '';
    } else if (this.isDryerLabelingSelected()) {
      this.getAvailableWashingMachine();
      if (
        machine.associatedWashingMachineId !== null &&
        machine.associatedWashingMachineId !== ''
      ) {
        this.selectedMachineAvailablePair = {
          id: machine.associatedWashingMachineId,
          name: this.getAssociatedWashingMachineName(machine),
        };
      } else {
        this.selectedMachineAvailablePair = null;
      }
      this.newAssociatedMachine =
        machine.associatedWashingMachineId !== null
          ? machine.associatedWashingMachineId
          : '';
    }
  }

  private saveWashingMachine(washingMachine: WashingMachine): void {
    washingMachine.statusMachine = this.newMachineStatus;
    washingMachine.associatedDryerId = this.newAssociatedMachine;
    washingMachine.name = this.newMachineName;
    this.showLoadingScreen();
    this.washingMachineService.updateWashingMachine(washingMachine).subscribe({
      next: () => {
        this.getWashingMachines();
        this.getAvailableDryer();
        this.getAvailableWashingMachine();
        this.hideLoadingScreen();
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private saveDryer(dryer: Dryer): void {
    dryer.statusMachine = this.newMachineStatus;
    dryer.associatedWashingMachineId = this.newAssociatedMachine;
    dryer.name = this.newMachineName;
    this.showLoadingScreen();
    this.dryerService.updateDryer(dryer).subscribe({
      next: () => {
        this.getDryers();
        this.getWashingMachines();
        this.getAvailableDryer();
        this.getAvailableWashingMachine();
        this.hideLoadingScreen();
      },
      error(err) {
        console.error(err);
      },
    });
  }

  public saveMachine(): void {
    if (this.isWashingMachineLabelingSelected()) {
      this.saveWashingMachine(this.editedMachine! as WashingMachine);
    } else if (this.isDryerLabelingSelected()) {
      this.saveDryer(this.editedMachine! as Dryer);
    }
    this.isEditDialogVisible = false;
  }
}
