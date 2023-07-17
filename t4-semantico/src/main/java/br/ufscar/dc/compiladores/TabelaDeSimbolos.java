package br.ufscar.dc.compiladores;

import java.util.HashMap;

public class TabelaDeSimbolos {
    public enum TipoDeclaracao {
        INTEIRO,
        REAL,
        LITERAL,
        LOGICO,
        REGISTRO,
        PONTEIRO,
        FUNCAO,
        PROCEDIMENTO,
        ENDERECO,
        INVALIDO
    }
    
    private class EntradaTabelaDeSimbolos {
        TipoDeclaracao tipo;
        // TabelaDeSimbolos dados = null;        

        private EntradaTabelaDeSimbolos(TipoDeclaracao tipo) {
            this.tipo = tipo;
            // this.dados = null;
        }

        private EntradaTabelaDeSimbolos(TipoDeclaracao tipo, TabelaDeSimbolos dados){
            this.tipo = tipo;
            // this.dados = dados;
        }
    }
    
    private final HashMap<String, EntradaTabelaDeSimbolos> tabela;
    
    public TabelaDeSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String nome, TipoDeclaracao tipo) {
        tabela.put(nome, new EntradaTabelaDeSimbolos(tipo));
    }

    public void adicionar(String nome, TipoDeclaracao tipo, TabelaDeSimbolos dados){
        tabela.put(nome, new EntradaTabelaDeSimbolos(tipo, dados));
    }
    
    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
    
    public TipoDeclaracao verificar(String nome) {
        return tabela.get(nome).tipo;
    }   
}
