import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { RoomsNumbersDto } from '../interfaces/rooms-numbers';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  private roomServiceUrl = 'http://localhost:8080/api/rooms';
  private getRoomsNumbersFromDromUrl = '/get-rooms-numbers-from-dorm/';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getRoomsNumbersFromDrom(dormName: string): Observable<RoomsNumbersDto> {
    return this.http.get<RoomsNumbersDto>(
      this.roomServiceUrl + this.getRoomsNumbersFromDromUrl + dormName
    );
  }
}
