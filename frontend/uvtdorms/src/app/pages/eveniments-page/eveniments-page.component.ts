import { Component } from '@angular/core';
import { EvenimentDto } from '../../interfaces/eveniment-dto';
import { EvenimentService } from '../../services/eveniment.service';
import { IdDto } from '../../interfaces/id-dto';

@Component({
  selector: 'app-eveniments-page',
  templateUrl: './eveniments-page.component.html',
  styleUrl: './eveniments-page.component.css',
})
export class EvenimentsPageComponent {
  public eveniments: EvenimentDto[] = [];

  constructor(private evenimentService: EvenimentService) {
    this.evenimentService.getEvenimentsFromDrom().subscribe({
      next: (eveniments) => {
        this.eveniments = eveniments;
        console.log(eveniments);
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

getEvenimentListisEmpty(): boolean {
    return this.eveniments.length === 0;
  }

  public formatDate(date: any[]): string {
    return (
      date[0] +
      '/' +
      (date[1] < 10 ? '0' + date[1] : date[1]) +
      '/' +
      (date[2] < 10 ? '0' + date[2] : date[2]) +
      ' ' +
      (date[3] < 10 ? '0' + date[3] : date[3]) +
      ':' +
      (date[4] < 10 ? '0' + date[4] : date[4])
    );
  }

  updateAttend(eveniment: EvenimentDto): void {
    let idDto: IdDto = {
      id: eveniment.id,
    };
    this.evenimentService.attendToEveniment(idDto).subscribe({
      next: () => {
        if (eveniment.isUserAttending) {
          eveniment.numberOfAttendees--;
          eveniment.isUserAttending = false;
        } else {
          eveniment.numberOfAttendees++;
          eveniment.isUserAttending = true;
        }

        this.eveniments = this.eveniments.map((e) => {
          if (e.id === eveniment.id) {
            return eveniment;
          }
          return e;
        });
      },
      error: (error) => {
        console.error(error);
      },
    });
  }
}
