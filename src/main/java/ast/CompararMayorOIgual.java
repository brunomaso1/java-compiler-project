package ast;

import java.util.*;

/*  HAY QUE VERLO A ESTE *********
*/
public class CompararMayorOIgual extends Exp {
	public final Exp left;
	public final Exp right;

	public CompararMayorOIgual(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" <= "+ right.unparse() +")";
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
		Exp left; Exp right; 
		left = Exp.generate(random, min-1, max-1);
		right = Exp.generate(random, min-1, max-1);
		return new CompararMayorOIgual(left, right);
	}

	@Override
	public Object evaluate(State state) {
		if ((left.evaluate(state) instanceof Double) & (right.evaluate(state) instanceof Double))
			return new Boolean(((Double)left.evaluate(state) <= (Double)right.evaluate(state))?true:false);
		else {
			System.out.print("Estas comparando mal. Nuemro1 -> " + left.evaluate(state) + " Numero2 -> " + right.evaluate(state));
			return null;
		}
	}
}
