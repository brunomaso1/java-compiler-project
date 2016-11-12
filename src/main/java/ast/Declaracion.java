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

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override public Set<String> freeVariables(Set<String> vars) {
		vars.add(variable); 
		return vars;
	}
	
	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		//ctx= expression.compileIL(ctx);

		//Integer index = ctx.variables.indexOf(id);
		//ctx.codeIL.append("stloc " + index + "\n");
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state){				
		//Expresion expresion = expression.optimization(state);
	//	if(expresion instanceof Numeral){
	//		state.set(id, ((Numeral)expresion).number);
	//	}
		return null; //new Asignacion(id, expresion);
	}
	
	@Override public int maxStackIL() {
		return 1;
	}
}
