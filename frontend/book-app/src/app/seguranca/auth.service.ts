import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';
import { UsuarioRegister} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  oauthTokenUrl =  environment.apiUrl + '/auth/login';
  oauthRefreshTokenUrl =  this.oauthTokenUrl + '/refresh';
  createUserUrl =  environment.apiUrl + '/auth/register';

  jwtPayload: any;
  private token: string | null = null;
  private refreshTokenInProgress = false;
  private refreshTokenPromise: Promise<void> | null = null;

  constructor(
      private http: HttpClient,
      private jwtHelper: JwtHelperService
    ) {
     this.carregarToken();
    }

    public getToken(): string | null {
      return this.token;
    }

    public login(email:string, password:string) {

    const headers = new HttpHeaders()
    .append('Content-Type', 'application/json');
    return this.http.post<HttpResponse<any>>(this.oauthTokenUrl, {email, password}, {headers, withCredentials: true, observe :'response'})
      .toPromise()
      .then((response: any)  => {

        this.armazenarToken(response.headers.get('Authorization')!);

      })
      .catch(erro =>{

        if (erro.status === 400) {
          if (erro.error.error === 'invalid_grant') {
            return Promise.reject('Usuário ou senha inválida!');
          }
        }

        return Promise.reject(erro);
      })
  }

  adicionar(usuario: UsuarioRegister) {

    const headers = new HttpHeaders()
    .append('Content-Type', 'application/json');

    return this.http.post<HttpResponse<any>>(this.createUserUrl, usuario, {headers, withCredentials: true, observe :'response'})
    .toPromise()
            .then((response: any)  => {

        console.log('Usuario adicionado com sucesso!');

      })
      .catch(erro =>{

        if (erro.status === 400) {
          if (erro.error.error === 'invalid_grant') {
            return Promise.reject('Usuário ou senha inválida!');
          }
        }
        return Promise.reject(erro);
      })
  }

  public obterNovoToken(): Promise<void> {
    if (this.refreshTokenInProgress && this.refreshTokenPromise) {
      return this.refreshTokenPromise;
    }

    this.refreshTokenInProgress = true;

    const body = 'grant_type=refresh_token';
    const headers = new HttpHeaders().append('Content-Type', 'application/x-www-form-urlencoded');

    this.refreshTokenPromise = this.http.post<HttpResponse<any>>(this.oauthRefreshTokenUrl, body, { headers, withCredentials: true, observe: 'response' })
      .toPromise()
      .then((response: any) => {
        const authorization = response?.headers.get('Authorization');
        if (authorization) {
          this.armazenarToken(authorization);
        }
      })
      .catch(() => {
        return;
      })
      .finally(() => {
        this.refreshTokenInProgress = false;
        this.refreshTokenPromise = null;
      });

    return this.refreshTokenPromise;
  }

  public limparAcessToken() {
    this.token = null;
    this.jwtPayload = null;
    localStorage.removeItem('tokenVoucher');
  }

  public isAccessTokenInvalido() {
    const token = this.token;
    return !token || this.jwtHelper.isTokenExpired(token);
  }

  public armazenarToken(token: string) {
    this.token = token;
    this.jwtPayload = this.jwtHelper.decodeToken(token);
    localStorage.setItem('tokenVoucher', token);
  }

  public carregarToken() {
    const token = localStorage.getItem('tokenVoucher');
    if (token) {
      this.armazenarToken(token);
    }
  }

  temPermissao(permissao: string) {

    return this.jwtPayload && this.jwtPayload.authorities.includes(permissao);
  }

  public temQualquerPermissao(roles: any) {
    for (const role of roles) {
      if (this.temPermissao(role)) {
        return true;
      }
    }

    return false;
  }

}
