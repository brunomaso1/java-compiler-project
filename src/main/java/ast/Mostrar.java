package ast;

import java.util.*;

import behaviour.CompilationContextIL;

/** Función Imprimir.
*/
public class Mostrar extends Sentencia {
	public final Expresion exp;

	public Mostrar(Expresion exp) {
		this.exp = exp;
	}

	@Override public String unparse() {
		return "mostrar "+ exp.unparse()+" }";
	}

	@Override public String toString() {
		return "Mostrar("+ exp +")";
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
		Expresion exp;
		exp = Expresion.generate(random, min-1, max-1);
		return new Mostrar(exp);		
	}
	
	@Override public Estado evaluate(Estado state){
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
	public Sentencia optimization(Estado state) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		return null;
	}
}
