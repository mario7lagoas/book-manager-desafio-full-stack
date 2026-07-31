import { Component, OnInit, ViewChild } from '@angular/core';
import { BookFiltro, BookPaginada } from 'src/app/core/model';
import { Table } from 'primeng/table';
import { ConfirmationService, LazyLoadEvent, MessageService, PrimeNGConfig} from 'primeng/api';
import { Title } from '@angular/platform-browser';
import { ErrorHandlerService } from 'src/app/core/error-handler.service';
import { BookService } from '../book.service';
import { AuthService } from 'src/app/seguranca/auth.service';

@Component({
  selector: 'app-book-pesquisa',
  templateUrl: './book-pesquisa.component.html',
  styleUrls: ['./book-pesquisa.component.css']
})
export class BookPesquisaComponent implements OnInit {

   @ViewChild('tabela') grid!: Table;

  BookPaginada = new BookPaginada();
  filtro = new BookFiltro();
  totalRegistros: number = 0;
  books: any [] = [];
  loading: boolean = true;

  constructor(
    private auth: AuthService,
    private errorHandler: ErrorHandlerService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private title: Title,
    private bookService : BookService
  ) { }

  ngOnInit(): void {
    this.title.setTitle('Pesquisa de Livros')
  }

    bookFiltro(pagina: number = 0){
    this.loading = true;

    this.filtro.pagina = pagina;
    this.filtro.email = this.auth.jwtPayload?.sub;

    setTimeout(() => {
      this.bookService.bookFiltro(this.filtro)
        .subscribe({
          next: (retorno: any) => {
            this.books = retorno.books;
            this.totalRegistros = retorno.total;
            this.loading = false;
          },
          error: erro => {
            this.loading = false;
            this.errorHandler.handle(erro);
          }
        });
    }, 250);

  }


  aoMudarPagina(event: LazyLoadEvent) {
    const pagina = event!.first! / event!.rows!;
    //this.pesquisarPaginado(pagina);
    this.bookFiltro(pagina);
  }

  confirmarExclusao(book: any): void {
    const temPermApagar = this.temPermissao('ROLE_APAGAR_BOOK');

    if (temPermApagar) {
      this.confirmationService.confirm({
        message: 'Tem certeza que deseja excluir?',
        header: 'Confirmação',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
          this.excluir(book);
        }
      });
    }
  }

  excluir(book: any) {

    this.bookService.excluir(book.guid)
      .subscribe({
        next: () => {
          if (this.grid.first === 0) {
            this.bookFiltro();
          } else {
            this.grid.reset();
          }

          this.messageService.add({ severity: 'success', detail: 'Livro excluído com sucesso!' });
        },
        error: erro => this.errorHandler.handle(erro)
      });
  }

  temPermissao(permissao: string) {
    return this.auth.temPermissao(permissao);
  }


}
