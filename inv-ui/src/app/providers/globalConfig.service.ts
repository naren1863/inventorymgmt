import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
  export class GlobalConfig {
    public isJson: boolean = false;
  }
