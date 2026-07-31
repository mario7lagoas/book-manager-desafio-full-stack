
import { AuthService } from 'src/app/seguranca/auth.service';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from, mergeMap } from 'rxjs';

export class NotAuthenticatedError { }

@Injectable()
export class BookHttpInterceptor implements HttpInterceptor{

  constructor(private auth: AuthService){ }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const isLoginRequest = req.url.includes('/login');
    const isRegisterRequest = req.url.includes('/register');
    const token = this.auth.getToken();

    if (isLoginRequest || isRegisterRequest) {
      return next.handle(req);
    }

    if (!token || this.auth.isAccessTokenInvalido()) {
      return from(this.auth.obterNovoToken()).pipe(
        mergeMap(() => {
          const refreshedToken = this.auth.getToken();

          if (!refreshedToken || this.auth.isAccessTokenInvalido()) {
            throw new NotAuthenticatedError();
          }

          const authReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${refreshedToken}`
            }
          });

          return next.handle(authReq);
        })
      );
    }

    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next.handle(authReq);
  }

}

