import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {


  constructor (
    private router: Router,
    private config: PrimeNGConfig){
    this.config.setTranslation({
      accept: 'Aceitar',
      reject: 'Cancelar',
      "dayNames": ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
      "dayNamesShort": ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"],
      "dayNamesMin": ["Do","Se","Te","Qa","Qi","Sx","Sa"],
      "monthNames": ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"],
      "monthNamesShort": ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun","Jul", "Ago", "Set", "Out", "Nov", "Dez"],
      "today": "Hoje",
      "weekHeader": "Sem"
  });
  }

  exibindoNavbar() {
    return this.router.url !== '/login';
  }

}
