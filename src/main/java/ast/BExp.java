package ast;

import java.util.*;
import behaviour.*;
import java.io.*;

/** Categoría sintáctica de las expresiones booleanas de While, las 
	construcciones del lenguaje que evalúan a un valor de verdad (booleano).
*/
public abstract class BExp {

	abstract public String unparse();

	abstract public Boolean evaluate(State state);

	abstract public Set<String> freeVariables(Set<String> vars);

	abstract public int maxStackIL();

	abstract public CompilationContextIL compileIL(CompilationContextIL ctx);

	@Override public abstract String toString();

	@Override public abstract int hashCode();

	@Override public abstract boolean equals(Object obj);

	public static BExp generate(Random random, int min, int max) {
		final int TERMINAL_COUNT = 1;
		final int NONTERMINAL_COUNT = 4;
		int i = min > 0 ? random.nextInt(NONTERMINAL_COUNT) + TERMINAL_COUNT
			: random.nextInt(max > 0 ? NONTERMINAL_COUNT + TERMINAL_COUNT: TERMINAL_COUNT);
		switch (i) {
		//Terminals
			case 0: return TruthValue.generate(random, min-1, max-1);
		//Non terminals
			case 1: return CompareEqual.generate(random, min-1, max-1);
			case 2: return CompareLessOrEqual.generate(random, min-1, max-1);
			case 3: return Negation.generate(random, min-1, max-1);
			case 4: return Conjunction.generate(random, min-1, max-1);
			default: throw new Error("Unexpected error at BExp.generate()!");
		}
	}
}
