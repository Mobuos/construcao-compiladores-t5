package br.ufscar.dc.compiladores;

import java.util.Iterator;
import br.ufscar.dc.compiladores.LAParser.IdentificadorContext;

import org.antlr.v4.runtime.tree.TerminalNode;
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

    @Override
    public Void visitDeclaracao_variaveis(LAParser.Declaracao_variaveisContext ctx){
        TipoDeclaracao tipoVar = TabelaDeSimbolos.TipoDeclaracao.INVALIDO;
        if(ctx.DECLARE() != null){
            String strTipoLA = ctx.variavel().tipo().getText();
            tipoVar = LAGeradorUtils.mapTipoDeclaracao(strTipoLA);
            String strTipoC = LAGeradorUtils.mapTipoC(tipoVar);

            // Adicionando tipo da variável
            saida.append(strTipoC + " ");

            // Loop pelos identificadores, formando uma variavel
            Iterator<IdentificadorContext> identificador = ctx.variavel().identificador().iterator();
            while(identificador.hasNext()){
                IdentificadorContext i = identificador.next();

                // Loop pelos IDENTs, formando um identificador
                Iterator<TerminalNode> ident = i.IDENT().iterator();
                String strIdentificador = "";
                while(ident.hasNext()){
                    String strIdent = ident.next().getText();
                    saida.append(strIdent);
                    if (tipoVar == TipoDeclaracao.LITERAL){
                        saida.append("[80]");
                    }
                    if (ident.hasNext()) {
                        // Se existir mais de um IDENT, acessar campos do struct
                        // com o ponto
                        saida.append(".");
                    }
                }
                if (identificador.hasNext()){
                    // Se existir mais de um identificador, separar com vírgula
                    saida.append(", ");
                }
                tabela.adicionar(strIdentificador, tipoVar);
            }
            saida.append(";\n");
        }
        return null;
    }
}
