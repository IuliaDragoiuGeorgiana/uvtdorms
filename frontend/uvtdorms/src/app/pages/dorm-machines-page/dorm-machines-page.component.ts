import { Component } from '@angular/core';
import { WashingMachine } from '../../interfaces/washing-machine';
import { DormService } from '../../services/dorm.service';
import { WashingMachineService } from '../../services/washing-machine.service';
import { DormAdministratorDetailsService } from '../../services/dorm-administrator-details.service';
import { DryerService } from '../../services/dryer.service';
import { Dryer } from '../../interfaces/dryer';
import { AppointmentService } from '../../services/appointment.service';
import { LaundryAppointmentDto } from '../../interfaces/laundry-appointment-dto';
import { MachineType } from '../../enums/machine-type';

@Component({
  selector: 'app-dorm-machines-page',
  templateUrl: './dorm-machines-page.component.html',
  styleUrl: './dorm-machines-page.component.css',
})
export class DormMachinesPageComponent {
  selectMachine(arg0: any) {
    throw new Error('Method not implemented.');
  }
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
        console.log(washingMachines);
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

  public getStatus(washingMachine: WashingMachine): string {
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

  public getStatusSeverity(washingMachine: WashingMachine): string {
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

  public getAssociatedDryerName(washingMachine: WashingMachine): string {
    let associatedDryer = this.dryers.find(
      (dryer) => dryer.id === washingMachine.associatedDryerId
    );
    return associatedDryer?.name ?? 'No associated dryer';
  }

  public getWeeklyAppointments(
    washingMachine: WashingMachine
  ): LaundryAppointmentDto[] {
    if (washingMachine.weeklyAppointments === null) {
      console.log(washingMachine.id);
      this.laundryAppointmentService
        .getWeeklyAppointmentsForDormForWashingMachine(washingMachine.id)
        .subscribe({
          next: (appointments) => {
            console.log(appointments);
            washingMachine.weeklyAppointments = appointments;
          },
          error(err) {
            console.error(err);
          },
        });
    }

    return washingMachine.weeklyAppointments ?? [];
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


}
