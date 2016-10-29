package ast;

import java.util.*;

/** Representación de divisiones.
*/
public class Division extends Exp {
	public final Exp left;
	public final Exp right;

	public Division(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" / "+ right.unparse() +")";
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
		Exp left; Exp right; 
		left = Exp.generate(random, min-1, max-1);
		right = Exp.generate(random, min-1, max-1);
		return new Division(left, right);
	}

	@Override
	public Object evaluate(State state) {
		if ((left.evaluate(state) instanceof Double) & (right.evaluate(state) instanceof Double))
			if (!((Double)right.evaluate(state) == 0))
				return new Double((Double)left.evaluate(state) / (Double)right.evaluate(state));
			else {
				System.out.print("Estas dividiendo entre 0.");
				return null;
			}
		else {
			System.out.print("Estas comparando mal. Nuemro1 -> " + left.evaluate(state) + " Numero2 -> " + right.evaluate(state));
			return null;
		}
	}
}
