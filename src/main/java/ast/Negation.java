package ast;

import java.util.*;

/** Representación de las negaciones de expresiones booleanas.
*/
public class Negation extends Exp {
	public final Exp condition;

	public Negation(Exp condition) {
		this.condition = condition;
	}

	@Override public String unparse() {
		return "(!"+ condition.unparse() +")";
	}

	@Override public String toString() {
		return "Negation("+ condition +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Negation other = (Negation)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition));
	}

	public static Negation generate(Random random, int min, int max) {
		Exp condition; 
		condition = Exp.generate(random, min-1, max-1);
		return new Negation(condition);
	}
	
	@Override
	public Object evaluate(State state) {
		if (condition.evaluate(state) instanceof Boolean)
			return !((Boolean)condition.evaluate(state));
		else {
			System.out.print("Estas haciendo una negacion mal. Nuemro1 -> " + condition.evaluate(state));
			return null;
		}
	}
}
