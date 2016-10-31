package parser;

import java_cup.runtime.Symbol;
import java.util.*;
import java.io.*;

%%

%unicode
%line
%column
%class Lexer
%cupsym Tokens
%cup
%implements Tokens

%{:

	public static List<Symbol> tokens(Reader input) throws IOException {
		Lexer lexer = new Lexer(input);
		List<Symbol> result = new ArrayList<Symbol>();
		for (Symbol token = lexer.next_token(); token.sym != Tokens.EOF; token = lexer.next_token()) {
			result.add(token);
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		Lexer lexer;
		if (args.length < 1) args = new String[]{ "" };
		for (int i = 0; i < args.length; ++i) {
			lexer = new Lexer(new InputStreamReader(args[i].length() > 0 ? new FileInputStream(args[i]) : System.in, "UTF8"));
			System.out.println(args[i] +":");
			for (Symbol token = lexer.next_token(); token.sym != Tokens.EOF; token = lexer.next_token()) {
				System.out.println("\t#"+ token.sym +" "+ token.value);
			}
		}
	}

%}

%%
"no"
	{ return new Symbol(SIGNO_NO, yyline, yycolumn, yytext()); }
"<>"
	{ return new Symbol(SIGNO_DISTINTO, yyline, yycolumn, yytext()); }
"y"
	{ return new Symbol(SIGNO_Y, yyline, yycolumn, yytext()); }
"("
	{ return new Symbol(PARENTESIS_IZQUIERDO, yyline, yycolumn, yytext()); }
")"
	{ return new Symbol(PARENTESIS_DERECHO, yyline, yycolumn, yytext()); }
"*"
	{ return new Symbol(SIGNO_MULT, yyline, yycolumn, yytext()); }
"+"
	{ return new Symbol(SIGNO_MAS, yyline, yycolumn, yytext()); }
"-"
	{ return new Symbol(SIGNO_MENOS, yyline, yycolumn, yytext()); }
"."
	{ return new Symbol(PUNTO, yyline, yycolumn, yytext()); }
"<="
	{ return new Symbol(SIGNO_MENOR_IGUAL, yyline, yycolumn, yytext()); }
">="
	{ return new Symbol(SIGNO_MAYOR_IGUAL, yyline, yycolumn, yytext()); }	
"="
	{ return new Symbol(SIGNO_IGUAL, yyline, yycolumn, yytext()); }
"poner"
	{ return new Symbol(PONER, yyline, yycolumn, yytext()); }
"en"
	{ return new Symbol(EN, yyline, yycolumn, yytext()); }
//"=="
//	{ return new Symbol(DOUBLE_EQUALS_SIGN, yyline, yycolumn, yytext()); }
"hacer"
	{ return new Symbol(HACER, yyline, yycolumn, yytext()); }
"sino"
	{ return new Symbol(SINO, yyline, yycolumn, yytext()); }
"falso"
	{ return new Symbol(FALSO, yyline, yycolumn, yytext()); }
"si"
	{ return new Symbol(SI, yyline, yycolumn, yytext()); }
"nada"
	{ return new Symbol(NADA, yyline, yycolumn, yytext()); }
"entonces"
	{ return new Symbol(ENTONCES, yyline, yycolumn, yytext()); }
"verdadero"
	{ return new Symbol(VERDADERO, yyline, yycolumn, yytext()); }
"mientras"
	{ return new Symbol(MIENTRAS, yyline, yycolumn, yytext()); }
"{"
	{ return new Symbol(PARENTESIS_CURVO_IZQUIERDO, yyline, yycolumn, yytext()); }
"}"
	{ return new Symbol(PARENTESIS_CURVO_DERECHO, yyline, yycolumn, yytext()); }
[0-9]+
	{ String $1 = yytext(); Double $0 = Double.parseDouble($1);
	  return new Symbol(NUM, yyline, yycolumn, $0); }
[$][a-zA-Z0-9_]*
	{ String $1 = yytext(); String $0;
	  $0 = $1;
	  return new Symbol(ID, yyline, yycolumn, $0); }
[ \t\r\n\f\v]+
	{ /* Ignore */ }
\/\*+([^\*]|\*+[^\/])*\*+\/
	{ /* Ignore */ }
\/\/[^\n]*\n
	{ /* Ignore */ }
.
	{ return new Symbol(error, yyline, yycolumn, "Unexpected input <"+ yytext() +">!"); }