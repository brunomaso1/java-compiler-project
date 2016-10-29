package ast;

import java.util.*;

/** Función Defined variable.
*/
public class Defined extends Exp {
	public final String var;

	public Defined(String var) {
		this.var = var;
	}

	@Override public String unparse() {
		return "print "+ var+" }";
	}

	@Override public String toString() {
		return "Print("+ var +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.var == null ? 0 : this.var.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Defined other = (Defined)obj;
		return (this.var == null ? other.var == null : this.var.equals(other.var));
	}

	public static Defined generate(Random random, int min, int max) {
		String var;
		var = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
				//String.generate(random, min-1, max-1);
		return new Defined(var);		
	}
	
	@Override public Object evaluate(State state){
				
		Object valor = state.devolverValor(this.var);
		if (valor==null) {
			return false;
			
		}
		return true;
				//this.var.evaluate(state);
	//	System.out.println(aState.toString());
	//	return state;	
	}	
}
