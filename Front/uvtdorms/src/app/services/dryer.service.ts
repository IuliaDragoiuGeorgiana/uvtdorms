import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Dryer } from '../interfaces/dryer';

@Injectable({
  providedIn: 'root'
})
export class DryerService {
  
  private dryerServiceUrl="http://localhost:8080/api/dryers";
  private getDryerFromDormUrl="/get-dryer-from-dorm";

  constructor(private http:HttpClient) { }
  getDryerFromDorm(dormId:string): Observable <Dryer[]> {
    return this.http.get<Dryer[]>(this.dryerServiceUrl+this.getDryerFromDormUrl+"/"+dormId);

  }
}
