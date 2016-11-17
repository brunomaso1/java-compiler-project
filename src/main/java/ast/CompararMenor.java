/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.*;

/**
 * Representacion de las comparaciones por menor o igual.
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class CompararMenor extends Expresion {
	public final Expresion left;
	public final Expresion right;

	/**
	 * Constructor de la clase.
	 * @param left
	 * @param right
	 */
	public CompararMenor(Expresion left, Expresion right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" <= "+ right.unparse() +")";
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		return right.freeVariables(left.freeVariables(vars));
	}

	@Override public int maxStackIL() {
		return Math.max(left.maxStackIL(), right.maxStackIL())+ 1;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = left.compileIL(ctx);
		ctx = right.compileIL(ctx);
		ctx.codeIL.append("clt " + "\n");
		
		return ctx;
	}
	
	/**
	 * Optimizaciones:
	 * - Pliegue de constantes.
	 * - Simplificaciones algebraicas.
	 */
	@Override public Expresion optimization(Estado state){
		Expresion opt1 = left.optimization(state);
		Expresion opt2 = right.optimization(state);
		
		//a<b
		if(opt1 instanceof Numeral && opt2 instanceof Numeral){
			if( ((Numeral)opt1).number < ((Numeral)opt2).number)
				return new ValorVerdad(true);
			else
				return new ValorVerdad(false);
		}
		
		//Variable a == Variable a
		if(opt1 instanceof Variable && opt2 instanceof Variable){
			if( ((Variable)opt1).id.equals(((Variable)opt2).id)) {
				return new ValorVerdad(false);
			}
		}
		
		return new CompararMenor(opt1, opt2);
	}
	
	@Override public String toString() {
		return "CompararMenor("+ left +", "+ right +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.left == null ? 0 : this.left.hashCode());
		result = result * 31 + (this.right == null ? 0 : this.right.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		CompararMenor other = (CompararMenor)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}
	
	@Override
	public Object check(ChequearEstado checkstate) {
		if ((left.check(checkstate).equals("entero") & right.check(checkstate).equals("entero")))
			return new String("boolean");
		else {
			Errores.exceptionList.add(new Errores("Comparacion Menor \"" + this.toString() + "\" tipos no n�mericos."));
		}
		return checkstate;
	}
}