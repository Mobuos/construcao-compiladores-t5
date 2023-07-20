// Generated from /mnt/Windows-SSD/Users/jdmdi/OneDrive/Documentos/UFSCar/2023-1/Compiladores/construcao-compiladores-t5/t5-gerador/src/main/antlr4/br/ufscar/dc/compiladores/LA.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LALexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMENTARIO=1, ALGORITMO=2, FIM_ALGORITMO=3, DECLARE=4, CONSTANTE=5, LITERAL=6, 
		INTEIRO=7, REAL=8, LOGICO=9, TRUE=10, FALSE=11, AND=12, OR=13, NOT=14, 
		IF=15, THEN=16, ELSE=17, ENDIF=18, CASO=19, SEJA=20, FIM_CASO=21, PARA=22, 
		ATE=23, FACA=24, FIM_PARA=25, WHILE=26, ENDWHILE=27, TIPO=28, REGISTRO=29, 
		FIM_REGISTRO=30, PROCEDIMENTO=31, VAR=32, FIM_PROCEDIMENTO=33, FUNCAO=34, 
		RETORNE=35, FIM_FUNCAO=36, LEIA=37, ESCREVA=38, INTERVALO=39, MENOR=40, 
		MENORIGUAL=41, MAIOR=42, MAIORIGUAL=43, IGUAL=44, DIFERENTE=45, DELIM=46, 
		ABREPAR=47, FECHAPAR=48, ABRECHAVE=49, FECHACHAVE=50, VIRGULA=51, ASPAS=52, 
		DIVISAO=53, MOD=54, SOMA=55, SUBTRACAO=56, MULTIPLICACAO=57, ATRIBUICAO=58, 
		PONTEIRO=59, ENDERECO=60, PONTO=61, NUM_INT=62, NUM_REAL=63, IDENT=64, 
		CADEIA=65, CADEIA_N_FECHADA=66, WS=67, COMENT_N_FECHADO=68, ERRO=69;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"COMENTARIO", "ALGORITMO", "FIM_ALGORITMO", "DECLARE", "CONSTANTE", "LITERAL", 
			"INTEIRO", "REAL", "LOGICO", "TRUE", "FALSE", "AND", "OR", "NOT", "IF", 
			"THEN", "ELSE", "ENDIF", "CASO", "SEJA", "FIM_CASO", "PARA", "ATE", "FACA", 
			"FIM_PARA", "WHILE", "ENDWHILE", "TIPO", "REGISTRO", "FIM_REGISTRO", 
			"PROCEDIMENTO", "VAR", "FIM_PROCEDIMENTO", "FUNCAO", "RETORNE", "FIM_FUNCAO", 
			"LEIA", "ESCREVA", "INTERVALO", "MENOR", "MENORIGUAL", "MAIOR", "MAIORIGUAL", 
			"IGUAL", "DIFERENTE", "DELIM", "ABREPAR", "FECHAPAR", "ABRECHAVE", "FECHACHAVE", 
			"VIRGULA", "ASPAS", "DIVISAO", "MOD", "SOMA", "SUBTRACAO", "MULTIPLICACAO", 
			"ATRIBUICAO", "PONTEIRO", "ENDERECO", "PONTO", "NUM_INT", "NUM_REAL", 
			"IDENT", "CADEIA", "CADEIA_N_FECHADA", "WS", "COMENT_N_FECHADO", "ERRO"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'algoritmo'", "'fim_algoritmo'", "'declare'", "'constante'", 
			"'literal'", "'inteiro'", "'real'", "'logico'", "'verdadeiro'", "'falso'", 
			"'e'", "'ou'", "'nao'", "'se'", "'entao'", "'senao'", "'fim_se'", "'caso'", 
			"'seja'", "'fim_caso'", "'para'", "'ate'", "'faca'", "'fim_para'", "'enquanto'", 
			"'fim_enquanto'", "'tipo'", "'registro'", "'fim_registro'", "'procedimento'", 
			"'var'", "'fim_procedimento'", "'funcao'", "'retorne'", "'fim_funcao'", 
			"'leia'", "'escreva'", "'..'", "'<'", "'<='", "'>'", "'>='", "'='", "'<>'", 
			"':'", "'('", "')'", "'['", "']'", "','", "'\"'", "'/'", "'%'", "'+'", 
			"'-'", "'*'", "'<-'", "'^'", "'&'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COMENTARIO", "ALGORITMO", "FIM_ALGORITMO", "DECLARE", "CONSTANTE", 
			"LITERAL", "INTEIRO", "REAL", "LOGICO", "TRUE", "FALSE", "AND", "OR", 
			"NOT", "IF", "THEN", "ELSE", "ENDIF", "CASO", "SEJA", "FIM_CASO", "PARA", 
			"ATE", "FACA", "FIM_PARA", "WHILE", "ENDWHILE", "TIPO", "REGISTRO", "FIM_REGISTRO", 
			"PROCEDIMENTO", "VAR", "FIM_PROCEDIMENTO", "FUNCAO", "RETORNE", "FIM_FUNCAO", 
			"LEIA", "ESCREVA", "INTERVALO", "MENOR", "MENORIGUAL", "MAIOR", "MAIORIGUAL", 
			"IGUAL", "DIFERENTE", "DELIM", "ABREPAR", "FECHAPAR", "ABRECHAVE", "FECHACHAVE", 
			"VIRGULA", "ASPAS", "DIVISAO", "MOD", "SOMA", "SUBTRACAO", "MULTIPLICACAO", 
			"ATRIBUICAO", "PONTEIRO", "ENDERECO", "PONTO", "NUM_INT", "NUM_REAL", 
			"IDENT", "CADEIA", "CADEIA_N_FECHADA", "WS", "COMENT_N_FECHADO", "ERRO"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LALexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LA.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 0:
			COMENTARIO_action((RuleContext)_localctx, actionIndex);
			break;
		case 66:
			WS_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void COMENTARIO_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			skip();
			break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			skip();
			break;
		}
	}
