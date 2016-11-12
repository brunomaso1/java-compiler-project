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
		return "declaracionIniciar " + tipo + " variable " + expresion;
	}

	@Override public String toString() {
		return "DeclaracionIniciar("+ tipo +", "+ expresion + ")";
	}

	@Override public int hashCode() {
		int result = 1;
		return result;
	}

	/*public static DeclaracionIniciar generate(Random random, int min, int max) {
		return null;
	}*/
		
	/*@Override public Estado evaluate(Estado state){
		state.set(id, expresion.evaluate(state));
		return state;
	}*/		
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		if(tipo == Tipo.ENTERO && expresion.check(checkstate).toString().equals("entero")){
			checkstate.agregar(id, new Par("entero", true));
		} else if(tipo == Tipo.BOOLEAN && expresion.check(checkstate).toString().equals("boolean")){
			checkstate.agregar(id, new Par("boolean", true));
			} else if(tipo == Tipo.NUMERAL && expresion.check(checkstate).toString().equals("numero")){
				checkstate.agregar(id, new Par("numero", true));
				} else if(tipo == Tipo.TEXTO && expresion.check(checkstate).toString().equals("texto")){
					checkstate.agregar(id, new Par("texto", true));	
				}
		return checkstate;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		DeclaracionIniciar other = (DeclaracionIniciar)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id))
			&& (this.expresion == null ? other.expresion == null : this.expresion.equals(other.expresion))
			&& (this.tipo == null ? other.tipo == null : this.tipo.equals(other.tipo));
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
