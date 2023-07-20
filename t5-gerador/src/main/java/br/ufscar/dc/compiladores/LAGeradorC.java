package br.ufscar.dc.compiladores;

import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.ibm.icu.text.Edits.Iterator;

import br.ufscar.dc.compiladores.TabelaDeSimbolos.TipoDeclaracao;

public class LAGeradorC extends LABaseVisitor<Void>{
    StringBuilder saida; //output saída
    TabelaDeSimbolos tabela;

    public LAGeradorC() {
        saida = new StringBuilder();
        this.tabela = new TabelaDeSimbolos();
    }

    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
        saida.append("#include <stdio.h>\n");
        saida.append("#include <stdlib.h>\n");
        saida.append("\n");
        ctx.declaracoes().declaracao_variaveis()
            .forEach(dec -> visitDeclaracao_variaveis(dec));
        ctx.declaracoes().declaracao_funcoes()
            .forEach(dec -> visitDeclaracao_funcoes(dec));
        saida.append("\n");
        saida.append("int main() {\n");
        ctx.corpo().declaracao_variaveis()
            .forEach(dec -> visitDeclaracao_variaveis(dec));
        ctx.corpo().cmd()
            .forEach(cmd -> visitCmd(cmd));
        saida.append("}\n");
        return null;
    }

    // @Override
    // public Void visitDeclaracao_variaveis(LAParser.Declaracao_variaveisContext ctx){
    //     if(ctx.DECLARE() != null){
    //         saida.append(ctx.variavel().tipo().getText() + " ");
    //         ctx.variavel().identificador().stream()
    //             .map(e->e.IDENT().stream().map(TerminalNode::getText).collect(Collectors.joining(".")))
    //             .collect(Collectors.joining(", "));
    //     }
    //     return null;
    // }
}
