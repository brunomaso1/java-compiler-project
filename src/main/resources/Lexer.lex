package parser;

import java_cup.runtime.Symbol;
import ast.Tipo;
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
"y"
	{ return new Symbol(SIGNO_Y, yyline, yycolumn, yytext()); }
"o"
	{ return new Symbol(SIGNO_O, yyline, yycolumn, yytext()); }
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
"<"
	{ return new Symbol(SIGNO_MENOR, yyline, yycolumn, yytext()); }
">"
	{ return new Symbol(SIGNO_MAYOR, yyline, yycolumn, yytext()); }	
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
"hacer"
	{ return new Symbol(HACER, yyline, yycolumn, yytext()); }
"sino"
	{ return new Symbol(SINO, yyline, yycolumn, yytext()); }
"esFalso"
	{ return new Symbol(FALSO, yyline, yycolumn, yytext()); }
"si"
	{ return new Symbol(SI, yyline, yycolumn, yytext()); }
"nada"
	{ return new Symbol(NADA, yyline, yycolumn, yytext()); }
"entonces"
	{ return new Symbol(ENTONCES, yyline, yycolumn, yytext()); }
"esVerdadero"
	{ return new Symbol(VERDADERO, yyline, yycolumn, yytext()); }
"mientras"
	{ return new Symbol(MIENTRAS, yyline, yycolumn, yytext()); }
"funcion"
	{ return new Symbol(FUNCION, yyline, yycolumn, yytext()); }
"recibe"
	{ return new Symbol(RECIBE, yyline, yycolumn, yytext()); }	
"llamar"
	{ return new Symbol(LLAMAR, yyline, yycolumn, yytext()); }
"mostrar"
	{ return new Symbol(MOSTRAR, yyline, yycolumn, yytext()); }		
"mostrarLinea"
	{ return new Symbol(MOSTRAR_LINEA, yyline, yycolumn, yytext()); }
"ponerEn"
	{ return new Symbol(PONER_EN, yyline, yycolumn, yytext()); }
"largo"
	{ return new Symbol(LARGO, yyline, yycolumn, yytext()); }
"definida"
	{ return new Symbol(DEFINIDA, yyline, yycolumn, yytext()); }
"entero"
	{ return new Symbol(TIPO, yyline, yycolumn, Tipo.ENTERO); }
"texto"
	{ return new Symbol(TIPO, yyline, yycolumn, Tipo.TEXTO); }
"boolean"
	{ return new Symbol(TIPO, yyline, yycolumn, Tipo.BOOLEAN); }
"crearVariable"
	{ return new Symbol(CREAR_VARIABLE, yyline, yycolumn, yytext()); }	
"crearLista"
	{ return new Symbol(CREAR_LISTA, yyline, yycolumn, yytext()); }
"tipo"
	{ return new Symbol(SIM_TIPO, yyline, yycolumn, yytext()); }
"cantidad"
	{ return new Symbol(CANTIDAD, yyline, yycolumn, yytext()); }
"posicion"
	{ return new Symbol(POSICION, yyline, yycolumn, yytext()); }
"obtenerPosicion"
	{ return new Symbol(OBTENER_POSICION, yyline, yycolumn, yytext()); }
"en"
	{ return new Symbol(EN, yyline, yycolumn, yytext()); }
"{"
	{ return new Symbol(CORCHETE_IZQUIERDO, yyline, yycolumn, yytext()); }
"}"
	{ return new Symbol(CORCHETE_DERECHO, yyline, yycolumn, yytext()); }
[0-9]+
	{ String $1 = yytext(); Double $0 = Double.parseDouble($1);
	  return new Symbol(NUM, yyline, yycolumn, $0); }
[$][a-zA-Z0-9_]*
	{ String $1 = yytext(); String $0;
	  $0 = $1;
	  return new Symbol(ID, yyline, yycolumn, $0); }
[%][a-zA-Z0-9_]*
	{ String $1 = yytext(); String $0;
	  $0 = $1;
	  return new Symbol(IDFUNC, yyline, yycolumn, $0); }
(\"([^\"\\\n]|\\[^\n])*\")
	{ String $1 = yytext(); String $0 = String.valueOf($1); $0 = $0.substring(1,$0.length()-1);
	  return new Symbol(STRING, yyline, yycolumn, $0); }
[ \t\r\n\f\v]+
	{ /* Ignore */ }
\/\*+([^\*]|\*+[^\/])*\*+\/
	{ /* Ignore */ }
\/\/[^\n]*\n
	{ /* Ignore */ }
.
	{ return new Symbol(error, yyline, yycolumn, "Unexpected input <"+ yytext() +">!"); }