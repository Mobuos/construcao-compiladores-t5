package br.ufscar.dc.compiladores;

import br.ufscar.dc.compiladores.LAParser.CmdAtribuicaoContext;
import br.ufscar.dc.compiladores.LAParser.Declaracao_funcoesContext;
import br.ufscar.dc.compiladores.LAParser.Declaracao_variaveisContext;
import br.ufscar.dc.compiladores.LAParser.DeclaracoesContext;
import br.ufscar.dc.compiladores.LAParser.IdentificadorContext;
import br.ufscar.dc.compiladores.LAParser.RegistroContext;
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
        if (ctx.DECLARE() != null){
            TabelaDeSimbolos tabelaAtual = escopo.escopoAtual();

            LASemanticoUtils.adicionarIdentificadoresNaTabela(escopo, tabelaAtual, ctx.variavel());
        }
        if (ctx.TIPO() != null){
            LASemanticoUtils.adicionarRegistroNoEscopo(escopo, ctx.registro(), ctx.IDENT().getText(), true);
        }
        return super.visitDeclaracao_variaveis(ctx);
    }

    @Override
    public Void visitIdentificador
    (
        IdentificadorContext ctx
    ) 
    {
        Boolean existeIdentificador = LASemanticoUtils.existeIdentificadorTodosEscopos(ctx, escopo);
        String nome = ctx.IDENT(0).getText();

        if (ctx.PONTO() != null){
                for (int i = 0; i < ctx.PONTO().size(); i++){
                    nome += "." + ctx.IDENT(i+1);
                }
            }
        
        if (!existeIdentificador && !(ctx.parent.parent instanceof RegistroContext)){
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
        if (LASemanticoUtils.existeIdentificadorTodosEscopos(ctx.identificador(), escopo)){
            String nome = ctx.identificador().IDENT(0).getText();

            if (ctx.identificador().PONTO() != null){
                for (int i = 0; i < ctx.identificador().PONTO().size(); i++){
                    nome += "." + ctx.identificador().IDENT(i+1);
                }
            }

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
                        "atribuicao nao compativel para "+ prefixo + ctx.identificador().getText()
                    );
                }
            }

        }

        return super.visitCmdAtribuicao(ctx);
    }
}