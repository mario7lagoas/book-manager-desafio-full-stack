import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PaginaNaoEncontradaComponent } from './core/pagina-nao-encontrada.component';
import { NaoAutorizadoComponent } from './core/navbar/nao-autorizado.component';
import { HomeComponent } from './core/home/home.component';

const routes: Routes = [
  { path : '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'book', loadChildren: () => import('./book/book.module').then(m => m.BookModule) },
  { path: 'nao-autorizado', component: NaoAutorizadoComponent },
  { path : 'pagina-nao-encontrada', component: PaginaNaoEncontradaComponent },
  { path: '**', redirectTo: 'pagina-nao-encontrada' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
