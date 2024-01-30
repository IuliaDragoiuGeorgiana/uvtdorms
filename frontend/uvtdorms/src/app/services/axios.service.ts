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

  request(method: string, url:string, data :any): Promise <any>{
    return axios({
      method: method as Method,
      url: url,
      data: data,
      headers: {'Content-type': 'application/json'}
    });
  }
}
