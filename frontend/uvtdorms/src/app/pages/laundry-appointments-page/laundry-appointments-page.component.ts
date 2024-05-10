import { Component } from '@angular/core';
import { WashingMachine } from '../../interfaces/washing-machine';
import { Dryer } from '../../interfaces/dryer';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { AppointmentService } from '../../services/appointment.service';
import { WashingMachineService } from '../../services/washing-machine.service';
import { DryerService } from '../../services/dryer.service';
import { StudentDetailsService } from '../../services/student-details.service';
import { format } from 'date-fns';
import { GetFreeIntervalsDto } from '../../interfaces/get-free-intervals-dto';
import { FreeIntervalsDto } from '../../interfaces/free-intervals-dto';
import { CreateLaundryAppointmentDto } from '../../interfaces/create-laundry-appointment-dto';

import { ConfirmationService, MessageService } from 'primeng/api';

interface MachinePair {
  washingMachine: WashingMachine;
  dryer: Dryer;
}

@Component({
  selector: 'app-laundry-appointments-page',
  templateUrl: './laundry-appointments-page.component.html',
  styleUrl: './laundry-appointments-page.component.css',
})
export class LaundryAppointmentsPageComponent {
  private washingMachines: WashingMachine[] = [];
  private dryers: Dryer[] = [];
  public machines: MachinePair[] = [];
  private availableIntervals: number[] = [];
  private selectedInterval: number = 0;

  public tabs = [
    'Sunday',
    'Monday',
    'Tuesday',
    'Wednesday',
    'Thursday',
    'Friday',
    'Saturday',
  ];
  public currentDay = new Date().getDay();
  public selectedDay = new FormControl(this.currentDay);
  public intervals = [8, 10, 12, 14, 16, 18, 20];

  private dormId: string = '';
  laundryAppointmentForm = this.formBuilder.group({
    selectedMachineId: ['', Validators.required],
    selectedDryerId: ['', Validators.required],
    selectedDate: ['', Validators.required],
    selectedIntervalStartHour: [0, Validators.required],
  });

