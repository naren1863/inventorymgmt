import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
  export class LoginService {
    public activeUsers: string[] = ['Naren', 'Tathagat', 'Piyush'];
    public loggedUser: string = 'defaultUser';
  }  