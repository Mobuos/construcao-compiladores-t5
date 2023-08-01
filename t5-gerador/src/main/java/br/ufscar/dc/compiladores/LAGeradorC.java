package br.ufscar.dc.compiladores;

import java.util.Iterator;

import br.ufscar.dc.compiladores.LAParser.Exp_aritmeticaContext;
import br.ufscar.dc.compiladores.LAParser.Exp_relacionalContext;
import br.ufscar.dc.compiladores.LAParser.ExpressaoContext;
import br.ufscar.dc.compiladores.LAParser.FatorContext;
import br.ufscar.dc.compiladores.LAParser.Fator_logicoContext;
import br.ufscar.dc.compiladores.LAParser.IdentificadorContext;
import br.ufscar.dc.compiladores.LAParser.ParcelaContext;
import br.ufscar.dc.compiladores.LAParser.Parcela_logicaContext;
import br.ufscar.dc.compiladores.LAParser.Parcela_nao_unarioContext;
import br.ufscar.dc.compiladores.LAParser.Parcela_unarioContext;
import br.ufscar.dc.compiladores.LAParser.TermoContext;
import br.ufscar.dc.compiladores.LAParser.Termo_logicoContext;

import org.antlr.v4.runtime.tree.TerminalNode;
import br.ufscar.dc.compiladores.TabelaDeSimbolos.TipoDeclaracao;

public class LAGeradorC extends LABaseVisitor<Void>{
    StringBuilder saida;
    Escopo escopo;

    public LAGeradorC() {
        saida = new StringBuilder();
        this.escopo = new Escopo();
        
    }

    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
        // Declaração inicial de bibliotecas
        saida.append("#include <stdio.h>\n");
        saida.append("#include <stdlib.h>\n");
        saida.append("\n");
        escopo.criarNovoEscopo();

        // Área de declaração de variáveis globais e funções
        ctx.declaracoes().declaracao_variaveis()
            .forEach(dec -> visitDeclaracao_variaveis(dec));
        ctx.declaracoes().declaracao_funcoes()
            .forEach(dec -> visitDeclaracao_funcoes(dec));

        // Início da função principal
        saida.append("int main() {\n");
        escopo.criarNovoEscopo();

