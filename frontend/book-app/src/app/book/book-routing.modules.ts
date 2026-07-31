import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { BookPesquisaComponent } from './book-pesquisa/book-pesquisa.component';
import { BookCadastroComponent } from './book-cadastro/book-cadastro.component';
import { AuthGuard } from '../seguranca/auth.guard';


const routes: Routes = [
  {path : '', component: BookPesquisaComponent,
  canActivate: [AuthGuard],
  data: { roles: ['ROLE_BUSCAR_BOOK', 'ROLE_APAGAR_BOOK', 'ROLE_ALTERAR_BOOK'] }
  },
  {path : 'novo', component: BookCadastroComponent,
  canActivate: [AuthGuard],
  data: { roles: ['ROLE_CADASTRAR_BOOK','ROLE_BUSCAR_BOOK'] }
  },
  {path : ':guid', component: BookCadastroComponent,
  canActivate: [AuthGuard],
  data: { roles: ['ROLE_BUSCAR_BOOK', 'ROLE_ALTERAR_BOOK'] }
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class BookRoutingModule { }
