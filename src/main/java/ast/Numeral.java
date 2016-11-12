package ast;

import java.util.*;

import behaviour.*;

import java.io.*;

/**
 * Representacion de los numeros.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Numeral extends Expresion {
	public final Double number;

	public Numeral(Double number) {
		this.number = number;
	}

	@Override public String unparse() {
		return number.toString();
	}

	/*@Override public Double evaluate(Estado state) {
		return number;
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		return vars;
	}

	@Override public int maxStackIL() {
		return 1;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx.codeIL.append("ldc.i4 " + this.number + "\n");
		return ctx;
	}
	
	@Override public Expresion optimization(Estado state){
		return this;
	}
	
	@Override public String toString() {
		return "Numeral("+ number +")";
	}
	
	@Override public Object check(ChequearEstado checkstate) {
		return new String("entero");
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
		Double number; 
		number = Math.round(random.nextDouble() * 1000) / 100.0;
		return new Numeral(number);
	}*/
	
}
