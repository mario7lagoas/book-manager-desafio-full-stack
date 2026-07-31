import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { ErrorHandlerService } from 'src/app/core/error-handler.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  jwtPayload: any;

  constructor(
    private auth: AuthService,
    private errorHandler: ErrorHandlerService,
    private router: Router) {


    }

  ngOnInit(): void {

  }

  login(email: string, password: string) {


    this.auth.login(email,password)
    .then(() => {
      this.router.navigate(['/home']);
    })
    .catch(err => {

      if ( "403" === JSON.stringify(err.status)){
        const erro: string = "Usuario ou senha inválida!";
        this.errorHandler.handle( erro);
      }
      if ( "0" === JSON.stringify(err.status)){

        const erro: string = "Serviço indisponivel no momento, tente mais tarde!";
        this.errorHandler.handle( erro);
      }


    })
  }




}
