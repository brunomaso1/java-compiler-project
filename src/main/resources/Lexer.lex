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
"!"
	{ return new Symbol(EXCLAMATION_MARK, yyline, yycolumn, yytext()); }
"&&"
	{ return new Symbol(DOUBLE_AMPERSAND, yyline, yycolumn, yytext()); }
"("
	{ return new Symbol(LEFT_PARENTHESIS, yyline, yycolumn, yytext()); }
")"
	{ return new Symbol(RIGHT_PARENTHESIS, yyline, yycolumn, yytext()); }
"*"
	{ return new Symbol(ASTERISK, yyline, yycolumn, yytext()); }
"+"
	{ return new Symbol(PLUS_SIGN, yyline, yycolumn, yytext()); }
"-"
	{ return new Symbol(HYPHEN_MINUS, yyline, yycolumn, yytext()); }
";"
	{ return new Symbol(SEMICOLON, yyline, yycolumn, yytext()); }
"<="
	{ return new Symbol(LESS_THAN_OR_EQUAL, yyline, yycolumn, yytext()); }
"="
	{ return new Symbol(EQUALS_SIGN, yyline, yycolumn, yytext()); }
"=="
	{ return new Symbol(DOUBLE_EQUALS_SIGN, yyline, yycolumn, yytext()); }
"do"
	{ return new Symbol(DO, yyline, yycolumn, yytext()); }
"else"
	{ return new Symbol(ELSE, yyline, yycolumn, yytext()); }
"false"
	{ return new Symbol(FALSE, yyline, yycolumn, yytext()); }
"if"
	{ return new Symbol(IF, yyline, yycolumn, yytext()); }
"skip"
	{ return new Symbol(SKIP, yyline, yycolumn, yytext()); }
"then"
	{ return new Symbol(THEN, yyline, yycolumn, yytext()); }
"true"
	{ return new Symbol(TRUE, yyline, yycolumn, yytext()); }
"while"
	{ return new Symbol(WHILE, yyline, yycolumn, yytext()); }
"{"
	{ return new Symbol(LEFT_CURLY_BRACKET, yyline, yycolumn, yytext()); }
"}"
	{ return new Symbol(RIGHT_CURLY_BRACKET, yyline, yycolumn, yytext()); }
[0-9]+
	{ String $1 = yytext(); Double $0 = Double.parseDouble($1);
	  return new Symbol(NUM, yyline, yycolumn, $0); }
[a-zA-Z_][a-zA-Z0-9_]*
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