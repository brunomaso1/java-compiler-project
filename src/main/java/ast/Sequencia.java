package ast;

import java.util.*;
import behaviour.*;
import java.io.*;

/**
 * Representacion de las secuencias de instrucciones.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Sequencia extends Stmt {
	public final Stmt[] statements;

	public Sequencia(Stmt[] statements) {
		this.statements = statements;
	}

	@Override public String unparse() {
		String result = "{ ";
		for (Stmt statement : statements) result += statement.unparse() +" ";
		return result +"}";
	}

	@Override public State evaluate(State state) {
		for (Stmt statement : statements) state = statement.evaluate(state);
					return state;
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		for (Stmt statement : statements) vars = statement.freeVariables(vars);
					return vars;
	}

	@Override public int maxStackIL() {
		int result = 0;
					for (Stmt statement : statements) result = Math.max(result, statement.maxStackIL());
					return result;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		for (Stmt stmt : statements) {
			ctx = stmt.compileIL(ctx);
		}
		return ctx;
	}

	@Override public String toString() {
		return "Sequencia("+ Arrays.toString(statements) +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + Arrays.hashCode(this.statements);
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Sequencia other = (Sequencia)obj;
		return Arrays.equals(this.statements, other.statements);
	}

	public static Sequencia generate(Random random, int min, int max) {
		Stmt[] statements; 
		statements = new Stmt[random.nextInt(Math.max(0, max)+1)];
		for (int i = 0; i < statements.length; i++) {
			statements[i] = Stmt.generate(random, min-1, max-1);
		}
		return new Sequencia(statements);
	}
}
