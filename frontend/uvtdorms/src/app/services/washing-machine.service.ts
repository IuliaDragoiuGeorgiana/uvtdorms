import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WashingMachine } from '../interfaces/washing-machine';


@Injectable({
  providedIn: 'root'
})
export class WashingMachineService {

  private washingMachineServiceUrl = "http://localhost:8080/api/washingmachines";
  private getWashingMachinesFromDormUrl = "/get-washing-machines-from-dorm";


  constructor(private http:HttpClient){}
  getWashingMachinesFromDorm(dormId:string): Observable <WashingMachine[]> {
    return this.http.get<WashingMachine[]>(this.washingMachineServiceUrl+this.getWashingMachinesFromDormUrl+"/"+dormId);

  }
}
