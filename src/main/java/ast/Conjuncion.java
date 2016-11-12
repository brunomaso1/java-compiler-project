package ast;

import java.util.*;

import behaviour.*;

/**
 * Representacion de conjunciones booleanas.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Conjuncion extends Expresion {
	public final Expresion left;
	public final Expresion right;

	public Conjuncion(Expresion left, Expresion right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" & "+ right.unparse() +")";
	}

	/*@Override
	public Object evaluate(Estado state) {
		if ((left.evaluate(state) instanceof Boolean) & (right.evaluate(state) instanceof Boolean))
			return new Boolean((Boolean)left.evaluate(state) && (Boolean)right.evaluate(state));
		else {
			System.out.print("Estas haciendo una conjuncion mal. Nuemro1 -> " + left.evaluate(state) + " Numero2 -> " + right.evaluate(state));
			return null;
		}
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		return right.freeVariables(left.freeVariables(vars));
	}

	@Override public int maxStackIL() {
		return Math.max(left.maxStackIL(), right.maxStackIL() + 1);
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = left.compileIL(ctx);
		ctx = right.compileIL(ctx);
		ctx.codeIL.append("and \n");
		return ctx;
	}
	
	@Override public Expresion optimization(Estado state){
		Expresion opt1 = left.optimization(state);
		Expresion opt2 = right.optimization(state);
		
		//NUM a == NUM b
		if(opt1 instanceof ValorVerdad && opt2 instanceof ValorVerdad){
			if( ((ValorVerdad)opt1).value && ((ValorVerdad)opt2).value)
				return new ValorVerdad(true);
			else
				return new ValorVerdad(false);
		}

		//True & BExp b --> b
		if(opt1 instanceof ValorVerdad && ((ValorVerdad)opt1).value)
			return opt2;
		//BExp b & True --> b
		if(opt2 instanceof ValorVerdad && ((ValorVerdad)opt2).value)
			return opt1;
		//False & BExp b --> False
		if(opt1 instanceof ValorVerdad && !((ValorVerdad)opt1).value)
			return new ValorVerdad(false);
		//BExp b & False --> False
		if(opt2 instanceof ValorVerdad && !((ValorVerdad)opt2).value)
			return new ValorVerdad(false);
		
		return new Conjuncion(opt1, opt2);
	}
	@Override public String toString() {
		return "Conjuncion("+ left +", "+ right +")";
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
		Conjuncion other = (Conjuncion)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	/*public static Conjuncion generate(Random random, int min, int max) {
		Expresion left; Expresion right; 
		left = Expresion.generate(random, min-1, max-1);
		right = Expresion.generate(random, min-1, max-1);
		return new Conjuncion(left, right);
	}*/
	
	@Override
	public Object check(ChequearEstado checkstate) {
		if ((left.check(checkstate).equals("boolean")) & (right.check(checkstate).equals("boolean"))){
			return new String("boolean");
		}else {
			Errores.exceptionList.add(new Errores("Conjuncion \"" + this.toString() + "\" tipos no booleanos."));
		}
		return checkstate;
	}
}
