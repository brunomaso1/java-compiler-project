package ast;

import java.util.Random;
import java.util.Set;

import behaviour.CompilationContextIL;

public class ObtenerLista extends Expresion {
	public final String id;
	public final Expresion posicion;

	public ObtenerLista(String id, Expresion posicion) {
		this.id = id;
		this.posicion = posicion;
	}

	@Override public String unparse() {
		return "obtener posicion "+posicion.unparse()+" "+id;
	}

	/*@Override public Object evaluate(Estado state) {
		return null;//state.get(id);
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars.add(id); return vars;
	}

	@Override public int maxStackIL() {
		return posicion.maxStackIL();
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
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
	
	@Override public Expresion optimization(Estado state){		
		//if(state.get(id) != null)
		////	return new Numeral((Double)state.get(id));
		//return this;
		return null;
	}

	@Override public String toString() {
		return "Obtener Posicion "+posicion.unparse()+" "+id;
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

	/*public static ObtenerLista generate(Random random, int min, int max) {
		//String id; 
		//id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		return null;//new Variable(id);
	}*/
	
	@Override public Object check(ChequearEstado checkstate){
		Par par = checkstate.devolverValor(id);
		if (par == null){
			Errores.exceptionList.add(new Errores("ObtenerLista lista \"" + id + "\" no definida"));
		}
		else{
			if (posicion.check(checkstate).equals("entero")){
				String aux = par.getTipo();
				return aux.replace("lista","");
			}else{
				Errores.exceptionList.add(new Errores("ObtenerLista Posicion \"" + posicion.toString() + "\" no numerica."));
			}
		}
		return checkstate;
	}
}
