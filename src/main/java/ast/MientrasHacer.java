package ast;

import java.util.*;

import behaviour.*;

import java.io.*;


public class MientrasHacer extends Sentencia {
	public final Expresion condition;
	public final Sentencia body;

	public MientrasHacer(Expresion condition, Sentencia body) {
		this.condition = condition;
		this.body = body;
	}

	@Override public String unparse() {
		return "Mientras "+ condition.unparse() +" hacer { "+ body.unparse() +" }";
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars = condition.freeVariables(vars); return body.freeVariables(vars);
	}

	@Override public int maxStackIL() {
		return Math.max(condition.maxStackIL(), body.maxStackIL());
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		
		//CS[while b do cs] = br L2 L1: :cs L2: CB[[b]]:brtrue L1
		String etiqueta = ctx.newLabel();
		String etiqueta2 = ctx.newLabel();
		
		ctx.codeIL.append("br.s " +etiqueta2+ "\n");
		ctx.codeIL.append(etiqueta+ ": " +"\n");
		ctx = body.compileIL(ctx);
		ctx.codeIL.append(etiqueta2+ ": " +"\n");
		ctx = condition.compileIL(ctx);
		ctx.codeIL.append("brtrue.s "+ etiqueta + "\n");
		ctx.codeIL.append("nop "+ "\n");
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state) {
		Expresion con = condition.optimization(state);
		Estado copiaEstadoAntes = state;
		Sentencia bodyWhile = body.optimization(state);
		Estado copiaBodyWhile = state;
		
		if(con instanceof ValorVerdad){
			if(!((ValorVerdad)con).value){
				return new Secuencia(new Sentencia[0]);
			}else{
				ArrayList<String> clavesBodyWhile = copiaEstadoAntes.devolverClaves();
				Estado resultado = new Estado();
				String var;
				for (int i = 0; i< clavesBodyWhile.size();i++) {
					var = clavesBodyWhile.get(i);
					if (copiaEstadoAntes.get(var) == copiaBodyWhile.get(var)) {
						resultado.set(var, copiaEstadoAntes.get(var));
					}
				}
				state = resultado;
				return new MientrasHacer(con, bodyWhile);
			}	
		}else{
			ArrayList<String> clavesBodyWhile = copiaEstadoAntes.devolverClaves();
			Estado resultado = new Estado();
			String var;
			for (int i = 0; i< clavesBodyWhile.size();i++) {
				var = clavesBodyWhile.get(i);
				if (copiaEstadoAntes.get(var) == copiaBodyWhile.get(var)) {
					resultado.set(var, copiaEstadoAntes.get(var));
				}
			}
			state = resultado;
			return new MientrasHacer(con, bodyWhile);
		}
	}

	@Override public String toString() {
		return "MientrasHacer("+ condition +", "+ body +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.body == null ? 0 : this.body.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MientrasHacer other = (MientrasHacer)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
			&& (this.body == null ? other.body == null : this.body.equals(other.body));
	}
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		if (condition.check(checkstate).equals("boolean")){
			return body.check(checkstate);
		}else{
			Errores.exceptionList.add(new Errores("Mientras \"" + condition.toString() + "\" condicion no booleana."));	
		}
		return checkstate;
	}
}
