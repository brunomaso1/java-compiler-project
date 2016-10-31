# PROPUESTA DE PROYECTO

## Idea:

<b> Definir un lenguaje de programación con palabras reservadas en español, similar a un pseudocódigo y a Python. Pensado para facilitar el aprendizaje de programación en la temprana edad. </b>

## Definición Variables:

<i>Todas las variables deben comenzar con el símbolo de pesos ($).</i>

## Ejemplos del lenguaje:
<ul>
	<li>Poner 5 en $variable</li>
	<li>si condicion entonces {
		BLOQUE
	}
	</li>
	<li>si condicion entonces {
		BLOQUE
	}
	sino {
		OTRO BLOQUE
	}
	</li>
	<li>repetir {
		BLOQUE
	} veces CANTIDAD
	</li>
	<li>mientras CONDICION hacer {
		BLOQUE
	}
	</li>
</ul>

### Símbolos booleanos:
Negación: no
Conjunción: y
Disyunción: o
Comparación: =, <, >, >=, <=

### Operadores aritméticos:

Suma: +
Resta: -
Multiplicación: *
División (entera): /
Módulo: resto

### Listas:

Se usa la asignación con una lista de elementos (pueden ser números o strings). 
Las listas comienzan en la posición 1.

#### Ejemplos:
<ul>
	<li>poner “pepe”,”juan”,”robertito” en $lista</li>
	<li>poner 1,2,3 en $lista2</li>
	<li>poner $lista3,$lista4,$lista5, en $lista6</li>
	<li>obtener POSICION de $lista</li>
	<li>poner VALOR en posicion POSICION de $lista</li>
</ul>

### Funciones pre definidas:

<ul>
	<li>ordenar LISTA</li>
</ul>

### Precedencia: 

<ol>
	<li>*, /, resto</li>
	<li>+, -</li>
	<li>=, <, >, >=, <=</li>
	<li>y, o</li>
</ol>

Ejemplo de división entera:
RESULTADO = $VARIABLE1 / $VARIABLE2
RESTO = $VARIABLE1 resto $VARIABLE2




Otros operadores:
Asignación: poner VALOR en $VARIABLE;
Concatenación de datos (el resultado es siempre un string excepto en el caso de listas que resulta en una nueva lista con los elementos de ambas):


Genéricamente: $VARIABLE1 unir $VARIABLE2;


Strings con strings: “hola” unir “mundo” 
Strings con números: “Propios“ unir 2255
Listas con listas: Lista1 unir Lista2 


TIPO DE DATOS: no fuertemente tipado como lo es python y javascript. Solo existen cadenas, números (solo permitir los números naturales incluyendo al 0) y listas.


Como declarar funciones:


funcion NOMBREFUNCION recibe VARIABLE1, VARIABLE2, …, VARIABLEN{
    BLOQUE
    devolver VALOR (esta línea es opcional)
}


mostrar VARIABLE




Ejemplos:


Ejemplo 1:
funcion sumarNumeros recibe numero1,numero2{
poner numero1 + numero2 en $resultado
devolver $resultado
}


mostrar sumarNumeros 1,2


En pantalla se mostrará el la suma de los numeros que se pasaron cómo parametro 1 + 2 = 3


Ejemplo 2:
poner 0 en $resultado
repetir 5 veces{
	mostrar $resultado
	poner $resultado + 1 en $resultado
}


El sistema mostrará la siguiente información:
	0
	1
	2
	3
	4


Ejemplo 3:
poner 0 en $resultado
mientras $resultado < 10 hacer{
si $resultado resto 2 = 0 entonces{
	mostrar $resultado
	poner $resultado + 1 en $resultado}
sino {
	poner $resultado + 1 en $resultado}
}
El sistema mostrará la siguiente información:
	0
	2
	4
	6
	8


Ejemplo 4:
poner 5,1,4,3,2 en lista
ordenar lista
mostrar lista


El sistema mostrará la siguiente información:
1,2,3,4,5


Ejemplo 5:


poner “hola ” en variable1
poner “mundo” en variable2
mostrar variable1 unir variable2




El sistema mostrará la siguiente información:
hola mundo


Ejemplo 6:


poner “pepe”,”juan”,”roberto” en lista1
poner “florinda”,”sofia”,”juana” en lista2


mostrar lista1 unir lista2


El sistema mostrará la siguiente información:
[pepe,juan,roberto,florinda,sofia,juana]


Ejemplo 7:


poner “pepe”,”juan”,”roberto” en lista1
obtener 1 de lista1


El sistema devuelve:
pepe

## Herramientas:
<ul>
	<li>Java como lenguaje de programación</li>
	<li>JFlex</li>
	<li>CUP</li>
</ul>

## Sintaxis abstracta (AST):
### Sentencias:
<ul>
	<li>SiEntonces</li>
	<li>SiEntoncesSino</li>
	<li>Repetir</li>
	<li>Mientras</li>
	<li>Imprimir</li>
	<li>Funcion</li>
</ul>

### Expresiones:
<ul>
	<li>Suma</li>
	<li>Resta</li>
	<li>Multiplicación</li>
	<li>División (entera)</li>
	<li>Resto</li>
	<li>Negacion</li>
	<li>Numeral</li>
	<li>Compracion por igual</li>
	<li>Comparacion por menor</li>
	<li>Comparacion por mayor</li>
	<li>Comparacion por mayor o igual</li>
	<li>Comparacion por menor o igual</li>
	<li>ValorVerdad</li>
	<li>Conjunción</li>
	<li>Disyunción</li>
	<li>Variable</li>
	<li>Largo</li>
	<li>Definir</li>
</ul>

## Sintaxis concreta (lexemas y gramática):

### Lexer:

"si"
	{ return new Symbol(SI, yyline, yycolumn, yytext()); }
"entonces"
	{ return new Symbol(ENTONCES, yyline, yycolumn, yytext()); }
"sino"
	{ return new Symbol(SINO, yyline, yycolumn, yytext()); }
"repetir"
	{ return new Symbol(REPETIR, yyline, yycolumn, yytext()); }
"veces"
	{ return new Symbol(VECES, yyline, yycolumn, yytext()); }
"mientras"
	{ return new Symbol(MIENTRAS, yyline, yycolumn, yytext()); }
"hacer"
	{ return new Symbol(HACER, yyline, yycolumn, yytext()); }
"no"
	{ return new Symbol(NO, yyline, yycolumn, yytext()); }
"y"
	{ return new Symbol(Y, yyline, yycolumn, yytext()); }
"o"
	{ return new Symbol(O, yyline, yycolumn, yytext()); }
"poner"
	{ return new Symbol(PONER, yyline, yycolumn, yytext()); }
"<="
	{ return new Symbol(MENOR_O_IGUAL, yyline, yycolumn, yytext()); }
">="
	{ return new Symbol(MAYOR_O_IGUAL, yyline, yycolumn, yytext()); }
"="
	{ return new Symbol(IGUAL, yyline, yycolumn, yytext()); }
"=="
	{ return new Symbol(DOBLE_IGUAL, yyline, yycolumn, yytext()); }
"*"
	{ return new Symbol(SIGNO_MULT, yyline, yycolumn, yytext()); }
"+"
	{ return new Symbol(SIGNO_MAS, yyline, yycolumn, yytext()); }
"-"
	{ return new Symbol(HYPHEN_MENOS, yyline, yycolumn, yytext()); }

### Parser: