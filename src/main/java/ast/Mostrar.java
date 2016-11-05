package ast;

import java.util.*;

import behaviour.CompilationContextIL;
import behaviour.State;

/** Función Imprimir.
*/
public class Mostrar extends Sentencia {
	public final ExpresionAritmetica exp;

	public Mostrar(ExpresionAritmetica exp) {
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
		Mostrar other = (Mostrar)obj;
		return (this.exp == null ? other.exp == null : this.exp.equals(other.exp));
	}

	public static Mostrar generate(Random random, int min, int max) {
		ExpresionAritmetica exp;
		exp = ExpresionAritmetica.generate(random, min-1, max-1);
		return new Mostrar(exp);		
	}
	
	@Override public State evaluate(State state){
		Object aState = this.exp.evaluate(state);
		System.out.println(aState.toString());
		return state;	
	}

	@Override
	public Set<String> freeVariables(Set<String> vars) {
		// TODO Auto-generated method stub
		return null;
	}

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
	public Sentencia optimization(State state) {
		// TODO Auto-generated method stub
		return null;
	}	
}
