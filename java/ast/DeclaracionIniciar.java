package ast;

import java.util.Random;
import java.util.Set;

import behaviour.CompilationContextIL;

public class DeclaracionIniciar extends Sentencia{

	public final Expresion expresion;
	public final Tipo tipo;
	public final String id;

	public DeclaracionIniciar(Expresion expresion, Tipo tipo, String id) {
		this.expresion = expresion;
		this.tipo = tipo;
		this.id = id;
		
	}

	@Override public String unparse() {
		return "declaracion " + tipo + " variable " + expresion;
	}

	@Override public String toString() {
		return "Declaration("+ tipo +", "+ expresion + ")";
	}

	@Override public int hashCode() {
		int result = 1;
		return result;
	}

	public static DeclaracionIniciar generate(Random random, int min, int max) {
		return null;
	}
		
	@Override public Estado evaluate(Estado state){
		state.set(id, expresion.evaluate(state));
		return state;
	}		
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		if(tipo == Tipo.ENTERO && expresion.check(checkstate).toString().equals("integer")){
			checkstate.agregar(id, new Par("integer", true));
		} else if(tipo == Tipo.VERDAD && expresion.check(checkstate).toString().equals("bool")){
			checkstate.agregar(id, new Par("bool", true));
			} else if(tipo == Tipo.NUMERAL && expresion.check(checkstate).toString().equals("number")){
				checkstate.agregar(id, new Par("number", true));
				} else if(tipo == Tipo.TEXTO && expresion.check(checkstate).toString().equals("string")){
					checkstate.agregar(id, new Par("string", true));	
				}
		return checkstate;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override public Set<String> freeVariables(Set<String> vars) {
		vars = expresion.freeVariables(vars); 
		vars.add(id);
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
		return Math.max(expresion.maxStackIL(),1);
	}
	
}
