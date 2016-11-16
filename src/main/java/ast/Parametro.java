/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.*;


/**
 * Representacion de las variables.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Parametro extends Expresion {
	public final String id;
	public final Tipo tipo;

	public Parametro(String id, Tipo tipo) {
		this.id = id;
		this.tipo = tipo;
	}

	@Override public String unparse() {
		return tipo+" "+id;
	}

	/*@Override public Object evaluate(Estado state) {
		return state.get(id);
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		return vars;
	}

	@Override public int maxStackIL() {
		return 1;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		Integer index = ctx.parametros.indexOf(id);
		ctx.codeIL.append("ldarg " +  index + "\n");
		return ctx;
	}
	
	@Override public Expresion optimization(Estado state){		
		return this;
	}

	@Override public String toString() {
		return "Parametro("+ tipo+" "+id +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Parametro other = (Parametro)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id));
	}

	/*public static Variable generate(Random random, int min, int max) {
		String id; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		return new Variable(id);
	}*/
	
	@Override public Object check(ChequearEstado checkstate){
		return checkstate.devolverValor(id).getTipo();
	}
}
