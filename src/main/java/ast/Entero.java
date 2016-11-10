package ast;

import java.util.*;

import behaviour.CompilationContextIL;

/** Representación de constantes numéricas o numerales.
*/
public class Entero extends Expresion {
	public final Integer number;

	public Entero(Integer number) {
		this.number = number;
	}

	@Override public String unparse() {
		return number.toString();
	}

	@Override public String toString() {
		return "Numeral("+ number +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.number == null ? 0 : this.number.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Numeral other = (Numeral)obj;
		return (this.number == null ? other.number == null : this.number.equals(other.number));
	}

	/*public static Numeral generate(Random random, int min, int max) {
		Integer number; 
		number = Math.round(random.nextInt() * 1000) / 100;
		return null;
	}*/

	/*@Override
	public Object evaluate(Estado state) {
		return new Integer(number);
	}*/
	
	@Override
	public Object check(ChequearEstado checkstate) {
		return new String("entero");
	}
	
	@Override
	public Set<String> freeVariables(Set<String> vars) {
		// TODO Auto-generated method stub
		return null;
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
	public int maxStackIL() {
		// TODO Auto-generated method stub
		return 0;
	}
}
