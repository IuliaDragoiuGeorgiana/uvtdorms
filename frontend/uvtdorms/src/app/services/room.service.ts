import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { RoomsNumbersDto } from '../interfaces/rooms-numbers';
import { Observable } from 'rxjs';
import { DetailedRoomDto } from '../interfaces/detailed-room-dto';
import { RoomNumberDto } from '../interfaces/room-number-dto';
import { LightUserDto } from '../interfaces/light-user-dto';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  private roomServiceUrl = 'http://localhost:8080/api/rooms';
  private getRoomsNumbersFromDromUrl = '/get-rooms-numbers-from-dorm/';
  private getRoomDetailsFromDromUrl = '/get-room-details-from-dorm';
  private addRoomToDormUrl = '/add-room-to-dorm';
  private deleteRoomFromDormUrl = '/delete-room-from-dorm';
  private getStudentsFromRoomUrl = '/get-students-from-room';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getRoomsNumbersFromDrom(dormName: string): Observable<RoomsNumbersDto> {
    return this.http.get<RoomsNumbersDto>(
      this.roomServiceUrl + this.getRoomsNumbersFromDromUrl + dormName
    );
  }

  getRoomDetailsFromDorm(): Observable<DetailedRoomDto[]> {
    return this.http.get<DetailedRoomDto[]>(
      this.roomServiceUrl + this.getRoomDetailsFromDromUrl,
      { headers: this.auth.getHeader() }
    );
  }

  addRoomToDorm(roomNumberDto: RoomNumberDto): Observable<void> {
    return this.http.post<void>(
      this.roomServiceUrl + this.addRoomToDormUrl,
      roomNumberDto,
      { headers: this.auth.getHeader() }
    );
  }

  deleteRoomFromDorm(roomNumberDto: RoomNumberDto): Observable<void> {
    return this.http.post<void>(
      this.roomServiceUrl + this.deleteRoomFromDormUrl,
      roomNumberDto,
      { headers: this.auth.getHeader() }
    );
  }

  getStudentsFromRoom(
    roomNumberDto: RoomNumberDto
  ): Observable<LightUserDto[]> {
    return this.http.post<LightUserDto[]>(
      this.roomServiceUrl + this.getStudentsFromRoomUrl,
      roomNumberDto,
      { headers: this.auth.getHeader() }
    );
  }
}
