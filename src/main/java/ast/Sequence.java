package ast;

import java.util.*;

/** Representación de las secuencias de sentencias.
*/
public class Sequence extends Stmt {
	public final Stmt[] statements;

	public Sequence(Stmt[] statements) {
		this.statements = statements;
	}

	@Override public String unparse() {
		String result = "{ ";
					for (Stmt statement : statements) {
						result += statement.unparse() +" ";
					}
					return result +"}";
	}

	@Override public String toString() {
		return "Sequence("+ Arrays.toString(statements) +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + Arrays.hashCode(this.statements);
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Sequence other = (Sequence)obj;
		return Arrays.equals(this.statements, other.statements);
	}

	public static Sequence generate(Random random, int min, int max) {
		Stmt[] statements; 
		statements = new Stmt[random.nextInt(Math.max(0, max)+1)];
		for (int i = 0; i < statements.length; i++) {
			statements[i] = Stmt.generate(random, min-1, max-1);
		}
		return new Sequence(statements);
	}
	
	@Override public State evaluate(State state){
		for (Stmt statement : statements) {
			state = statement.evaluate(state);
		}
		return state;
	}	
}
