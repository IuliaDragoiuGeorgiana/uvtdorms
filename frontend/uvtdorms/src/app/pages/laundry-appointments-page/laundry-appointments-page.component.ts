import { Component, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WashingMachine } from '../../interfaces/washing-machine';
import { timeInterval } from '../../interfaces/time-interval';
import { Dryer } from '../../interfaces/dryer';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { AppointmentService } from '../../services/appointment.service';
import { WashingMachineService } from '../../services/washing-machine.service';
import { DryerService } from '../../services/dryer.service';
import { StudentDetailsService } from '../../services/student-details.service';
import { format } from 'date-fns';
import { start } from 'repl';
import { machine } from 'os';
import { MatInput } from '@angular/material/input';

interface MachinePair
{
  washingMachine: WashingMachine;
  dryer: Dryer;
}

@Component({
  selector: 'app-laundry-appointments-page',
  templateUrl: './laundry-appointments-page.component.html',
  styleUrl: './laundry-appointments-page.component.css'
})
export class LaundryAppointmentsPageComponent {
  washingMachines: WashingMachine[] = [];
  dryers: Dryer[] = [];
  machines: MachinePair[] = [];
  selectedMachines: MachinePair | null = null;
  @ViewChild('dryer') dryer!: MatInput;

  tabs = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  currentDay = new Date().getDay();
  selectedDay = new FormControl(this.currentDay);
  intervals = [8, 10, 12, 14, 16, 18, 20];


  timeIntervals: timeInterval[] = [
    {startHour: 8, printableValue: '8:00'},
    // {startHour: 10, printableValue: '10:00'},
    // {startHour: 12, printableValue: '12:00'},
    {startHour: 14, printableValue: '14:00'},
    {startHour: 16, printableValue: '16:00'},
    {startHour: 18, printableValue: '18:00'},
    {startHour: 20, printableValue: '20:00'}
  ];

  dormId:string ="";
  laundryAppointmentForm = this.formBuilder.group({
    selectedMachineId: ['', Validators.required],
    selectedDryerId: ['', Validators.required],
    selectedDate: [this.getTomorrowDate() || '', Validators.required],
    selectedIntervalStartHour: [0, Validators.required]
  });

  constructor(private snackBar: MatSnackBar,
              private formBuilder: FormBuilder,
              private appointmentService: AppointmentService,
              private washingMachineService: WashingMachineService,
              private dryerService: DryerService,
              private studentService:StudentDetailsService)
  {
    console.log(this.tabs.slice(this.currentDay, 7));
  }

  ngOnInit()
  {
    if (this.timeIntervals && this.timeIntervals.length > 0) {
      this.laundryAppointmentForm.get('selectedIntervalStartHour')?.setValue(this.timeIntervals[0].startHour);
    }
    this.studentService.getStudentDormId("iulia.dragoiu02@e-uvt.ro").subscribe({
      next:dormId=>{
        console.log(dormId);
        this.dormId=dormId.id;
        this.getDryers();
        this.getWashingMachines();
      }, 
      error:(error) =>{
        console.log(error);
      }
    });

  }

  getWashingMachines(): void{
    this.washingMachineService.getWashingMachinesFromDorm(this.dormId).subscribe({
      next:washingMachines=>{
       this.washingMachines=washingMachines;
       this.joinMachines();
       console.log(washingMachines);
      },
      error:(error)=>{
        console.log(error);
      }
    })
  }

  getDryers(): void{
    this.dryerService.getDryerFromDorm(this.dormId).subscribe({
      next:dryers=>{
       this.dryers=dryers;
       this.joinMachines();
       console.log(this.dryers);
      },
      error:(error)=>{
        console.log(error);
      }
    })
  }

  joinMachines(): void
  {
    for(let i = 0; i < this.washingMachines.length && i < this.dryers.length; i++)
    {
      this.machines.push({washingMachine: this.washingMachines[i], dryer: this.dryers[i]});
    }
    console.log(this.machines);
  }

  getTomorrowDate(): Date{
    const date = new Date();
    date.setDate(date.getDate() + 1);

    return date;
  }

  datePickerFilter = (d: Date | null): boolean => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const startOfWeek = new Date(today);
    startOfWeek.setDate(today.getDate() - today.getDay() + (today.getDay() === 0 ? -6 : 1));

    const endOfWeek = new Date(startOfWeek);
    endOfWeek.setDate(startOfWeek.getDate() + 6);

    const date = (d || new Date());
    return date >= startOfWeek && date <= endOfWeek && date.getDay() !== 0 && date >= today;
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

    let formDataCopy = { ...this.laundryAppointmentForm.getRawValue() };

    const selectedDate = new Date(formDataCopy.selectedDate!);
    // const formattedDate = selectedDate.toISOString().split('T')[0];
    const formattedDate = format(selectedDate, 'yyyy-MM-dd');

    let x = {
      userEmail: "iulia.dragoiu02@e-uvt.ro",
      selectedMachineId: formDataCopy.selectedMachineId,
      selectedDryerId: formDataCopy.selectedDryerId,
      selectedDate: formattedDate,
      selectedInterval: formDataCopy.selectedIntervalStartHour,
    }

    this.appointmentService.createAppointment(x).subscribe({
      next:next=>{
        console.log(next);
        this.updateIntervals();
      },
      error:(error)=>{console.log(error)}
    })
    console.log("Everything went right");
  }

  updateIntervals(): void {
    //TODO: create custom dto
    for(let machine of this.machines)
    {
      if(machine.washingMachine.id === this.laundryAppointmentForm.get('selectedMachineId')?.value)
      {
        this.selectedMachines = machine;
        break;
      }
    }

    let tempDto = {
      dormId: this.dormId,
      date: format(this.laundryAppointmentForm.get('selectedDate')?.value!, 'yyyy-MM-dd'),
      washingMachineId: this.selectedMachines?.washingMachine.id,
      dryerId: this.selectedMachines?.dryer.id
    }

    console.log(tempDto);

    const intervalsDay = this.laundryAppointmentForm.get('selectedDate')?.value?.getDate();
    const today = new Date().getDate();

    console.log(tempDto);
    this.appointmentService.getFreeIntervalsForCreatingAppointment(tempDto).subscribe({
      next: intervals => {
        console.log(intervals);
        if (Array.isArray(intervals)) {
          this.timeIntervals = [];
          intervals.forEach(startHour => {
              let currentHour = new Date().getHours();
              // console.log(currentHour);
              if(startHour > currentHour || intervalsDay != today)
              this.timeIntervals.push({
                startHour: startHour,
                printableValue: `${startHour}:00`
              });
          });
        }
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  openSnackBar(message: string, action: string){
    this.snackBar.open(message, action);
  }

  getSelectedDayName()
  {
    return this.tabs[this.selectedDay.value!];
  }

  isIntervalAvailable(interval: number)
  {
    for(let i of this.timeIntervals)
    {
      if (i.startHour === interval) return true;
    }
    return false;
  }
}