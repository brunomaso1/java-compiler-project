package ast;

import java.util.Random;
import java.util.Set;

import behaviour.CompilationContextIL;

public class Declaracion extends Sentencia{
	public final String variable;
	public final Tipo tipo;

	public Declaracion(String variable, Tipo tipo) {
		this.variable = variable;
		this.tipo = tipo;
	}

	@Override public String unparse() {
		return "declaracion " + tipo + " variable " + variable;
	}

	@Override public String toString() {
		return "Declaracion("+ tipo +", "+ variable + ")";
	}

	@Override public int hashCode() {
		int result = 1;
		return result;
	}

	/*public static Declaracion generate(Random random, int min, int max) {
		return null;
	}*/
		
	/*@Override public Estado evaluate(Estado state){
		state.set(variable, null);
		return state;
	}*/		
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		if (checkstate.devolverValor(variable)==null){
			checkstate.agregar(variable, new Par(tipo.toString(), false, false));
		}
		else {
			Errores.exceptionList.add(new Errores("Error en la declaracion \"" + variable + "\" variable ya declarada."));
		}
		return checkstate;		
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		DeclaracionIniciar other = (DeclaracionIniciar)obj;
		return (this.variable == null ? other.id == null : this.variable.equals(other.id))
			&& (this.tipo == null ? other.tipo == null : this.tipo.equals(other.tipo));
	}
	
	@Override public Set<String> freeVariables(Set<String> vars) {
		return vars;
	}
	
	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx.variables.add(variable);
		ctx.variablesTipo.add(new ParComp(variable,tipo.toString().toLowerCase()));
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state){				
		state.set(variable, null);
		return this;
	}
	
	@Override public int maxStackIL() {
		return 1;
	}
}
