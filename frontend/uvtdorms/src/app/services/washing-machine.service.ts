import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WashingMachine } from '../interfaces/washing-machine';
import { AuthService } from './auth.service';
import { AvailableWashingMachineDto } from '../interfaces/available-washing-machine-dto';
import { NewMachineDto } from '../interfaces/new-machine-dto';

@Injectable({
  providedIn: 'root',
})
export class WashingMachineService {
  private washingMachineServiceUrl =
    'http://localhost:8080/api/washingmachines';
  private getWashingMachinesFromDormUrl = '/get-washing-machines-from-dorm';
  private getAvailableWashingMachineUrl = '/get-available-washing-machines';
  private createWashingMachineUrl = '/create-washing-machine';
  private updateWashinMachineUrl = '/update-washing-machine';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getWashingMachinesFromDorm(dormId: string): Observable<WashingMachine[]> {
    return this.http.get<WashingMachine[]>(
      this.washingMachineServiceUrl +
        this.getWashingMachinesFromDormUrl +
        '/' +
        dormId,
      { headers: this.auth.getHeader() }
    );
  }
  getAvailableWashingMachine(): Observable<AvailableWashingMachineDto[]> {
    return this.http.get<AvailableWashingMachineDto[]>(
      this.washingMachineServiceUrl + this.getAvailableWashingMachineUrl,
      { headers: this.auth.getHeader() }
    );
  }

  createWashingMachine(washingMachine: NewMachineDto) {
    return this.http.post(
      this.washingMachineServiceUrl + this.createWashingMachineUrl,
      washingMachine,
      { headers: this.auth.getHeader() }
    );
  }

  updateWashingMachine(
    washingMachine: WashingMachine
  ): Observable<WashingMachine> {
    return this.http.post<WashingMachine>(
      this.washingMachineServiceUrl + this.updateWashinMachineUrl,
      washingMachine,
      { headers: this.auth.getHeader() }
    );
  }
}
