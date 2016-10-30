package ast;

import java.util.*;
import behaviour.*;
import java.io.*;

/** Categoría sintáctica de las sentencias (statements) de While, las 
	construcciones del lenguaje que modifican (potencialmente) los 
	valores de las variables en el estado del programa.
*/
public abstract class Stmt {

	abstract public String unparse();

	abstract public State evaluate(State state);

	abstract public Set<String> freeVariables(Set<String> vars);

	abstract public int maxStackIL();

	abstract public CompilationContextIL compileIL(CompilationContextIL ctx);

	@Override public abstract String toString();

	@Override public abstract int hashCode();

	@Override public abstract boolean equals(Object obj);

	public static Stmt generate(Random random, int min, int max) {
		final int TERMINAL_COUNT = 0;
		final int NONTERMINAL_COUNT = 4;
		int i = random.nextInt(TERMINAL_COUNT + NONTERMINAL_COUNT);
		switch (i) {
		//Terminals
		//Non terminals
			case 0: return Assignment.generate(random, min-1, max-1);
			case 1: return Sequence.generate(random, min-1, max-1);
			case 2: return IfThenElse.generate(random, min-1, max-1);
			case 3: return WhileDo.generate(random, min-1, max-1);
			default: throw new Error("Unexpected error at Stmt.generate()!");
		}
	}
}
