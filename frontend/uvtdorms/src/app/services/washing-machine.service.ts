import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WashingMachine } from '../interfaces/washing-machine';
import { AuthService } from './auth.service';
import { AvailableWashingMachineDto } from '../interfaces/available-washing-machine-dto';


@Injectable({
  providedIn: 'root'
})
export class WashingMachineService {

  private washingMachineServiceUrl = "http://localhost:8080/api/washingmachines";
  private getWashingMachinesFromDormUrl = "/get-washing-machines-from-dorm";
  private getAvailableWashingMachineUrl = "/get-available-washing-machines";

  constructor(private http: HttpClient, private auth: AuthService) { }

  getWashingMachinesFromDorm(dormId: string): Observable<WashingMachine[]> {
    return this.http.get<WashingMachine[]>(this.washingMachineServiceUrl + this.getWashingMachinesFromDormUrl + "/" + dormId, { headers: this.auth.getHeader() });
  }
  getAvailableWashingMachine(): Observable<AvailableWashingMachineDto[]> {
    return this.http.get<AvailableWashingMachineDto[]>(this.washingMachineServiceUrl + this.getAvailableWashingMachineUrl, { headers: this.auth.getHeader() });
  }

}
