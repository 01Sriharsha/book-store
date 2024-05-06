import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthenticationRequest } from '../../../types/authentication-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  authRequest: AuthenticationRequest = {
    email: '',
    fullname: '',
    password: '',
  };
}
