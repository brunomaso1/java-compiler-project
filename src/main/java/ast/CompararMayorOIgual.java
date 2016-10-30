package ast;

import java.util.*;
import behaviour.*;

/**
 * Representacion de las comparaciones por mayor o igual.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class CompararMayorOIgual extends BExp {
	public final AExp left;
	public final AExp right;

	public CompararMayorOIgual(AExp left, AExp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" >= "+ right.unparse() +")";
	}

	@Override public Boolean evaluate(State state) {
		return left.evaluate(state) >= right.evaluate(state);
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		return right.freeVariables(left.freeVariables(vars));
	}

	@Override public int maxStackIL() {
		return Math.max(left.maxStackIL(), right.maxStackIL() + 1);
	}

	/**
	 * FALTA ARREGLAR.
	 * Falta la comparacion por igual... Habria que especificar como el contrario del menor.
	 */
	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = left.compileIL(ctx);
		ctx = right.compileIL(ctx);
		ctx.codeIL.append("clt \n");
		
		return ctx;
	}

	@Override public String toString() {
		return "CompararMayorOIgual("+ left +", "+ right +")";
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
		CompararMayorOIgual other = (CompararMayorOIgual)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static CompararMayorOIgual generate(Random random, int min, int max) {
		AExp left; AExp right; 
		left = AExp.generate(random, min-1, max-1);
		right = AExp.generate(random, min-1, max-1);
		return new CompararMayorOIgual(left, right);
	}
}
