package ast;

import java.util.*;

import behaviour.*;

/**
 * Representacion de las comparaciones por menor o igual.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class CompararMenorOIgual extends Expresion {
	public final Expresion left;
	public final Expresion right;

	public CompararMenorOIgual(Expresion left, Expresion right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" <= "+ right.unparse() +")";
	}

	/*@Override public Boolean evaluate(Estado state) {
		if ((left.evaluate(state) instanceof Double) & (right.evaluate(state) instanceof Double))
			return new Boolean(((Double)left.evaluate(state) <= (Double)right.evaluate(state))?true:false);
		else {
			System.out.print("Estas comparando mal. Numero1 -> " + left.evaluate(state) + " Numero2 -> " + right.evaluate(state));
			return null;
		}
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		return right.freeVariables(left.freeVariables(vars));
	}

	@Override public int maxStackIL() {
		return Math.max(left.maxStackIL(), right.maxStackIL() + 1);
	}

	/**
	 * FALTA ARREGLAR.
	 * Esta haciendo unicamente la comparacion por menor, habria que implementar la comparacion
	 * por menor o igual como la negacion de la comparacion como mayor.
	 */
	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = left.compileIL(ctx);
		ctx = right.compileIL(ctx);
		ctx.codeIL.append("cgt " + "\n");
		ctx.codeIL.append("ldc.i4.0 " + "\n");
		ctx.codeIL.append("ceq " + "\n");
		
		return ctx;
	}
	
	@Override public Expresion optimization(Estado state){
		Expresion opt1 = left.optimization(state);
		Expresion opt2 = right.optimization(state);
		
		//NUM a == NUM b
		if(opt1 instanceof Numeral && opt2 instanceof Numeral){
			if( ((Numeral)opt1).number <= ((Numeral)opt2).number)
				return new ValorVerdad(true);
			else
				return new ValorVerdad(false);
		}
		
		//Variable a <= Variable a --> True
		if(opt1 instanceof Variable && opt2 instanceof Variable){
			if( ((Variable)opt1).id.equals(((Variable)opt2).id)) {
				return new ValorVerdad(true);
			}
		}
		
		return new CompararMenorOIgual(opt1, opt2);
	}
	
	@Override public String toString() {
		return "CompararMenorOIgual("+ left +", "+ right +")";
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
		CompararMenorOIgual other = (CompararMenorOIgual)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	/*public static CompararMenorOIgual generate(Random random, int min, int max) {
		Expresion left; Expresion right; 
		left = Expresion.generate(random, min-1, max-1);
		right = Expresion.generate(random, min-1, max-1);
		return new CompararMenorOIgual(left, right);
	}*/
	
	@Override
	public Object check(ChequearEstado checkstate) {
		
		if ((left.check(checkstate).equals("number") & right.check(checkstate).equals("number")))
			return new String("boolean");
		else {
			System.out.print("Estas comparando mal. Numero1 -> " + left.check(checkstate) + " Numero2 -> " + right.check(checkstate));
			return null;
		}
	}
}
