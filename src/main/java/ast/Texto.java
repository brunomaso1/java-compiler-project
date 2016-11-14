package ast;

import java.util.*;

import behaviour.CompilationContextIL;

public class Texto extends Expresion{
	public final String str;

	public Texto(String str) {
		this.str = str;
	}

	@Override public String unparse() {
		return str.toString();
	}

	@Override public String toString() {
		return "String("+ str +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.str == null ? 0 : this.str.hashCode());
		return result;
	}

	/* ESTA CLASE PUEDE TENER PROBLEMAS */
	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Texto other = (Texto)obj;
		return (this.str == null ? other.str == null : this.str.equals(other.str));
	}
	
	@Override
	public Object check(ChequearEstado checkstate) {
		return new String("texto");
	}
	
	@Override
	public Set<String> freeVariables(Set<String> vars) {
		return vars;
	}
	
	@Override
	public CompilationContextIL compileIL(CompilationContextIL ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expresion optimization(Estado state) {
		return this;
	}	
	@Override
	public int maxStackIL() {
		return 1;
	}
	
}
