package ast;

import java.util.*;
import behaviour.*;

/**
 * Representacion de las divisiones.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Division extends ExpresionAritmetica {
	public final ExpresionAritmetica left;
	public final ExpresionAritmetica right;

	public Division(ExpresionAritmetica left, ExpresionAritmetica right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" / "+ right.unparse() +")";
	}

	@Override public Double evaluate(State state) {
		return left.evaluate(state) / right.evaluate(state);
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
		ctx.codeIL.append("div \n");
		return ctx;
	}

	@Override public String toString() {
		return "Division("+ left +", "+ right +")";
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
		Division other = (Division)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static Division generate(Random random, int min, int max) {
		ExpresionAritmetica left; ExpresionAritmetica right; 
		left = ExpresionAritmetica.generate(random, min-1, max-1);
		right = ExpresionAritmetica.generate(random, min-1, max-1);
		return new Division(left, right);
	}
	

}
