package ast;

import java.util.*;

/** Representación de las asignaciones de valores a variables.
*/
public class Asignacion extends Stmt {
	public final String id;
	public final Exp expression;

	public Asignacion(String id, Exp expression) {
		this.id = id;
		this.expression = expression;
	}

	@Override public String unparse() {
		return id +" = "+ expression.unparse() +"; ";
	}

	@Override public String toString() {
		return "Assignment("+ id +", "+ expression +")";
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
		String id; Exp expression; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		expression = Exp.generate(random, min-1, max-1);
		return new Asignacion(id, expression);
	}
	
	@Override public State evaluate(State state){
		Object aux = expression.evaluate(state);
		if (aux.getClass().equals(Integer.class)) {
			state.agregar(id, (Integer)aux);
		}else if (aux.getClass().equals(Double.class)) {
			state.agregar(id, (Double)aux);
		}else if (aux.getClass().equals(String.class)) {
			state.agregar(id, (String)aux);
		}else if (aux.getClass().equals(Boolean.class)) {
			state.agregar(id, (Boolean)aux);
		}
		return state;
	}		
}
