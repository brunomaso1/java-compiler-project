package ast;

import java.util.*;

import behaviour.*;

import java.io.*;

/**
 * Representacion de los bucles.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
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

	/*@Override public Estado evaluate(Estado state) {
		while((Boolean) condition.evaluate(state)){
			state = body.evaluate(state);
		}
		return state;
	}*/	

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
		
		Expresion bExpCondition = condition.optimization(state);
		if(bExpCondition instanceof ValorVerdad && !((ValorVerdad)bExpCondition).value)
			return Sentencia.skip;
		
		Estado newStateVacio = new Estado();
		bExpCondition = condition.optimization(newStateVacio);
		Sentencia stmtBodyOpt = body.optimization(newStateVacio);
		state.estado.clear();//Porque no sabemos cuales constantes se invalidaron dentro del cuerpo del while.
		return new MientrasHacer(bExpCondition, stmtBodyOpt);
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

	/*public static MientrasHacer generate(Random random, int min, int max) {
		Expresion condition; Sentencia body; 
		condition = Expresion.generate(random, min-1, max-1);
		body = Sentencia.generate(random, min-1, max-1);
		return new MientrasHacer(condition, body);
	}*/
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		if (condition.check(checkstate).equals("boolean")){
			return body.check(checkstate);
		}else{
			Errores.exceptionList.add(new Errores("Mientras \"" + condition.toString() + "\" condicion no booleana."));
			
		}
			
		return checkstate;
	}
}
