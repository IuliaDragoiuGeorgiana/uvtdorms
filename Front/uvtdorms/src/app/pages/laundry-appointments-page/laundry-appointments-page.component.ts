import { Component } from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

interface washingMachine {
  id: number
  name: string
  available: boolean
}

interface timeInterval {
  startHour: number,
  printableValue: string
}

@Component({
  selector: 'app-laundry-appointments-page',
  templateUrl: './laundry-appointments-page.component.html',
  styleUrl: './laundry-appointments-page.component.css'
})
export class LaundryAppointmentsPageComponent {
  washingMachines: washingMachine[] = [
    {id: 1, name: 'Machine 1', available: true},
    {id: 2, name: 'Machine 2', available: false},
    {id: 3, name: 'Machine 3', available: true}
  ];

  timeIntervals: timeInterval[] = [
    {startHour: 8, printableValue: '8:00-10:00'},
    {startHour: 10, printableValue: '10:00-12:00'},
    {startHour: 12, printableValue: '12:00-14:00'},
    {startHour: 14, printableValue: '14:00-16:00'},
    {startHour: 16, printableValue: '16:00-18:00'},
    {startHour: 18, printableValue: '18:00-20:00'}
  ]


  selectedMachineId: number | null;
  selectedDate: Date | null;
  selectedIntervalStartHour: number | null;

  constructor(private snackBar: MatSnackBar){
    this.selectedMachineId = null;
    this.selectedDate = this.getNextSaturday();
    this.selectedIntervalStartHour = null;
  }

  getNextSaturday(): Date{
    const date = new Date();
    date.setDate(date.getDate() + 1);
    // date.setDate(date.getDate() + (6 - date.getDay()));

    return date;
  }

  datePickerFilter = (d: Date | null): boolean => {
    const today = new Date().getDate();
    const date = (d || new Date()).getDate();
    const day = (d || new Date()).getDay();
    return date > today && day !== 0;
  }

  makeAppointment(): void{
    if(this.selectedMachineId === null){
      this.openSnackBar("Please select a washing machine", "Got it!");
      return;
    }
    if(this.selectedIntervalStartHour === null){
      this.openSnackBar("Please select a time interval", "Got it!");
      return;
    }
    console.log("Selected machine:");
    console.log(this.selectedMachineId);
    console.log("Selected date:");
    console.log(this.selectedDate);
    console.log("Selected interval:");
    console.log(this.selectedIntervalStartHour);
  }

  openSnackBar(message: string, action: string){
    this.snackBar.open(message, action);
  }
}
