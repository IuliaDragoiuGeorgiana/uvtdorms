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

@Component({
  selector: 'app-dorm-machines-page',
  templateUrl: './dorm-machines-page.component.html',
  styleUrl: './dorm-machines-page.component.css',
})
export class DormMachinesPageComponent {
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
  };

  public machineType = MachineType;
  public selectedMachineType: MachineType | null = null;
  private availableWashingMachines: AvailableWashingMachineDto[] = [
    {
      id: '',
      name: 'No associated washing machine',
    },
  ];
  private availableDryers: AvailableDryerDto[] = [
    {
      id: '',
      name: 'No associated dryer',
    },
  ];

  public newMachineForm = new FormGroup({
    name: new FormControl('', Validators.required),
    associatedDryerOrWashingMachineId: new FormControl(''),
  });

  public listedMachinesType: MachineType = MachineType.WASHING_MACHINE;
  public listableMachineTypes = [
    { type: MachineType.WASHING_MACHINE, name: 'Washing machines' },
    { type: MachineType.DRYER, name: 'Dryers' },
  ];

  constructor(
    private dormAdministratorDetailsService: DormAdministratorDetailsService,
    private washingMachineService: WashingMachineService,
    private dryerService: DryerService,
    private laundryAppointmentService: AppointmentService
  ) {
    this.dormAdministratorDetailsService.getAdministratedDormId().subscribe({
      next: (dormIdDto) => {
        this.dormId = dormIdDto.id;
        this.getWashingMachines();
        this.getDryers();
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private getWashingMachines(): void {
    this.washingMachineService
      .getWashingMachinesFromDorm(this.dormId)
      .subscribe({
        next: (washingMachines) => {
          this.washingMachines = [];
          washingMachines.forEach((washingMachine) => {
            this.washingMachines.push({
              id: washingMachine.id,
              name: washingMachine.name,
              isAvailable: washingMachine.isAvailable,
              associatedDryerId: washingMachine.associatedDryerId,
              weeklyAppointments: null,
            });
          });
        },
        error(err) {
          console.error(err);
        },
      });
  }

  private getDryers(): void {
    this.dryerService.getDryerFromDorm(this.dormId).subscribe({
      next: (dryers) => {
        console.log(dryers);
        this.dryers = dryers;
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private getAvailableWashingMachine(): void {
    this.washingMachineService.getAvailableWashingMachine().subscribe({
      next: (washingMachines) => {
        this.availableWashingMachines.push(...washingMachines);
        console.log(washingMachines);
      },
      error(err) {
        console.error(err);
      },
    });
  }

  private getAvailableDryer(): void {
    this.dryerService.getAvailableDryer().subscribe({
      next: (dryers) => {
        this.availableDryers.push(...dryers);
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
    if (!washingMachine.isAvailable) {
      return 'Not available';
    }

    let associatedDryer = this.dryers.find(
      (dryer) => dryer.id === washingMachine.associatedDryerId
    );

    if (associatedDryer === undefined) {
      return 'No associated dryer';
    }

    if (!associatedDryer?.isAvailable) {
      return 'Dryer not available';
    }

    return 'Available';
  }

  private getDryerStatus(dryer: Dryer): string {
    if (!dryer.isAvailable) {
      return 'Not available';
    }

    let associatedWashingMachine = this.washingMachines.find(
      (washingMachine) => washingMachine.associatedDryerId === dryer.id
    );

    if (associatedWashingMachine === undefined) {
      return 'No associated washing machine';
    }

    if (!associatedWashingMachine?.isAvailable) {
      return 'Washing machine not available';
    }

    return 'Available';
  }

  public getStatus(machine: any): string {
    if (this.isWashingMachineLabelingSelected()) {
      return this.getWashingMachineStatus(machine);
    } else if (this.isDryerLabelingSelected()) {
      return this.getDryerStatus(machine);
    }
    return '';
  }

  private getWashingMachineStatusSeverity(
    washingMachine: WashingMachine
  ): string {
    if (!washingMachine.isAvailable) {
      return 'danger';
    }

    let associatedDryer = this.dryers.find(
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
    let associatedDryer = this.dryers.find(
      (dryer) => dryer.id === washingMachine.associatedDryerId
    );
    return associatedDryer?.name ?? 'No associated dryer';
  }

  public getAssociatedWashingMachineName(dryer: Dryer): string {
    let associatedWashingMachine = this.washingMachines.find(
      (washingMachine) => washingMachine.associatedDryerId === dryer.id
    );
    return associatedWashingMachine?.name ?? 'No associated washing machine';
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

  public formatDate(date: string[]): string {
    return (
      date[0] +
      '-' +
      (date[1].toString.length < 2 ? '0' + date[1] : date[1]) +
      '-' +
      (date[2].toString.length < 2 ? '0' + date[2] : date[2]) +
      ' ' +
      (date[3].toString.length < 2 ? '0' + date[3] : date[3]) +
      ':' +
      (date[4].toString.length < 2 ? '0' + date[4] : date[4])
    );
  }

  public openNew(): void {
    this.addSomething = true;
  }

  public hideDialog(): void {
    this.addSomething = false;
  }

  public saveSomething(): void {
    this.submitted = true;
    this.addSomething = false;
    console.log(this.washingMachine);
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
          console.log('Dryer created');
          window.location.reload();
        },
        error(err) {
          console.error(err);
        },
      });
    } else if (this.isWashingMachineSelected()) {
      this.washingMachineService.createWashingMachine(newMachineDto).subscribe({
        next: () => {
          console.log('Washing machine created');
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
}
