package ast;

import java.util.*;

import behaviour.*;

import java.io.*;

/**
 * Representacion de las negaciones.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Negacion extends Expresion {
	public final Expresion condition;

	public Negacion(Expresion condition) {
		this.condition = condition;
	}

	@Override public String unparse() {
		return "(!"+ condition.unparse() +")";
	}

	/*@Override public Object evaluate(Estado state) {
		if (condition.evaluate(state) instanceof Boolean)
			return !((Boolean)condition.evaluate(state));
		else {
			System.out.print("Estas haciendo una negacion mal. Numero1 -> " + condition.evaluate(state));
			return null;
		}
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		return condition.freeVariables(vars);
	}

	@Override public int maxStackIL() {
		return condition.maxStackIL();
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = condition.compileIL(ctx);
		ctx.codeIL.append("neg \n");
		return ctx;
	}
	
	@Override
	public Expresion optimization(Estado state) {
		Expresion exp = condition.optimization(state);
		
		if(exp instanceof ValorVerdad)
			return new ValorVerdad( ! ((ValorVerdad)exp).value );
		return this;
	}

	@Override public String toString() {
		return "Negacion("+ condition +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Negacion other = (Negacion)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition));
	}

	/*public static Negacion generate(Random random, int min, int max) {
		Expresion condition; 
		condition = Expresion.generate(random, min-1, max-1);
		return new Negacion(condition);
	}*/
	
	@Override public Object check(ChequearEstado checkstate){
		
		if (condition.check(checkstate).equals("boolean")){
			return new String("boolean");
		}else{
			Errores.exceptionList.add(new Errores("Negación \"" + condition.toString() + "\" no booleana."));
		}	
		return checkstate;
	}
	
}
