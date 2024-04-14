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
  public selectedMachines: MachinePair | null = null;
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
    selectedDate: [Date.now() || '', Validators.required],
    selectedIntervalStartHour: [0, Validators.required],
  });

  constructor(
    private formBuilder: FormBuilder,
    private appointmentService: AppointmentService,
    private washingMachineService: WashingMachineService,
    private dryerService: DryerService,
    private studentService: StudentDetailsService
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
    for (
      let i = 0;
      i < this.washingMachines.length && i < this.dryers.length;
      i++
    ) {
      this.machines.push({
        washingMachine: this.washingMachines[i],
        dryer: this.dryers[i],
      });
    }
  }

  public makeAppointment(): void {
    this.selectedMachines = this.getSelectedMachines();
    this.laundryAppointmentForm.value.selectedDryerId =
      this.selectedMachines?.dryer.id;

    console.log(this.laundryAppointmentForm.value);
    if (!this.laundryAppointmentForm.valid) return;

    let createAppointmentDto: CreateLaundryAppointmentDto = {
      selectedMachineId: this.selectedMachines?.washingMachine.id!,
      selectedDryerId: this.selectedMachines?.dryer.id!,
      selectedDate: this.getSelectedDateString(),
      selectedInterval:
        this.laundryAppointmentForm.value.selectedIntervalStartHour!,
    };

    this.appointmentService.createAppointment(createAppointmentDto).subscribe({
      next: () => {
        console.log('Appointment created');
        // ADD popup message
      },
      error: (error) => {
        console.log(error);
        // ADD popup message
      },
    });
  }

  private getSelectedMachines(): MachinePair | null {
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
    this.selectedMachines = this.getSelectedMachines();

    let getFreeIntervalsDto: GetFreeIntervalsDto = {
      dormId: this.dormId,
      date: this.getSelectedDateString(),
      washingMachineId: this.selectedMachines?.washingMachine.id!,
      dryerId: this.selectedMachines?.dryer.id!,
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
    this.selectedInterval = interval;
    this.laundryAppointmentForm.value.selectedIntervalStartHour = interval;
  }
}
