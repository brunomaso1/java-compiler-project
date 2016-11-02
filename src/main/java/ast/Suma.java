package ast;

import java.util.*;

import behaviour.*;

/**
 * Representacion de las sumas.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Suma extends ExpresionAritmetica {
	public final ExpresionAritmetica left;
	public final ExpresionAritmetica right;

	public Suma(ExpresionAritmetica left, ExpresionAritmetica right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" + "+ right.unparse() +")";
	}

	@Override public Double evaluate(State state) {
		return left.evaluate(state) + right.evaluate(state);
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
		ctx.codeIL.append("add \n");
		return ctx;
	}
	
	@Override public ExpresionAritmetica optimization(State state){
		ExpresionAritmetica opt1 = left.optimization(state);
		ExpresionAritmetica opt2 = right.optimization(state);
		
		if(opt1 instanceof Numeral && opt2 instanceof Numeral)
		{
			//El resultado es lo mismo que devolver un numeral de la suma de ambos operandos, pero 
			//de esta forma estamos evitando hacer LA OPERACION DE LA SUMA.
			//0 + a = a
			if(((Numeral)opt1).number == 0)
				return opt2;
			//a + 0 = a
			if(((Numeral)opt2).number == 0)
				return opt1;
			//Si no entra en los otros casos, devolvemos un Numeral(opt1 + opt2)
			return new Numeral( ((Numeral)opt1).number + ((Numeral)opt2).number );
		}else{
			return new Suma(opt1, opt2);
		}
	}

	@Override public String toString() {
		return "Suma("+ left +", "+ right +")";
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
		Suma other = (Suma)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static Suma generate(Random random, int min, int max) {
		ExpresionAritmetica left; ExpresionAritmetica right; 
		left = ExpresionAritmetica.generate(random, min-1, max-1);
		right = ExpresionAritmetica.generate(random, min-1, max-1);
		return new Suma(left, right);
	}
	
}
