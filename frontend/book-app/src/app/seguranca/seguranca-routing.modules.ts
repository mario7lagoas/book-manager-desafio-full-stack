import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginCadastroComponent } from './login-cadastro/login-cadastro.component';


const routes: Routes = [
  { path : 'login', component: LoginFormComponent },
  { path: 'login/create', component: LoginCadastroComponent  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SegurancaRoutingModule { }
