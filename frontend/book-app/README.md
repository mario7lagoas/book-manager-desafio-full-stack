# Book App

## 1) Visão geral

Este repositório contém uma aplicação web frontend em Angular para gerenciamento de livros. A aplicação possui navegação por rotas, autenticação, componentes de cadastro e pesquisa, além de uma estrutura modular organizada em áreas como segurança, núcleo da aplicação e módulos de livros.

O projeto está configurado para ser executado localmente com o Angular CLI e usa scripts definidos no arquivo package.json para subir o servidor, gerar build e executar testes.

## 2) Tecnologias usadas

- Angular 14
- TypeScript 4.7
- RxJS 7.5
- PrimeNG, PrimeFlex e PrimeIcons
- Angular Router e Angular Animations
- Karma e Jasmine para testes
- Node.js + npm para execução do ambiente

## 3) Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- Node.js 14.15+ (recomendado: 16.x)
- npm 6+ (recomendado: 8.x)
- Git
- Navegador moderno (Chrome, Edge ou similar)

## 4) Como instalar dependências

Na pasta raiz do projeto, execute:

```bash
npm install
```

Esse comando instala todas as dependências listadas no arquivo package.json.

## 5) Passo a passo exato de execução no terminal

1. Abra o terminal na pasta do projeto:

```bash
cd book-app
```

2. Instale as dependências:

```bash
npm install
```

3. Inicie a aplicação em modo de desenvolvimento:

```bash
npm start
```

O comando acima executa o script definido em package.json:

```bash
ng serve
```

A aplicação ficará disponível em:

```text
http://localhost:4200/
```

4. Para parar a execução, pressione:

```text
Ctrl + C
```

### Outras operações úteis

Gerar build de produção:

```bash
npm run build
```

Se quiser, também é possível usar o Angular CLI diretamente:

```bash
npx ng serve
```
