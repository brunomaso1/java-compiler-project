package ast;

import java.util.*;

import behaviour.*;

import java.io.*;

/**
 * Representacion de las multiplicaciones.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Multiplicacion extends ExpresionAritmetica {
	public final ExpresionAritmetica left;
	public final ExpresionAritmetica right;

	public Multiplicacion(ExpresionAritmetica left, ExpresionAritmetica right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" * "+ right.unparse() +")";
	}

	@Override public Double evaluate(State state) {
		return left.evaluate(state) * right.evaluate(state);
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		return right.freeVariables(left.freeVariables(vars));
	}

	@Override public int maxStackIL() {
		return Math.max(left.maxStackIL(), right.maxStackIL() + 1);
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = left.compileIL(ctx);
		ctx = right.compileIL(ctx);
		ctx.codeIL.append("mul \n");
		return ctx;
	}
	
	@Override public ExpresionAritmetica optimization(State state){
		ExpresionAritmetica opt1 = left.optimization(state);
		ExpresionAritmetica opt2 = right.optimization(state);
		
		if(opt1 instanceof Numeral && opt2 instanceof Numeral)
		{
			//0 * a = 0
			if(((Numeral)opt1).number == 0)
				return opt1;
			//a * 0 = 0
			if(((Numeral)opt2).number == 0)
				return opt2;
			//1 * a = a
			if(((Numeral)opt1).number == 1)
				return opt2;
			//a * 1 = a
			if(((Numeral)opt2).number == 1)
				return opt1;
			//Si no entra en los otros casos, devolvemos un Numeral(opt1 * opt2)
			return new Numeral( ((Numeral)opt1).number * ((Numeral)opt2).number );
		}else{
			return new Multiplicacion(opt1, opt2);
		}
	}
	
	@Override public String toString() {
		return "Multiplicacion("+ left +", "+ right +")";
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
		Multiplicacion other = (Multiplicacion)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static Multiplicacion generate(Random random, int min, int max) {
		ExpresionAritmetica left; ExpresionAritmetica right; 
		left = ExpresionAritmetica.generate(random, min-1, max-1);
		right = ExpresionAritmetica.generate(random, min-1, max-1);
		return new Multiplicacion(left, right);
	}
	

}