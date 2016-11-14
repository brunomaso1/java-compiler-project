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
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		if (checkstate.devolverValor(id)!=null){
			Errores.exceptionList.add(new Errores("DeclaracionIniciar \"" + id + "\" variable ya declarada."));
			return checkstate;
		}
		if(tipo == Tipo.ENTERO && expresion.check(checkstate).toString().equals("entero")){
			checkstate.agregar(id, new Par("entero", true,false));
		}else if(tipo == Tipo.BOOLEAN && expresion.check(checkstate).toString().equals("boolean")){
			checkstate.agregar(id, new Par("boolean", true,false));
		} else if(tipo == Tipo.TEXTO && expresion.check(checkstate).toString().equals("texto")){
			checkstate.agregar(id, new Par("texto", true, false));	
		}else{
			Errores.exceptionList.add(new Errores("DeclaracionIniciar tipo \"" + tipo + "\""+ " expresion \"" + expresion.toString() + "\" no coinciden."));
		}
		return checkstate;
	}

	@Override
	public boolean equals(Object obj) {
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
		ctx.variables.add(id);
		ctx.variablesTipo.add(new ParComp(id,tipo.toString().toLowerCase()));
		
		ctx= expresion.compileIL(ctx);

		Integer index = ctx.variables.indexOf(id);
		ctx.codeIL.append("stloc " + index + "\n");
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state){				
		Expresion expOpt = expresion.optimization(state);
		if(expOpt instanceof Numeral){
			state.set(id, ((Numeral)expOpt).number);
		}if(expOpt instanceof Texto){
			state.set(id, ((Texto)expOpt).str);
		}if(expOpt instanceof ValorVerdad){
			state.set(id, ((ValorVerdad)expOpt).value);
		}
		return new DeclaracionIniciar(expOpt, tipo, id);
	}
	
	@Override public int maxStackIL() {
		return Math.max(expresion.maxStackIL(),1);
	}
	
}
