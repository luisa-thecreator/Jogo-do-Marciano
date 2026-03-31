# Jogo do Marciano

Jogo de adivinhação em Java: o computador escolhe um número secreto entre **1** e **100** e o jogador tenta descobrir com dicas **MAIOR** ou **MENOR**.

## Requisitos

- **JDK** (Java Development Kit) — por exemplo **17** ou **21**.  
  O instalador antigo do site “java.com” em geral **não** inclui o compilador; use um JDK completo.

### Instalar Java (exemplos no macOS)

- **Homebrew:** `brew install openjdk@21` (siga as instruções que o `brew` mostrar no final).  
- **Instalador:** baixe um JDK em [Adoptium (Eclipse Temurin)](https://adoptium.net) e instale o `.pkg`.

No terminal, confira se está instalado:

```bash
javac -version
java -version
```

Se aparecer a versão, está pronto.

## Como rodar

1. Baixe ou clone o repositório e abra um terminal.
2. Entre na pasta onde está o arquivo `JogoDoMarciano.java` (a raiz do projeto):

```bash
cd caminho/para/pasta-do-projeto
```

Substitua `caminho/para/pasta-do-projeto` pelo caminho real no seu computador (por exemplo, onde você descompactou o `.zip` ou clonou o repositório).

3. **Compile** e **execute**:

```bash
javac JogoDoMarciano.java
java JogoDoMarciano
```

Abre uma janela com a interface do jogo.

## Como jogar

- Digite um número de 1 a 100 no campo e clique em **TENTAR** (ou pressione **Enter**).
- Leia a dica e tente de novo até acertar.
- Use **Novo jogo** para sortear outro número sem fechar a janela.

## Arquivos

| Arquivo               | Descrição                      |
|-----------------------|--------------------------------|
| `JogoDoMarciano.java` | Código-fonte (interface Swing) |

Os arquivos `.class` são gerados pelo `javac` ao compilar; podem ser apagados e gerados de novo com os comandos acima.
