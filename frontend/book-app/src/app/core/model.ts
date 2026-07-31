export class Pagina{
  itensPorPagina = 10;
  pagina = 0;
}

export class UsuarioRegister {
  guid?: string;
  userName?: string;
  email?: string;
  dataCadastro?: string;
  password?: string;
}

export class Book {
  guid?: string;
  title?: string;
  description?: string;
  author?: string;
  year?: number;
}

export class BookPaginada extends Pagina {
  title?: string;
}

export class BookFiltro extends Pagina {
  title?: string;
  email?: string;

}


