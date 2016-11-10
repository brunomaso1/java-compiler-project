package ast;

import java.util.*;

import behaviour.CompilationContextIL;

/** Función Largo.
*/
public class Largo extends Expresion {
	public final Expresion exp;

	public Largo(Expresion exp) {
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
		Largo other = (Largo)obj;
		return (this.exp == null ? other.exp == null : this.exp.equals(other.exp));
	}

	/*public static Largo generate(Random random, int min, int max) {
		Expresion exp;
		exp = Expresion.generate(random, min-1, max-1);
		return new Largo(exp);
	}*/
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		return null;
	}
			
	
	/*@Override public Object evaluate(Estado state){
		Object aState = this.exp.evaluate(state);
		Double valor = aState.toString().length() * 1.0;
		//System.out.println(aState.toString().length()); 
		return valor;	
	}*/
	
	@Override
	public int maxStackIL() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CompilationContextIL compileIL(CompilationContextIL ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expresion optimization(Estado state) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	@Override
	public Set<String> freeVariables(Set<String> vars) {
		// TODO Auto-generated method stub
		return null;
	}
}
