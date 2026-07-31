import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MessageService} from 'primeng/api';
import { Router } from '@angular/router';
import { UsuarioRegister } from './../../core/model';
import { ErrorHandlerService } from 'src/app/core/error-handler.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login-cadastro',
  templateUrl: './login-cadastro.component.html',
  styleUrls: ['./login-cadastro.component.css']
})
export class LoginCadastroComponent implements OnInit {

  usuario: UsuarioRegister = new UsuarioRegister();

  constructor(
    private router: Router,
    private errorHandler: ErrorHandlerService,
    private messageService: MessageService,
    private auth: AuthService
  ) { }

  ngOnInit(): void {

  }

  salvar(usuariofrom: NgForm){

      this.adicionarUsuario(usuariofrom)
  }

  adicionarUsuario(form: NgForm) {

    this.auth.adicionar(this.usuario)
    .then(() => {
      this.messageService.add({ severity: 'success', detail: 'Usuario adicionado com sucesso!' });
      this.router.navigate(['/login']);
    })
    .catch(erro => this.errorHandler.handle(erro));
  }
}
