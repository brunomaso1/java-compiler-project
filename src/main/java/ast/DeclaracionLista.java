package ast;

import java.util.Random;
import java.util.Set;

import behaviour.CompilationContextIL;

public class DeclaracionLista extends Sentencia {
	public final String id;
	public final Tipo tipo;
	public final Expresion cantidad;

	public DeclaracionLista(String id, Tipo tipo, Expresion cantidad) {
		this.id = id;
		this.tipo = tipo;
		this.cantidad = cantidad;
	}

	@Override public String unparse() {
		return "lista "+tipo.toString()+" "+id+"["+cantidad+"]";
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars.add(id); return vars;
	}

	@Override public int maxStackIL() {
		return cantidad.maxStackIL();
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		if(ctx.parametros.contains(id)){
			Integer index = ctx.parametros.indexOf(id);
			ctx.codeIL.append("ldarg " +  index + "\n");
			return ctx;
		}	
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
		Expresion optCant = cantidad.optimization(state);
		if (optCant instanceof Numeral){
			if (((Numeral)optCant).number > 0){
				Double aux = ((Numeral)optCant).number;
				int aux2 = Integer.parseInt(aux.toString());
				Object[] objetos = new Object[aux2];
				ParLista parLista = new ParLista(objetos,tipo);				
				state.setLista(id, parLista);
			}else{
				Errores.exceptionList.add(new Errores("Declaracion Lista cantidad \"" + optCant.toString() + "\" debe ser mayor que 0."));			
			}
		}else{
			Errores.exceptionList.add(new Errores("Declaracion Lista cantidad \"" + optCant.toString() + "\" deber ser numerica."));
		}
		return new DeclaracionLista(id, tipo, optCant);
	}

	@Override public String toString() {
		return "Lista "+tipo.toString()+" "+id+"["+cantidad+"]";
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
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		if (!(cantidad.check(checkstate).equals("entero"))) {
			Errores.exceptionList.add(new Errores("Error. La cantidad debe ser entero."));
			return checkstate;
		}
		if (checkstate.devolverValor(id)==null){
			checkstate.agregar(id, new Par(tipo.toString(), true, true));
		}
		else {
			Errores.exceptionList.add(new Errores("Error en la declaracion \"" + id + "\" lista ya declarada."));
		}
		return checkstate;
	}
}
