package ast;

import java.util.*;
import behaviour.*;
import java.io.*;

/** Categoría sintáctica de las expresiones aritméticas de While, las 
	construcciones del lenguaje que evalúan a un número.
*/
public abstract class AExp {

	abstract public String unparse();

	abstract public Double evaluate(State state);

	abstract public Set<String> freeVariables(Set<String> vars);

	abstract public int maxStackIL();

	abstract public CompilationContextIL compileIL(CompilationContextIL ctx);

	@Override public abstract String toString();

	@Override public abstract int hashCode();

	@Override public abstract boolean equals(Object obj);

	public static AExp generate(Random random, int min, int max) {
		final int TERMINAL_COUNT = 2;
		final int NONTERMINAL_COUNT = 3;
		int i = min > 0 ? random.nextInt(NONTERMINAL_COUNT) + TERMINAL_COUNT
			: random.nextInt(max > 0 ? NONTERMINAL_COUNT + TERMINAL_COUNT: TERMINAL_COUNT);
		switch (i) {
		//Terminals
			case 0: return Numeral.generate(random, min-1, max-1);
			case 1: return Variable.generate(random, min-1, max-1);
		//Non terminals
			case 2: return Multiplication.generate(random, min-1, max-1);
			case 3: return Addition.generate(random, min-1, max-1);
			case 4: return Subtraction.generate(random, min-1, max-1);
			default: throw new Error("Unexpected error at AExp.generate()!");
		}
	}
}
