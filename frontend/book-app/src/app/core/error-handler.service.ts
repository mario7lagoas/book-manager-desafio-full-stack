import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from '../seguranca/auth.service';
import { NotAuthenticatedError } from '../seguranca/book-http-interceptor';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor(
    private messageService: MessageService,
    private router: Router,
    private auth: AuthService) { }

  handle(errorResponse: any){
    let msg: string;

    console.log("Erro no erro handle -> " + JSON.stringify(errorResponse));
    console.log("messagem : " + errorResponse?.mensagem);

    if (typeof errorResponse === 'string'){
      msg = errorResponse;
    }else if ( errorResponse.status instanceof NotAuthenticatedError){

        msg = 'Senha Expirada.';
        this.router.navigate(['/login']);
    }else if (errorResponse instanceof Response && errorResponse.status >= 400 && errorResponse.status <= 499) {
      let errors;
      msg = 'Ocorreu um erro ao processar a sua solicitação';

      try {
        errors = errorResponse.json();

      //msg = errors[0].mensagemUsuario;
      } catch (e) { }

      console.error('Ocorreu um erro', errorResponse);

    } else {
      msg = 'Erro ao processar serviço remoto. Tente novamente.';
      console.error('Ocorreu um erro', errorResponse);
  }

  if ( errorResponse.status === 400  ||
    errorResponse.status === 422 ||
    errorResponse.status === 404 && errorResponse?.error?.erros.length > 0){

    for (var error of errorResponse?.error?.erros){
     // console.log("messagem : " + error?.mensagem)
      this.messageService.add({severity:'error', summary:'Atenção', detail: error?.mensagem});
    }

  }else{
    //this.messageService.add({ severity: 'error', detail: msg });
    msg = 'Ocorreu um erro ao processar a sua solicitação';
    this.messageService.add({severity:'error', summary:'Atenção', detail: msg});
  }

  }

}
