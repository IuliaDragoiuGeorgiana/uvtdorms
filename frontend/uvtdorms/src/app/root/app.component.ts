import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'uvtdorms';

  ngOnInit(){
    console.log("ceau bau");
  }

  constructor(){
    console.log("ceau bau2");
  }
}
