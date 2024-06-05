import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { EvenimentDto } from '../interfaces/eveniment-dto';
import { CreateEvenimentDto } from '../interfaces/create-eveniment-dto';
import { UpdateEvenimentDto } from '../interfaces/update-eveniment-dto';
import { IdDto } from '../interfaces/id-dto';
@Injectable({
  providedIn: 'root',
})
export class EvenimentService {
  private evenimentServiceUrl = 'http://localhost:8080/api/eveniment';
  private getEvenimentsFromDormUrl = '/get-eveniment-from-dorm';
  private createEvenimentUrl = '/create-eveniment';
  private updateEvenimentUrl = '/update-eveniment';
  private deleteEvenimentUrl = '/delete-eveniment';
  private attendToEvenimentUrl = '/attend-to-eveniment';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getEvenimentsFromDrom(): Observable<EvenimentDto[]> {
    return this.http.get<EvenimentDto[]>(
      this.evenimentServiceUrl + this.getEvenimentsFromDormUrl,
      { headers: this.auth.getHeader() }
    );
  }

  createEveniment(createEvenimentDto: CreateEvenimentDto): Observable<void> {
    return this.http.post<void>(
      this.evenimentServiceUrl + this.createEvenimentUrl,
      createEvenimentDto,
      { headers: this.auth.getHeader() }
    );
  }

  updateEveniment(updateEvenimentDto: UpdateEvenimentDto): Observable<void> {
    return this.http.post<void>(
      this.evenimentServiceUrl + this.updateEvenimentUrl,
      updateEvenimentDto,
      { headers: this.auth.getHeader() }
    );
  }

  deleteEveniment(idDto: IdDto): Observable<void> {
    return this.http.post<void>(
      this.evenimentServiceUrl + this.deleteEvenimentUrl,
      idDto,
      { headers: this.auth.getHeader() }
    );
  }

  attendToEveniment(idDto: IdDto): Observable<void> {
    return this.http.post<void>(
      this.evenimentServiceUrl + this.attendToEvenimentUrl,
      idDto,
      { headers: this.auth.getHeader() }
    );
  }
}
