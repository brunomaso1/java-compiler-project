package ast;

import java.util.*;
import behaviour.*;

/**
 * Representacion de las asignaciones de valores a variables.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Asignacion extends Stmt {
	public final String id;
	public final AExp expression;

	public Asignacion(String id, AExp expression) {
		this.id = id;
		this.expression = expression;
	}

	@Override public String unparse() {
		return id +" = "+ expression.unparse() +"; ";
	}

	@Override public State evaluate(State state) {
		state.set(id, expression.evaluate(state)); 
		return state;
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars = expression.freeVariables(vars); vars.add(id); 
		return vars;
	}

	@Override public int maxStackIL() {
		return expression.maxStackIL();
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx= expression.compileIL(ctx);

		Integer index = ctx.variables.indexOf(id);
		ctx.codeIL.append("stloc." + index + "\n");
		return ctx;
	}

	@Override public String toString() {
		return "Asignacion("+ id +", "+ expression +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		result = result * 31 + (this.expression == null ? 0 : this.expression.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Asignacion other = (Asignacion)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id))
			&& (this.expression == null ? other.expression == null : this.expression.equals(other.expression));
	}

	public static Asignacion generate(Random random, int min, int max) {
		String id; AExp expression; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		expression = AExp.generate(random, min-1, max-1);
		return new Asignacion(id, expression);
	}
}