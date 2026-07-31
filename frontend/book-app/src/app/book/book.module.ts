import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookPesquisaComponent } from './book-pesquisa/book-pesquisa.component';
import { BookCadastroComponent } from './book-cadastro/book-cadastro.component';
import { FormsModule } from '@angular/forms';
import { PrimeNgModule } from '../primeng.module';
import { BookRoutingModule } from './book-routing.modules';



@NgModule({
  declarations: [
    BookPesquisaComponent,
    BookCadastroComponent
  ],
  imports: [
    CommonModule,
    PrimeNgModule,
    FormsModule,
    BookRoutingModule
  ]
})
export class BookModule { }
