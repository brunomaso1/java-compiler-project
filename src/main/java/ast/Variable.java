package ast;

import java.util.*;

import behaviour.*;

import java.io.*;

/**
 * Representacion de las variables.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Variable extends ExpresionAritmetica {
	public final String id;

	public Variable(String id) {
		this.id = id;
	}

	@Override public String unparse() {
		return id;
	}

	@Override public Double evaluate(State state) {
		return state.get(id);
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars.add(id); return vars;
	}

	@Override public int maxStackIL() {
		return 1;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		if(!ctx.variables.contains(id)){
			ctx.variables.add(id);
		}	
		Integer index = ctx.variables.indexOf(id);
		ctx.codeIL.append("ldloc " +  index + "\n");
		return ctx;
	}
	
	@Override public ExpresionAritmetica optimization(State state){		
		if(state.get(id) != null)
			return new Numeral(state.get(id));
		return this;
	}

	@Override public String toString() {
		return "Variable("+ id +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Variable other = (Variable)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id));
	}

	public static Variable generate(Random random, int min, int max) {
		String id; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		return new Variable(id);
	}
}
