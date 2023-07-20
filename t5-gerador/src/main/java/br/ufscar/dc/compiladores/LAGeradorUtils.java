package br.ufscar.dc.compiladores;
import br.ufscar.dc.compiladores.TabelaDeSimbolos.TipoDeclaracao;

public class LAGeradorUtils {
    public static String mapTipoC(TipoDeclaracao tipo) {
        String strC = "";
        switch (tipo) {
            case LITERAL: //TODO: Fazer isso aqui ficar certo (kk)
                strC = "char";
            case INTEIRO:
                strC = "int";
                break;
            case REAL:
                strC = "float";
                break;
            case LOGICO:
                strC = "int";
            default:
                // Nunca irá acontecer, pois o analisador sintático
                // não permite
                break;
        }
        return strC;
    }

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
            case "logico":
                tipo = TipoDeclaracao.LOGICO;
                break;
            default:
                tipo = TipoDeclaracao.INVALIDO;
                break;
        }
        return tipo;
    }
}