        ctx.corpo().declaracao_variaveis()
            .forEach(dec -> visitDeclaracao_variaveis(dec));
        ctx.corpo().cmd()
            .forEach(cmd -> visitCmd(cmd));
        saida.append("\treturn 0;\n}\n");
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
            saida.append("\t" + strTipoC + " ");

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
                    strIdentificador = strIdentificador.concat(strIdent);
                    // No caso de string, adicionar um tamanho para o vetor de
                    // caracteres com máximo definido em 80 (de acordo com os
                    // testes disponibilizados)
                    if (tipoVar == TipoDeclaracao.LITERAL){
                        saida.append("[80]");
                    }
                    if (ident.hasNext()) {
                        // Se existir mais de um IDENT, acessar campos do struct
                        // com o ponto
                        saida.append(".");
                        strIdentificador = strIdentificador.concat(".");
                    }
                }
                if (identificador.hasNext()){
                    // Se existir mais de um identificador, separar com vírgula
                    saida.append(", ");
                }
                // System.out.println("Adicionando identificador \"" + strIdentificador + "\" do tipo " + tipoVar);
                escopo.escopoAtual().adicionar(strIdentificador, tipoVar);
            }
            saida.append(";\n");
        }
        return null;
    }

    // '^'? identificador '<-' expressao
    @Override
    public Void visitCmdAtribuicao(LAParser.CmdAtribuicaoContext ctx) {
        saida.append("\t" + ctx.identificador().getText() + "=");
        visitExpressao(ctx.expressao());
        saida.append(";\n");
        return null;
    }

    // 'se' expressao 'entao' cmdIf+=cmd* ('senao' cmdElse+=cmd*)? 'fim_se'
    @Override
    public Void visitCmdSe(LAParser.CmdSeContext ctx) {
        saida.append("\tif ("); 
        visitExpressao(ctx.expressao()); 
        saida.append(") {\n");
        for (int i=0; i< ctx.cmdIf.size(); i++)
        {
            saida.append("\t");
            visitCmd(ctx.cmd(i));
        }
        saida.append("\t}\n");
        if (ctx.ELSE() != null){
            saida.append("\telse {\n");
            for (int j=ctx.cmdIf.size(); j< ctx.cmdIf.size() + ctx.cmdElse.size(); j++)
            {
                saida.append("\t");
                visitCmd(ctx.cmd(j));
            }
            saida.append("\t}\n");
        }
        return null;
    }

    @Override
    public Void visitOp_relacional(LAParser.Op_relacionalContext ctx) {
        if (ctx.IGUAL() != null) {
            saida.append(" == ");
        }
        else if (ctx.DIFERENTE() != null) {
            saida.append(" != ");
        }
        else if (ctx.MAIORIGUAL() != null) {
            saida.append(" >= ");
        }
        else if (ctx.MENORIGUAL() != null) {
            saida.append(" <= ");
        }
        else if (ctx.MAIOR() != null) {
            saida.append(" > ");
        }
        else if (ctx.MENOR() != null) {
            saida.append(" < ");
        }
        return null;
    }

    @Override
    public Void visitOp_logico_1(LAParser.Op_logico_1Context ctx){
        saida.append(" || ");
        return null;
    }

    @Override
    public Void visitOp_logico_2(LAParser.Op_logico_2Context ctx){
        saida.append(" && ");
        return null;
    }

    @Override
    public Void visitCmdLeia(LAParser.CmdLeiaContext ctx) {
        Iterator<IdentificadorContext> identificador = ctx.identificador().iterator();
        while(identificador.hasNext()){
            String nomeVar = identificador.next().getText();
            TipoDeclaracao tipoVar = escopo.escopoAtual().verificar(nomeVar);
            String formatString = LAGeradorUtils.TipoParaFormatString(tipoVar);

            if (formatString == "%s"){
                formatString = "%[^\\n]";
            }

            saida.append("\tscanf(\"" + formatString + "\", &" + nomeVar + ");\n");
        }
        
        return null;
    }

    @Override
    public Void visitExpressao(LAParser.ExpressaoContext ctx){
        visitTermo_logico(ctx.termo_logico(0));
        if (ctx.op_logico_1() != null){
            for (int i=0; i<ctx.op_logico_1().size(); i++){
                visitOp_logico_1(ctx.op_logico_1(i));
                visitTermo_logico(ctx.termo_logico(i+1));
            }
        }
        return null;
    }

    @Override
    public Void visitTermo_logico(LAParser.Termo_logicoContext ctx){
        visitFator_logico(ctx.fator_logico(0));
        if (ctx.op_logico_2() != null){
            for (int i=0; i<ctx.op_logico_2().size(); i++){
                visitOp_logico_2(ctx.op_logico_2(i));
                visitFator_logico(ctx.fator_logico(i+1));
            }
        }
        return null;
    }

    @Override
    public Void visitFator_logico(LAParser.Fator_logicoContext ctx){
        if (ctx.NOT() != null){
            saida.append("!(");
        }
        visitParcela_logica(ctx.parcela_logica());
        if (ctx.NOT() != null){
            saida.append(")");
        }
        return null;
    }

    @Override
    public Void visitParcela_logica(LAParser.Parcela_logicaContext ctx){
        if (ctx.TRUE() != null){
            saida.append("1");
        }
        else if (ctx.FALSE() != null){
            saida.append("0");
        }
        else{
            visitExp_relacional(ctx.exp_relacional());
        }
        return null;
    }
    
    @Override
    public Void visitExp_relacional(LAParser.Exp_relacionalContext ctx){
        saida.append(ctx.exp_aritmetica(0).getText());
        if (ctx.op_relacional() != null) {
            visitOp_relacional(ctx.op_relacional());
            saida.append(ctx.exp_aritmetica(1).getText());
        }
        return null;
    }

    @Override
    public Void visitCmdEscreva(LAParser.CmdEscrevaContext ctx) {
        saida.append("\tprintf(\"");

        for (ExpressaoContext expressao: ctx.expressao()){
            utilsExpressao(expressao, false);
        }

        // Adiciona aspas para fechar a string.
        saida.append("\"");

        Iterator<String> itLista = LAGeradorUtils.getIteratorLista();
        
        // Caso exista variáveis para serem mostradas.
        if (itLista.hasNext()){
            saida.append(", ");

            while (itLista.hasNext()){
                saida.append(itLista.next());
                
                if (itLista.hasNext()){
                    saida.append(", ");
                }
            }
        }

        saida.append(");\n");

        LAGeradorUtils.limparListaVariavel();

        return null;
    }

    // termo_logico (op_logico_1 termo_logico)*
    //                   'ou'
    public Void utilsExpressao(ExpressaoContext ctx, boolean append) {
        for (Termo_logicoContext termoLogico: ctx.termo_logico()){
            utilsTermo_logico(termoLogico, append);
        }

        return null;
    }

    // fator_logico (op_logico_2 fator_logico)*
    //                  'e'
    public Void utilsTermo_logico(Termo_logicoContext ctx, boolean append) {
        for (Fator_logicoContext fatorLogico: ctx.fator_logico()){
            utilsFator_logico(fatorLogico, append);
        }

        return null;
    }

    // 'nao'? parcela_logica
    public Void utilsFator_logico(Fator_logicoContext ctx, boolean append) {
        if (ctx.parcela_logica() != null){
            utilsParcela_logica(ctx.parcela_logica(), append);
        }

        return null;
    }

    // : ( 'verdadeiro' | 'falso' )
	// | exp_relacional
    public Void utilsParcela_logica(Parcela_logicaContext ctx, boolean append) {
        if (ctx.exp_relacional() != null){
            utilsExp_relacional(ctx.exp_relacional(), append);
        }

        else{
            
        }

        return null;
    }

    // exp_aritmetica (op_relacional exp_aritmetica)?
    //      '=' | '<>' | '>=' | '<=' | '>' | '<'
    public Void utilsExp_relacional(Exp_relacionalContext ctx, boolean append) {
        for (Exp_aritmeticaContext expAritmetica: ctx.exp_aritmetica()){
            utilsExp_aritmetica(expAritmetica, append);
        }
        return null;
    }
    
    // termo (op1 termo)*
    //     '+' | '-'
    public Void utilsExp_aritmetica(Exp_aritmeticaContext ctx, boolean append) {
        utilsTermo(ctx.termo(0), append);

        if (ctx.op1() != null){
            for (int i = 0; i < ctx.op1().size(); i++){
                // Complementa a última variável que foi colocada com a operação.
                LAGeradorUtils.complementarVariavel(ctx.op1(i).getText());
                utilsTermo(ctx.termo(i + 1), true);
            }
        }
        return null;
    }

    // fator (op2 fator)*
    //     '*' | '/'
    public Void utilsTermo(TermoContext ctx, boolean append) {
        for (FatorContext fator: ctx.fator()){
            utilsFator(fator, append);
        }
        return null;
    }

    // parcela (op3 parcela)*
    //          '%'
    public Void utilsFator(FatorContext ctx, boolean append) {
        for (ParcelaContext parcela: ctx.parcela()){
            utilsParcela(parcela, append);
        }
        
        return null;
    }

    // op_unario? parcela_unario | parcela_nao_unario
    //   '-'
    public Void utilsParcela(ParcelaContext ctx, boolean append) {
        if (ctx.parcela_nao_unario() != null){
            utilsParcela_nao_unario(ctx.parcela_nao_unario(), append);
        }
        else{
            utilsParcela_unario(ctx.parcela_unario(), append);
        }

        return null;
    }

    // : '^'? identificador
	// | cmdChamada
	// | NUM_INT
	// | NUM_REAL
	// | '(' exp_unica=expressao ')'
    public Void utilsParcela_unario(Parcela_unarioContext ctx, boolean append) {
        if (ctx.identificador() != null){
            utilsIdentificador(ctx.identificador(), append);
        }
        
        return null;
    }

    // '&' identificador | CADEIA
    public Void utilsParcela_nao_unario(Parcela_nao_unarioContext ctx, boolean append) {
        if (ctx.CADEIA() != null){
            // Remove as aspas da CADEIA.
            saida.append(ctx.CADEIA().getText().replace("\"", ""));
        }

        return null;
    }

    public Void utilsIdentificador(IdentificadorContext ctx, boolean append) {
        String nomeVar = ctx.IDENT(0).getText();
        String formatString = "";

        // Caso seja um registro monta o nome da variável com pontos.
        for(int i = 0; i < ctx.PONTO().size(); i++){
            nomeVar += "." + ctx.IDENT(i + 1);
        }

        formatString = LAGeradorUtils.TipoParaFormatString(escopo.verificaTodosEscopos(nomeVar));

        if (formatString != "ERR"){
            if (append){
                LAGeradorUtils.complementarVariavel(nomeVar);
            }
            else{
                LAGeradorUtils.adicionarVariavel(nomeVar);
                saida.append(formatString);
            }
        }

        return null;
    }

}
