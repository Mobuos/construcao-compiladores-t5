package br.ufscar.dc.compiladores;
import java.util.ArrayList;
import java.util.Iterator;

import br.ufscar.dc.compiladores.TabelaDeSimbolos.TipoDeclaracao;

public class LAGeradorUtils {
    // Array para armazenar o nome das variáveis para o printf.
    private static ArrayList<String> listaVariaveis = new ArrayList<String>();

    // Função auxiliar para colocar variáveis, na lista.
    public static Void adicionarVariavel(String nomeVariavel){
        listaVariaveis.add(nomeVariavel);

        return null;
    }

    // Função auxiliar para complementar a última variável.
    public static Void complementarVariavel(String complemento){
        int lastIndex = listaVariaveis.size() - 1;
        String valorAntigo = listaVariaveis.get(lastIndex);

        listaVariaveis.set(lastIndex, valorAntigo + complemento );
        
        return null;
    }

    // Função auxiliar para complementar a variável do índice `index`.
    public static Void complementarVariavel(String complemento, int index){
        String valorAntigo = listaVariaveis.get(index);

        listaVariaveis.set(index, valorAntigo + complemento );
        return null;
    }

    // Função para limpar lista de variaveis.
    public static Void limparListaVariavel(){
        listaVariaveis.clear();
        
        return null;
    }

    // Função auxiliar para retornar o iterator da lista de variáveis.
    public static Iterator<String> getIteratorLista(){
        return listaVariaveis.iterator();
    }

    // Função auxiliar para converter tipos da tabela de símbolo em strings
    // utilizadas no código C.
    public static String mapTipoC(TipoDeclaracao tipo) {
        String strC = "";
        switch (tipo) {
            case LITERAL:
                strC = "char";
                break;
            case INTEIRO:
                strC = "int";
                break;
            case REAL:
                strC = "float";
                break;
            case LOGICO:
                strC = "int";
                break;
            default:
                // Nunca irá acontecer, pois o analisador sintático
                // não permite
                break;
        }
        return strC;
    }

    // Função auxiliar para converter tipos da linguagem LA para o enumerador
    // da tabela de símbolos.
    public static TipoDeclaracao mapTipoDeclaracao(String strLA) {
        TipoDeclaracao tipo;
        switch (strLA) {
            case "literal":
                tipo = TipoDeclaracao.LITERAL;
                break;
            case "inteiro":
                tipo = TipoDeclaracao.INTEIRO;
                break;
            case "real":
                tipo = TipoDeclaracao.REAL;
                break;
            case "logico":
                tipo = TipoDeclaracao.LOGICO;
                break;
            default:
                tipo = TipoDeclaracao.INVALIDO;
                break;
        }
        return tipo;
    }

    // Função auxiliar para transformar um tipo da linguagem LA para
    // uma string de formatação em C (Utilizado na leitura e impressão)
    public static String TipoParaFormatString(TipoDeclaracao tipo){
        switch (tipo) {
            case INTEIRO:
                return "%d";
            case REAL:
                return "%f";
            case LITERAL:
                return "%s";
            default:
                System.out.println("Erro ao converter tipo \"" + tipo + "\" para Format String");
                return "ERR";
        }
    }
}
