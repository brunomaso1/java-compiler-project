package ast;

import java.util.*;
import behaviour.*;
import java.io.*;

/** Representación de valores de verdad (cierto o falso).
*/
public class TruthValue extends BExp {
	public final Boolean value;

	public TruthValue(Boolean value) {
		this.value = value;
	}

	@Override public String unparse() {
		return value ? "true" : "false";
	}

	@Override public Boolean evaluate(State state) {
		return value;
	}

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

	@Override public String toString() {
		return "TruthValue("+ value +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.value == null ? 0 : this.value.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TruthValue other = (TruthValue)obj;
		return (this.value == null ? other.value == null : this.value.equals(other.value));
	}

	public static TruthValue generate(Random random, int min, int max) {
		Boolean value; 
		value = random.nextBoolean();
		return new TruthValue(value);
	}
}
