package ast;

import java.util.*;

/** Función Largo.
*/
public class Length extends Exp {
	public final Exp exp;

	public Length(Exp exp) {
		this.exp = exp;
	}

	@Override public String unparse() {
		return "print "+ exp.unparse()+" }";
	}

	@Override public String toString() {
		return "Print("+ exp +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.exp == null ? 0 : this.exp.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Length other = (Length)obj;
		return (this.exp == null ? other.exp == null : this.exp.equals(other.exp));
	}

	public static Length generate(Random random, int min, int max) {
		Exp exp;
		exp = Exp.generate(random, min-1, max-1);
		return new Length(exp);
	}
	
	@Override public Object evaluate(State state){
		Object aState = this.exp.evaluate(state);
		Double valor = aState.toString().length() * 1.0;
		//System.out.println(aState.toString().length()); 
		return valor;	
	}	
}