  constructor(
    private formBuilder: FormBuilder,
    private appointmentService: AppointmentService,
    private washingMachineService: WashingMachineService,
    private dryerService: DryerService,
    private studentService: StudentDetailsService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.studentService.getStudentDormId().subscribe({
      next: (dormId) => {
        this.dormId = dormId.id;
        this.getDryers();
        this.getWashingMachines();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  private getWashingMachines(): void {
    this.washingMachineService
      .getWashingMachinesFromDorm(this.dormId)
      .subscribe({
        next: (washingMachines) => {
          this.washingMachines = washingMachines;
          this.joinMachines();
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  private getDryers(): void {
    this.dryerService.getDryerFromDorm(this.dormId).subscribe({
      next: (dryers) => {
        this.dryers = dryers;
        this.joinMachines();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  private joinMachines(): void {
    for (let wahingMachine of this.washingMachines) {
      for (let dryer of this.dryers) {
        if (wahingMachine.associatedDryerId === dryer.id) {
          this.machines.push({
            washingMachine: wahingMachine,
            dryer: dryer,
          });
        }
      }
    }
  }

  public makeAppointment(): void {
    this.updateLaundryAppointmentForm();

    if (this.laundryAppointmentForm.invalid) {
      return;
    }

    this.confirmationService.confirm({
      header: 'Are you sure?',
      message:
        'Do you want to create an appointment for ' +
        this.getSelectedMachines()?.washingMachine.name +
        ' at ' +
        this.laundryAppointmentForm.value.selectedIntervalStartHour?.toString() +
        ':00 ?',
      accept: () => {
        this.sendCreateAppointmentRequest();
      },
    });
  }

  private updateLaundryAppointmentForm(): void {
    let selectedMachines = this.getSelectedMachines();
    this.laundryAppointmentForm.setValue({
      selectedMachineId: selectedMachines?.washingMachine.id!,
      selectedDryerId: selectedMachines?.dryer.id!,
      selectedDate: this.getSelectedDateString(),
      selectedIntervalStartHour: this.selectedInterval,
    });
  }

  private convertLaundryAppointmentFormToCreateAppointmentDto(): CreateLaundryAppointmentDto {
    let createAppointmentDto: CreateLaundryAppointmentDto = {
      selectedMachineId: this.laundryAppointmentForm.value.selectedMachineId!,
      selectedDryerId: this.laundryAppointmentForm.value.selectedDryerId!,
      selectedDate: this.laundryAppointmentForm.value.selectedDate!,
      selectedInterval:
        this.laundryAppointmentForm.value.selectedIntervalStartHour!,
    };

    return createAppointmentDto;
  }

  private sendCreateAppointmentRequest(): void {
    let createAppointmentDto: CreateLaundryAppointmentDto =
      this.convertLaundryAppointmentFormToCreateAppointmentDto();

    this.appointmentService.createAppointment(createAppointmentDto).subscribe({
      next: () => {
        this.displayConfirmMessage();
        this.updateIntervals();
      },
      error: (error) => {
        this.displayErrorMessage(error);
      },
    });
  }

  private displayErrorMessage(error: any): void {
    let errorMsg: string = error.error.message;
    if (errorMsg === 'The student already has an appointment for this week') {
      errorMsg = 'You already have an appointment for this week.';
    }
    this.messageService.add({
      severity: 'error',
      summary: 'Something went wrong',
      detail: errorMsg,
      sticky: true,
    });
  }

  private displayConfirmMessage(): void {
    this.messageService.add({
      severity: 'success',
      summary: 'Confirmed',
      detail: 'Appointment successfuly created!',
      life: 3000,
    });
  }

  public getSelectedMachines(): MachinePair | null {
    for (let machine of this.machines) {
      if (
        machine.washingMachine.id ===
        this.laundryAppointmentForm.get('selectedMachineId')?.value
      ) {
        return machine;
      }
    }
    return null;
  }

  private getSelectedDate(): Date {
    const date = new Date();
    const resultDate = new Date(date.getTime());
    resultDate.setDate(
      date.getDate() + ((this.selectedDay.value! - date.getDay()) % 7)
    );

    return resultDate;
  }

  private getSelectedDateString(): string {
    return format(this.getSelectedDate(), 'yyyy-MM-dd');
  }

  public updateIntervals(): void {
    this.selectedInterval = 0;
    let selectedMachines = this.getSelectedMachines();

    let getFreeIntervalsDto: GetFreeIntervalsDto = {
      dormId: this.dormId,
      date: this.getSelectedDateString(),
      washingMachineId: selectedMachines?.washingMachine.id!,
      dryerId: selectedMachines?.dryer.id!,
    };

    this.appointmentService
      .getFreeIntervalsForCreatingAppointment(getFreeIntervalsDto)
      .subscribe({
        next: (intervals: FreeIntervalsDto) => {
          this.availableIntervals = [];
          let currentHour = new Date().getHours();
          let today = new Date().getDate();
          let selectedDate = this.getSelectedDate().getDate();
          for (let interval of intervals.freeIntervals) {
            if (interval > currentHour || today != selectedDate)
              this.availableIntervals.push(interval);
          }
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  public getSelectedDayName() {
    return this.tabs[this.selectedDay.value!];
  }

  public isIntervalAvailable(interval: number) {
    for (let availableInterval of this.availableIntervals) {
      if (availableInterval === interval) return true;
    }
    return false;
  }

  public isIntervalSelected(interval: number) {
    return this.selectedInterval === interval;
  }

  public selectInterval(interval: number) {
    if (!this.isIntervalAvailable(interval)) return;
    this.selectedInterval = interval;
    this.laundryAppointmentForm.value.selectedIntervalStartHour = interval;
  }
}
