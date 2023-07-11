# Analisador Semântico para a linguagem de LA (Linguagem Algorítmica).

Terceiro trabalho da disciplina de Construção de Compiladores, lecionada pelo professor Daniel Lucrédio.

<h3 align="center">
  Feito por:
</h3>

<div align="center">
  <table>
    <tr>
      <td>João Dini de Miranda</td>
      <td><code>790716</code></td>
    </tr>
    <tr>
      <td>Miguel Henrique Chinellato</td>
      <td><code>791964</code></td>
    </tr>
    <tr>
      <td>Vitor de Almeida Recoaro</td>
      <td><code>790035</code></td>
    </tr>
  </table>
</div>

---

## Compilação e Execução

Os requisitos para compilação são o **Java 11** e a ferramenta de gerenciamento de pacotes **Maven**.

Para compilar e executar o programa basta executar os comandos a seguir, a partir da pasta `t3-semantico`.

```bash
  mvn package
```

```bash
  java -jar target/t3-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar <caminho para o código fonte LA> [caminho para arquivo de saída]
```

Note que para rodar o programa manualmente, talvez seja útil não especificar um arquivo de saída. Nesse caso o comportamento do programa é imprimir na saída padrão do terminal.

## Rodando testes automáticos

Para fazer a correção do trabalho é possível utilizar o `run-corretor.sh` utilizando o seguinte comando a partir da raiz do repositório:

```bash
  bash testes/run-corretor.sh
```
