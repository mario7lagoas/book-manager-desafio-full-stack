import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pagina-nao-encontrada',
  template: `
   <div class="container">
      <H1 class="text-center">Página não encontrada.</H1>
      <a routerLink="/home">Ir para home</a>
   </div>
  `,
  styles: []
})
export class PaginaNaoEncontradaComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
