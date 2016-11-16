/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;
import behaviour.CompilationContextIL;

/**
 * Representacion de las asignaciones dentro de las expresiones.
 * @author Grupo_9
 * @version 0.0.1
 * @date 16 nov. 2016
 */
public class AsignacionExpresion extends Expresion {
	public final Sentencia assigment;

	/**
	 * Constructor de la clase.
	 * @param assigment
	 */
	public AsignacionExpresion(Sentencia assigment) {
		this.assigment = assigment;
	}

	@Override public String unparse() {
		return assigment.unparse();
	}

	@Override public String toString() {
		return "AssigExp("+ assigment +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.assigment == null ? 0 : this.assigment.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		AsignacionExpresion other = (AsignacionExpresion)obj;
		return (this.assigment == null ? other.assigment == null : this.assigment.equals(other.assigment));
	}
	
	@Override public Object check(ChequearEstado checkstate){	
		checkstate = assigment.check(checkstate);
		Asignacion asignacion = (Asignacion)assigment;
		String id = asignacion.id;
		Par par = checkstate.devolverValor(id); 
		
		return par.getTipo();
	}	
	
	@Override public Set<String> freeVariables(Set<String> vars) {
		Asignacion a = (Asignacion) this.assigment;
		vars = a.freeVariables(vars);
		return vars;
	}
	@Override public int maxStackIL() {
		Asignacion a = (Asignacion) this.assigment;
		return a.expression.maxStackIL();
	}
	
	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		Asignacion a = (Asignacion) this.assigment;
		ctx= a.compileIL(ctx);
		Integer index = ctx.variables.indexOf(a.id);
		ctx.codeIL.append("ldloc " + index + " // "+a.id+"\n");
		return ctx;
	}
	
	@Override public Expresion optimization(Estado state){
		Asignacion ret = (Asignacion) assigment.optimization(state);
		return new AsignacionExpresion(ret);
	}
}
