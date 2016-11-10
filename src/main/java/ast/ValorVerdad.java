package ast;

import java.util.*;
import behaviour.*;



/**
 * Representacion de los valores de verdad.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class ValorVerdad extends Expresion {
	public final Boolean value;

	public ValorVerdad(Boolean value) {
		this.value = value;
	}

	@Override public String unparse() {
		return value ? "true" : "false";
	}

	/*@Override public Boolean evaluate(Estado state) {
		return value;
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		return vars;
	}

	@Override public int maxStackIL() {
		return 1;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		if(value){
			ctx.codeIL.append("ldc.i4.1 \n");
			return ctx;
		}else{
			ctx.codeIL.append("ldc.i4.0 \n");
			return ctx;			
		}
	}
	
	@Override public Expresion optimization(Estado state){
		return this;
	}

	@Override public String toString() {
		return "ValorVerdad("+ value +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.value == null ? 0 : this.value.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ValorVerdad other = (ValorVerdad)obj;
		return (this.value == null ? other.value == null : this.value.equals(other.value));
	}

	/*public static ValorVerdad generate(Random random, int min, int max) {
		Boolean value; 
		value = random.nextBoolean();
		return new ValorVerdad(value);
	}*/
	
	@Override public Object check(ChequearEstado checkstate){
		return new String("verdad");
	}
}
