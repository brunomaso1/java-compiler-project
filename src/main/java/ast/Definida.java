package ast;

import java.util.*;

import behaviour.CompilationContextIL;

/** Función Defined variable.
*/
public class Definida extends Expresion {
	public final String var;

	public Definida(String var) {
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
		Definida other = (Definida)obj;
		return (this.var == null ? other.var == null : this.var.equals(other.var));
	}

	/*public static Definida generate(Random random, int min, int max) {
		String var;
		var = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
				//String.generate(random, min-1, max-1);
		return new Definida(var);		
	}*/
	
	/*@Override public Object evaluate(Estado state){
				
		Object valor = state.get(this.var);
		if (valor==null) {
			return false;
			
		}
		return true;
				//this.var.evaluate(state);
	//	System.out.println(aState.toString());
	//	return state;	
	}*/	
	
	
	@Override public Object check(ChequearEstado checkstate){
		
		return new String("bool");
		
				//this.var.evaluate(state);
	//	System.out.println(aState.toString());
	//	return state;	
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
