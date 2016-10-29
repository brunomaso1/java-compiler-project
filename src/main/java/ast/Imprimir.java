package ast;

import java.util.*;

/** Función Imprimir.
*/
public class Imprimir extends Stmt {
	public final Exp exp;

	public Imprimir(Exp exp) {
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
		Imprimir other = (Imprimir)obj;
		return (this.exp == null ? other.exp == null : this.exp.equals(other.exp));
	}

	public static Imprimir generate(Random random, int min, int max) {
		Exp exp;
		exp = Exp.generate(random, min-1, max-1);
		return new Imprimir(exp);		
	}
	
	@Override public State evaluate(State state){
		Object aState = this.exp.evaluate(state);
		System.out.println(aState.toString());
		return state;	
	}	
}
