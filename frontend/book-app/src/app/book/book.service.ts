import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Book, BookFiltro } from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  bookUrl: string;

  constructor(private http: HttpClient) {
    this.bookUrl = `${environment.apiUrl}/books`;
  }

  bookFiltro(filtro: BookFiltro): Observable<{ books: any[]; total: number }> {
    let params = new HttpParams()
      .set('page', filtro.pagina)
      .set('size', filtro.itensPorPagina);

    if (filtro.title) {
      params = params.set('title', filtro.title);
    }

    return this.http.get<any>(`${this.bookUrl}`, { params })
      .pipe(
        map(response => ({
          books: response?.books,
          total: response.totalElements
        })),
        catchError((erro: any) => throwError(() => (erro?.messagem ? erro.messagem : 'Erro em buscar livros')))
      );
  }

  excluir(guid: string): Observable<void> {
    return this.http.delete<void>(`${this.bookUrl}/${guid}`)
      .pipe(
        catchError((erro: any) => throwError(() => erro))
      );
  }

  atualizar(book: Book): Observable<Book> {
    console.log('Envio -> ' + JSON.stringify(book));
    return this.http.put<Book>(`${this.bookUrl}/${book.guid}`, book)
      .pipe(
        map(response => {

          return response;
        }),
        catchError((erro: any) => throwError(() => erro))
      );
  }

  buscarPorCodigo(guid: string): Observable<Book> {
    return this.http.get<Book>(`${this.bookUrl}/${guid}`)
      .pipe(
        map(response => {
          return response;
        }),
        catchError((erro: any) => throwError(() => erro))
      );
  }

    adicionar(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.bookUrl}/create`, book)
      .pipe(
        catchError((erro: any) => throwError(() => erro))
      );
  }

}
