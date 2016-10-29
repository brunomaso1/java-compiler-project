package ast;

import java.util.*;

/** Representación de conjunciones booleanas (AND).
*/
public class Conjuncion extends Exp {
	public final Exp left;
	public final Exp right;

	public Conjuncion(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" and "+ right.unparse() +")";
	}

	@Override public String toString() {
		return "Conjunction("+ left +", "+ right +")";
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

	public static Conjuncion generate(Random random, int min, int max) {
		Exp left; Exp right; 
		left = Exp.generate(random, min-1, max-1);
		right = Exp.generate(random, min-1, max-1);
		return new Conjuncion(left, right);
	}

	@Override
	public Object evaluate(State state) {
		if ((left.evaluate(state) instanceof Boolean) & (right.evaluate(state) instanceof Boolean))
			return new Boolean((Boolean)left.evaluate(state) && (Boolean)right.evaluate(state));
		else {
			System.out.print("Estas haciendo una conjuncion mal. Nuemro1 -> " + left.evaluate(state) + " Numero2 -> " + right.evaluate(state));
			return null;
		}
	}
}
