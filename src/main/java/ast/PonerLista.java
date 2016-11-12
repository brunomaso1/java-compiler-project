package ast;

import java.util.Random;
import java.util.Set;

import behaviour.CompilationContextIL;

public class PonerLista extends Sentencia {
	public final String id;
	public final Expresion posicion;
	public final Expresion expresion;

	public PonerLista(String id, Expresion posicion, Expresion expresion) {
		this.id = id;
		this.posicion = posicion;
		this.expresion = expresion;
	}

	@Override public String unparse() {
		return "poner "+expresion.unparse()+" en "+id+" posicion "+posicion.unparse()+".";
	}

	/*@Override public Estado evaluate(Estado tipostate) {
		return null;//state.get(id);
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars.add(id); return vars;
	}

	@Override public int maxStackIL() {
		return expresion.maxStackIL();
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx.var
		
		if(ctx.parametros.contains(id)){
			Integer index = ctx.parametros.indexOf(id);
			ctx.codeIL.append("ldarg " +  index + "\n");
			return ctx;
		}	
		if(!ctx.variables.contains(id)){
			ctx.variables.add(id);
		}	
		Integer index = ctx.variables.indexOf(id);
		ctx.codeIL.append("ldloc " +  index + "\n");
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state){		
		//if(state.get(id) != null)
		//	return new Numeral((Double)state.get(id));
		return null;//this;
	}

	@Override public String toString() {
		return "Poner "+expresion.unparse()+" en "+id+" posicion "+posicion.unparse()+".";
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

	/*public static PonerLista generate(Random random, int min, int max) {
		//String id; 
		//id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		return null;//ew Variable(id);
	}*/
	
	@Override public ChequearEstado check(ChequearEstado checkstate){		
		
		// Errores.exceptionList.add(new Errores("Comparacion Menor \"" + this.toString() + "\" tipos no n�mericos."));

		
		return checkstate;//checkstate.devolverValor(id).getTipo();
	}
}
