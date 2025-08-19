# LiterAlura — Catálogo de Livros 

## 🧠 Funcionalidades (menu)
1. **Buscar livro pelo título (API Gutendex)** e salvar no banco.  
2. **Listar livros registrados**.  
3. **Listar autores registrados** (com anos de nascimento/falecimento e seus livros).  
4. **Listar autores vivos em um ano** (ex.: 1800).  
5. **Listar livros por idioma** (`pt`, `en`, `es`, `fr`).  
6. **Top 10 livros por downloads** (com base no que está no banco).  
0. **Sair**.

> Somente a opção **1** acessa a API; as demais usam apenas o banco.salvo em h2.

## 🌐 API utilizada
**Gutendex** — https://gutendex.com/books/  
Consulta por título é feita com `?search=<termo>`.

## 🧩 Estrutura de pacotes
```
com.danicodes.literalura
 ├─ controller/ConsoleController.java   # menu e interação via console
 ├─ model/Author.java, Book.java        # entidades JPA
 ├─ repository/*.java                   # repositórios Spring Data JPA
 ├─ service/GutendexClient.java         # cliente HTTP da Gutendex
 ├─ service/CatalogService.java         # regras de negócio
 └─ LiterAluraApplication.java          # main (CommandLineRunner)
```

## 🗃️ Banco de dados
- **Padrão (H2 em memória)**: nenhuma instalação necessária.
- **PostgreSQL**: disponível via perfil `postgres`.

## 💡 Extras sugeridos (templates prontos)
- Top 10 por downloads (**implementado**).
- Buscar autor pelo nome (pode adicionar método `findByNameContainingIgnoreCase` no AuthorRepository).
- Outras consultas: por intervalo de downloads, por palavra-chave no título, etc.

## 🧪 Teste rápido
- Rode o app e escolha `1` (buscar por título). Exemplos:
  - `Dom Casmurro`
  - `Emma`
- Explore opções `2` a `6`.

---


