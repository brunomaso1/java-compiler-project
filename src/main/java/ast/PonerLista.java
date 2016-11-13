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
//		ctx.var
//		
//		if(ctx.parametros.contains(id)){
//			Integer index = ctx.parametros.indexOf(id);
//			ctx.codeIL.append("ldarg " +  index + "\n");
//			return ctx;
//		}	
//		if(!ctx.variables.contains(id)){
//			ctx.variables.add(id);
//		}	
//		Integer index = ctx.variables.indexOf(id);
//		ctx.codeIL.append("ldloc " +  index + "\n");
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state){		
		Expresion posicionOpt = posicion.optimization(state);
		Expresion expresionOpt = expresion.optimization(state);
		
		return new PonerLista(id, posicionOpt, expresionOpt);
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
		Par par = checkstate.devolverValor(id);
		if (par == null){
			Errores.exceptionList.add(new Errores("PonerLista lista \"" + id + "\" no definida"));
		}
		else{
			if (posicion.check(checkstate).equals("entero")){
				String aux = par.getTipo();
				aux = aux.replace("lista","");
				if (expresion.check(checkstate).equals(aux)){
					return checkstate;
				}else{
					Errores.exceptionList.add(new Errores("PonerLista expresion \"" + posicion.toString() + "\" no es del mismo tipo que Lista."));
				}
			}else{
				Errores.exceptionList.add(new Errores("PonerLista Posicion \"" + posicion.toString() + "\" no numerica."));
			}
		}
		return checkstate;
	}
}
