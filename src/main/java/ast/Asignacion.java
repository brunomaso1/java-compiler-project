/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.CompilationContextIL;

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
		return vars;
	}

	@Override public int maxStackIL() {
		return expression.maxStackIL();
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx= expression.compileIL(ctx);

		Integer index = ctx.variables.indexOf(id);
		ctx.codeIL.append("stloc " + index + " // "+id+"\n");
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state){				
		Expresion der = expression.optimization(state);
		if(der instanceof Numeral){
			state.set(id, ((Numeral)der).number);
		}
		return new Asignacion(id, der);		
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
		else {
			if (expression.check(checkstate).equals(par.getTipo().toString())){
				par.setInicializada(true);
				checkstate.agregar(id, par);
				return checkstate;
			}else{
				Errores.exceptionList.add(new Errores("Error en la asignacion \"" + this.toString() + "\" tipos no coinciden."));
			}
		}
		return checkstate;

	}

	public String getId() {
		return id;
	}

	public Expresion getExpression() {
		return expression;
	}

	/*public static Asignacion generate(Random random, int min, int max) {
		String id; Expresion expression; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		expression = Expresion.generate(random, min-1, max-1);
		return new Asignacion(id, expression);
	}*/
}