import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { PrimeNgModule } from '../primeng.module';
import { FormsModule } from '@angular/forms';
import { SegurancaRoutingModule } from './seguranca-routing.modules';
import { JwtModule, JwtHelperService } from '@auth0/angular-jwt';
import { AuthGuard } from './auth.guard';
import { environment } from 'src/environments/environment';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BookHttpInterceptor } from './book-http-interceptor';
import { LoginCadastroComponent } from './login-cadastro/login-cadastro.component';

export function tokenGetter(): string {
  return localStorage.getItem('tokenVoucher')!;
}

@NgModule({
  declarations: [
    LoginFormComponent,
    LoginCadastroComponent
  ],
  imports: [
    CommonModule,
    PrimeNgModule,
    FormsModule,
    SegurancaRoutingModule,
    JwtModule.forRoot({
      config: {
        tokenGetter,
        allowedDomains: environment.tokenAllowedDomains,
        disallowedRoutes: environment.tokenDisallowedRoutes
      }
    }),

  ],
  providers: [
    JwtHelperService,
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: BookHttpInterceptor,
      multi: true
    }
  ],
  exports: [
    LoginFormComponent,
    LoginCadastroComponent

  ]
})
export class SegurancaModule { }
