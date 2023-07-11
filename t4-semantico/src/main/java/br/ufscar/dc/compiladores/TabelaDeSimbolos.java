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
    
    class EntradaTabelaDeSimbolos {
        TipoDeclaracao tipo;
        EntradaTabelaDeSimbolos dados;        

        private EntradaTabelaDeSimbolos(TipoDeclaracao tipo) {
            this.tipo = tipo;
        }

        private EntradaTabelaDeSimbolos(TipoDeclaracao tipo, EntradaTabelaDeSimbolos dados){
            this.tipo = tipo;
            this.dados = dados;
        }
    }
    
    private final HashMap<String, EntradaTabelaDeSimbolos> tabela;
    
    public TabelaDeSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String nome, TipoDeclaracao tipo) {
        tabela.put(nome, new EntradaTabelaDeSimbolos(tipo));
    }

    public void adicionar(String nome, TipoDeclaracao tipo, EntradaTabelaDeSimbolos dados){
        tabela.put(nome, new EntradaTabelaDeSimbolos(tipo, dados));
    }
    
    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
    
    public TipoDeclaracao verificar(String nome) {
        return tabela.get(nome).tipo;
    }   
}
