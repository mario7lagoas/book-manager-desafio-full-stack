import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from 'src/app/seguranca/auth.service';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService} from 'primeng/api';
import { ErrorHandlerService } from 'src/app/core/error-handler.service';
import { Book } from 'src/app/core/model';
import { BookService } from './../book.service';

@Component({
  selector: 'app-book-cadastro',
  templateUrl: './book-cadastro.component.html',
  styleUrls: ['./book-cadastro.component.css']
})
export class BookCadastroComponent implements OnInit {

  livro: Book = new Book();

  constructor(
    private auth: AuthService,
    private errorHandler: ErrorHandlerService,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute,
    private title: Title,
    private bookService: BookService

  ) { }

  ngOnInit(): void {
    this.title.setTitle('Novo loja');
    const codigoLivro = this.route.snapshot.params['guid'];

    if (codigoLivro && codigoLivro !== 'novo') {
      this.carregarLivro(codigoLivro)
    }
  }

  carregarLivro(guid: string) {
    this.bookService.buscarPorCodigo(guid)
      .subscribe({
        next: livro => {
          this.livro = livro;
          this.atualizarTituloEdicao();
        },
        error: erro => this.errorHandler.handle(erro)
      });
  }

   salvar(livroForm: NgForm){
    if (this.editando) {
      this.atualizarLivro(livroForm)
    } else {
      this.adicionarLivro(livroForm)
    }
  }
  adicionarLivro(form: NgForm) {

    this.bookService.adicionar(this.livro)
      .subscribe({
        next: livro => {
          this.messageService.add({ severity: 'success', detail: 'Livro adicionado com sucesso!' });
          this.router.navigate(['/book', livro.guid]);
        },
        error: erro => this.errorHandler.handle(erro)
      });
  }

  atualizarLivro(form: NgForm) {
    this.bookService.atualizar(this.livro)
      .subscribe({
        next: (livro: Book) => {
          this.livro = livro;
          this.messageService.add({ severity: 'success', detail: 'Livro alterado com sucesso!' });
          this.atualizarTituloEdicao();
        },
        error: erro => this.errorHandler.handle(erro)
      });
  }

  novo(form: NgForm) {
    form.reset();

    setTimeout(() => {
      this.livro = new Book();
    }, 1);

    this.router.navigate(['book/novo']);
  }

  get editando() {
    return Boolean(this.livro.guid)
  }
  atualizarTituloEdicao() {
    this.title.setTitle(`Edição de livro: ${this.livro.title}`)
  }

  temPermissao(permissao: string) {
    return this.auth.temPermissao(permissao);
  }

}
