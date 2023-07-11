package br.ufscar.dc.compiladores;

import br.ufscar.dc.compiladores.LAParser.CmdAtribuicaoContext;
import br.ufscar.dc.compiladores.LAParser.Declaracao_funcoesContext;
import br.ufscar.dc.compiladores.LAParser.Declaracao_variaveisContext;
import br.ufscar.dc.compiladores.LAParser.DeclaracoesContext;
import br.ufscar.dc.compiladores.LAParser.IdentificadorContext;
import br.ufscar.dc.compiladores.TabelaDeSimbolos.TipoDeclaracao;

public class LASemantico extends LABaseVisitor<Void> {

    Escopo escopo = new Escopo();

    @Override
    public Void visitDeclaracoes
    (
        DeclaracoesContext ctx
    ) 
    {
        escopo.criarNovoEscopo();
        return super.visitDeclaracoes(ctx);
    }

    @Override
    public Void visitDeclaracao_funcoes
    (
        Declaracao_funcoesContext ctx
    )
    {
        TabelaDeSimbolos tabelaAtual = escopo.escopoAtual();

        if (ctx.PROCEDIMENTO() != null){
            String nome = ctx.PROCEDIMENTO().getText();

            // TODO: Colocar erro semântico caso o nome do procedimento já exista.
            if (tabelaAtual.existe(nome)){
                
            }
        }
        else if (ctx.FUNCAO() != null){
            String nome = ctx.FUNCAO().getText();
            
            // TODO: Colocar erro semântico caso o nome do procedimento já exista.
            if (tabelaAtual.existe(nome)){
            }
        }

        return super.visitDeclaracao_funcoes(ctx);
    }

    @Override
    public Void visitDeclaracao_variaveis
    (
        Declaracao_variaveisContext ctx
    )
    {
        TabelaDeSimbolos tabela = escopo.escopoAtual();

        if (ctx.DECLARE() != null){
            String nome = ctx.DECLARE().getText();

            if (tabela.existe(nome)){
                System.out.println("Variavel " + nome + "ja esta declarada");
            }
            else{
                LASemanticoUtils.verificarTipo(escopo, ctx.variavel());
            }
        }
        return super.visitDeclaracao_variaveis(ctx);
    }

    @Override
    public Void visitIdentificador
    (
        IdentificadorContext ctx
    ) 
    {
        Boolean existeIdentificador = LASemanticoUtils.existeIdentificador(ctx, escopo);
        String nome = ctx.IDENT().get(0).getText();

        if (!existeIdentificador){
            LASemanticoUtils.adicionarErroSemantico(ctx.start, "identificador " + nome + " nao declarado" );
        }

        return super.visitIdentificador(ctx);
    }

    @Override
    public Void visitCmdAtribuicao
    (
        CmdAtribuicaoContext ctx
    ) 
    {
        if (LASemanticoUtils.existeIdentificador(ctx.identificador(), escopo)){
            String nome = ctx.identificador().IDENT(0).getText();
            TipoDeclaracao tipoAlvo = LASemanticoUtils.getTipoDeTodosEscopos(escopo, nome);

            if (ctx.PONTEIRO() != null){
                tipoAlvo = TipoDeclaracao.PONTEIRO;
            }

            if (tipoAlvo == TipoDeclaracao.INVALIDO){
                LASemanticoUtils.adicionarErroSemantico(ctx.start, "identificador " + nome + " com tipo invalido");
            }
            else {
                TipoDeclaracao tipoExpressao = LASemanticoUtils.verificarTipo(ctx.expressao(), escopo);

                if (
                    tipoExpressao == TipoDeclaracao.INVALIDO || 
                    !LASemanticoUtils.tiposCompativeis(tipoExpressao, tipoAlvo)
                ){
                    String prefixo = "";

                    if (ctx.PONTEIRO() != null){
                        prefixo = ctx.PONTEIRO().getText();
                    }

                    LASemanticoUtils.adicionarErroSemantico(
                        ctx.start,
                        "atribuicao nao compativel para "+ prefixo + ctx.identificador().IDENT(0).getText()
                    );
                }
            }

        }

        return super.visitCmdAtribuicao(ctx);
    }
}