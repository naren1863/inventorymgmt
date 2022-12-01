import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'inv-ui';
  activeUsers: string[] = ['Naren', 'Tathagat', 'Piyush'];
  loggedUser: string = 'defaultUser';
}
