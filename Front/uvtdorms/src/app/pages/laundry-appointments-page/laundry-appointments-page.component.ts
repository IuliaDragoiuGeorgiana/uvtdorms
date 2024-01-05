import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WashingMachine } from '../../interfaces/washing-machine';
import { timeInterval } from '../../interfaces/time-interval';
import { Dryer } from '../../interfaces/dryer';
import { FormBuilder, Validators } from '@angular/forms';
import { AppointmentService } from '../../services/appointment.service';
import { error } from 'console';
import { WashingMachineService } from '../../services/washing-machine.service';
import { DryerService } from '../../services/dryer.service';

@Component({
  selector: 'app-laundry-appointments-page',
  templateUrl: './laundry-appointments-page.component.html',
  styleUrl: './laundry-appointments-page.component.css'
})
export class LaundryAppointmentsPageComponent {
  washingMachines: WashingMachine[] = [];

  dryers: Dryer[] = [];

  timeIntervals: timeInterval[] = [
    {startHour: 8, printableValue: '8:00-10:00'},
    {startHour: 10, printableValue: '10:00-12:00'},
    {startHour: 12, printableValue: '12:00-14:00'},
    {startHour: 14, printableValue: '14:00-16:00'},
    {startHour: 16, printableValue: '16:00-18:00'},
    {startHour: 18, printableValue: '18:00-20:00'}
  ];
  
  dormId:string ="02432b00-b1fa-481e-aaa6-f1592823cba7";
  laundryAppointmentForm = this.formBuilder.group({
    selectedMachineId: ['', Validators.required],
    selectedDryerId: ['', Validators.required],
    selectedDate: [this.getTomorrowDate() || null, Validators.required],
    selectedIntervalStartHour: [0, Validators.required]
  });

  constructor(private snackBar: MatSnackBar,
              private formBuilder: FormBuilder,
              private appointmentService: AppointmentService,
              private washingMachineService: WashingMachineService,
              private dryerService: DryerService)
  {
  }

  ngOnInit()
  {
    if (this.timeIntervals && this.timeIntervals.length > 0) {
      this.laundryAppointmentForm.get('selectedIntervalStartHour')?.setValue(this.timeIntervals[0].startHour);
    }
    this.washingMachineService.getWashingMachinesFromDorm(this.dormId).subscribe({
      next:washingMachines=>{
       this.washingMachines=washingMachines;
       console.log(washingMachines);
      },
      error:(error)=>{
        console.log(error);
      }
    })

    this.dryerService.getDryerFromDorm(this.dormId).subscribe({
      next:dryers=>{
       this.dryers=dryers;
       console.log(this.dryers);
      },
      error:(error)=>{
        console.log(error);
      }
    })

  }

  getTomorrowDate(): Date{
    const date = new Date();
    date.setDate(date.getDate() + 1);

    return date;
  }

  datePickerFilter = (d: Date | null): boolean => {
    const today = new Date().getDate();
    const date = (d || new Date()).getDate();
    const day = (d || new Date()).getDay();
    return date > today && day !== 0;
  }

  onDateChange(newDate: Date | null) {
    this.laundryAppointmentForm.get('selectedDate')?.setValue(newDate);
  }

  makeAppointment(): void {
    console.log(this.laundryAppointmentForm.getRawValue());
    if(!this.laundryAppointmentForm.valid) return;
    if(this.laundryAppointmentForm.get('selectedIntervalStartHour')?.value === 0) {
      this.openSnackBar('Something went wrong. Please try again later.', 'Got it!');
      return;
    }
    this.appointmentService.createAppointment(this.laundryAppointmentForm.getRawValue()).subscribe({
      next:next=>{
        console.log(next)
        
      },
      error:(error)=>{console.log(error)}
    })
    console.log("Everything went right");
  }

  openSnackBar(message: string, action: string){
    this.snackBar.open(message, action);
  }
}
