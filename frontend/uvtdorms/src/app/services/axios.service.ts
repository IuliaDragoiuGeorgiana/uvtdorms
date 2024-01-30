import { Injectable } from '@angular/core';
import axios, { Method } from 'axios';

@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  constructor() { 
   axios.defaults.baseURL="http://localhost:8080"
  //  axios.defaults.headers.post["Content-type"]="application/json"
  }

  getAuthToken(): string | null {
    return window.localStorage.getItem("uvtdorms_auth_token");
  }

  setAuthToken(token: string | null) {
    if(token !== null) {
      window.localStorage.setItem("uvtdorms_auth_token", token);
    } else {
      window.localStorage.removeItem("uvtdorms_auth_token");
    }
  }

  request(method: string, url:string, data :any): Promise <any>{
    let headers = {};

    if(this.getAuthToken() !== null) {
      headers = {"Authorization": "Bearer" + this.getAuthToken()};
    }
    return axios({
      method: method as Method,
      url: url,
      data: data,
      headers: headers
    });
  }
}
