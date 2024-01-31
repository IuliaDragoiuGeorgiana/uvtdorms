import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'uvtdorms';

  constructor(private authService: AuthService){}

  ngOnInit(){
    if(this.authService.getAuthToken() != null)
    {
      this.authService.loginWithToken().subscribe({
        next: (tokenDto) => {
          console.log(tokenDto);
        }
      });
    } else {
      console.log("token not found"); 
    }
  }

}
