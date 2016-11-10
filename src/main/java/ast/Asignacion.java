package ast;

import java.util.*;

import behaviour.CompilationContextIL;

/**
 * Representacion de las asignaciones de valores a variables.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Asignacion extends Sentencia {
	public final String id;
	public final Expresion expression;

	public Asignacion(String id, Expresion expression) {
		this.id = id;
		this.expression = expression;
	}

	@Override public String unparse() {
		return id +" = "+ expression.unparse() +"; ";
	}

	/*@Override public Estado evaluate(Estado state) {
		state.set(id, expression.evaluate(state)); 
		return state;
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars = expression.freeVariables(vars); 
		vars.add(id); 
		return vars;
	}

	@Override public int maxStackIL() {
		return expression.maxStackIL();
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx= expression.compileIL(ctx);

		Integer index = ctx.variables.indexOf(id);
		ctx.codeIL.append("stloc " + index + "\n");
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state){				
		Expresion expresion = expression.optimization(state);
		if(expresion instanceof Numeral){
			state.set(id, ((Numeral)expresion).number);
		}
		return new Asignacion(id, expresion);
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
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		Par par = checkstate.devolverValor(id);
		if (par == null){
			Errores.exceptionList.add(new Errores("Variable \"" + id + "\" no definida"));
		}
		expression.check(checkstate);
		return checkstate;
	}

	/*public static Asignacion generate(Random random, int min, int max) {
		String id; Expresion expression; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		expression = Expresion.generate(random, min-1, max-1);
		return new Asignacion(id, expression);
	}*/
}