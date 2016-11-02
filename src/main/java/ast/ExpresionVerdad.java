package ast;

import java.util.*;

import behaviour.*;

import java.io.*;

/** Categoría sintáctica de las expresiones booleanas de While, las 
	construcciones del lenguaje que evalúan a un valor de verdad (booleano).
*/
public abstract class ExpresionVerdad {

	abstract public String unparse();

	abstract public Boolean evaluate(State state);

	abstract public Set<String> freeVariables(Set<String> vars);

	abstract public int maxStackIL();

	abstract public CompilationContextIL compileIL(CompilationContextIL ctx);

	abstract public ExpresionVerdad optimization(State state);
	
	@Override public abstract String toString();

	@Override public abstract int hashCode();

	@Override public abstract boolean equals(Object obj);

	public static ExpresionVerdad generate(Random random, int min, int max) {
		final int TERMINAL_COUNT = 1;
		final int NONTERMINAL_COUNT = 6;
		int i = min > 0 ? random.nextInt(NONTERMINAL_COUNT) + TERMINAL_COUNT
			: random.nextInt(max > 0 ? NONTERMINAL_COUNT + TERMINAL_COUNT: TERMINAL_COUNT);
		switch (i) {
		//Terminals
			case 0: return ValorVerdad.generate(random, min-1, max-1);
		//Non terminals
			case 1: return CompararIgual.generate(random, min-1, max-1);
			case 2: return CompararMenorOIgual.generate(random, min-1, max-1);
			case 3: return CompararMayorOIgual.generate(random, min-1, max-1);
			case 4: return Negacion.generate(random, min-1, max-1);
			case 5: return Conjuncion.generate(random, min-1, max-1);
			default: throw new Error("Unexpected error at BExp.generate()!");
		}
	}
}
