import { NgModule } from '@angular/core';
import { CommonModule, DatePipe, registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import localePt from '@angular/common/locales/pt';

import { NavbarComponent } from './navbar/navbar.component';
import { ErrorHandlerService } from './error-handler.service';
import { PaginaNaoEncontradaComponent } from './pagina-nao-encontrada.component';
import { AuthService } from '../seguranca/auth.service';
import { MessageService, ConfirmationService } from 'primeng/api';
import { NaoAutorizadoComponent } from './navbar/nao-autorizado.component';
import { HomeComponent } from './home/home.component';
import { HomeRoutingModule } from './home-routing.module';
import { BookService } from '../book/book.service';

registerLocaleData(localePt, 'pt-BR');

@NgModule({
  declarations: [
    NavbarComponent,
    PaginaNaoEncontradaComponent,
    NaoAutorizadoComponent,
    HomeComponent

  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    HomeRoutingModule

  ],
  exports: [
    NavbarComponent,
    HomeComponent
  ],
  providers: [
    ErrorHandlerService,
    AuthService,
    MessageService,
    ConfirmationService,
    DatePipe,
    BookService
  ]

})
export class CoreModule { }
