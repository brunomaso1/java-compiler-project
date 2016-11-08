package ast;

import java.util.*;

import behaviour.*;

import java.io.*;

/**
 * Representacion de la condicion logica Si->Entonces.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class SiEntonces extends Sentencia {
	public final Expresion condition;
	public final Sentencia thenBody;

	public SiEntonces(Expresion condition, Sentencia thenBody) {
		this.condition = condition;
		this.thenBody = thenBody;
	}

	@Override public String unparse() {
		return "Si "+ condition.unparse() +" entonces { "+ thenBody.unparse() +" }";
	}

	/**
	 * FALTA ARREGLAR.
	 * Está como si se ejecutara un ifthen.
	 */	
	@Override public Estado evaluate(Estado state) {
		Boolean resCond = (Boolean) condition.evaluate(state);
		if (resCond){
			state = thenBody.evaluate(state);
		}
		return state;
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars = condition.freeVariables(vars); return thenBody.freeVariables(vars);
	}

	@Override public int maxStackIL() {
		return Math.max(condition.maxStackIL(), thenBody.maxStackIL());
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = condition.compileIL(ctx);
		
		String etiqueta = ctx.newLabel();
		ctx.codeIL.append("brsfalse " +etiqueta+ "\n");
		
		ctx = thenBody.compileIL(ctx);
		ctx.codeIL.append(etiqueta + ":nop" + "\n");
		
		return ctx;
	}
	
	@Override public Sentencia optimization(Estado state) {
		Expresion bExpCondition = condition.optimization(state);
		
		if(bExpCondition instanceof ValorVerdad){
			if(((ValorVerdad)bExpCondition).value){
				return thenBody.optimization(state);
			}
		}
		
		Sentencia stmtThenBodyOpt = thenBody.optimization(state);
		
		return new SiEntonces(bExpCondition, stmtThenBodyOpt);
	}

	@Override public String toString() {
		return "SiEntonces("+ condition +", "+ thenBody + ")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.thenBody == null ? 0 : this.thenBody.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		SiEntonces other = (SiEntonces)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
			&& (this.thenBody == null ? other.thenBody == null : this.thenBody.equals(other.thenBody));
			//&& (this.elseBody == null ? other.elseBody == null : this.elseBody.equals(other.elseBody));
	}

	public static SiEntonces generate(Random random, int min, int max) {
		Expresion condition; Sentencia thenBody;
		condition = Expresion.generate(random, min-1, max-1);
		thenBody = Sentencia.generate(random, min-1, max-1);
		return new SiEntonces(condition, thenBody);
	}

	@Override public ChequearEstado check(ChequearEstado checkstate){
		if (condition.check(checkstate).equals("bool")){
			
			return thenBody.check(checkstate);
					
		}
		return null;
		
		
	}
}
