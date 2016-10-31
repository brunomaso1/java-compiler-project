package ast;

import java.util.*;
import behaviour.*;
import java.io.*;

/**
 * Representacion de la condicion logica Si->Entonces->Sino.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class SiEntoncesSino extends Sentencia {
	public final ExpresionVerdad condition;
	public final Sentencia thenBody;
	public final Sentencia elseBody;

	public SiEntoncesSino(ExpresionVerdad condition, Sentencia thenBody, Sentencia elseBody) {
		this.condition = condition;
		this.thenBody = thenBody;
		this.elseBody = elseBody;
	}

	@Override public String unparse() {
		return "Si "+ condition.unparse() +" entonces { "+ thenBody.unparse() +" } sino { "+ elseBody.unparse() +" }";
	}

	@Override public State evaluate(State state) {
		return condition.evaluate(state) ? thenBody.evaluate(state) : elseBody.evaluate(state);
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars = condition.freeVariables(vars); vars = thenBody.freeVariables(vars); return elseBody.freeVariables(vars);
	}

	@Override public int maxStackIL() {
		return Math.max(condition.maxStackIL(), Math.max(thenBody.maxStackIL(), elseBody.maxStackIL()));
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = condition.compileIL(ctx);
		
		String etiqueta = ctx.newLabel();
		ctx.codeIL.append("brsfalse " +etiqueta+ "\n");
		
		ctx = thenBody.compileIL(ctx);
		String etiqueta2 = ctx.newLabel(); 
		ctx.codeIL.append("br " + etiqueta2 + "\n");
		ctx.codeIL.append(etiqueta + ":" + "\n");
		ctx = elseBody.compileIL(ctx);
		ctx.codeIL.append(etiqueta2 + ":nop" + "\n");
		
		return ctx;
	}

	@Override public String toString() {
		return "SiEntoncesSino("+ condition +", "+ thenBody +", "+ elseBody +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.thenBody == null ? 0 : this.thenBody.hashCode());
		result = result * 31 + (this.elseBody == null ? 0 : this.elseBody.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		SiEntoncesSino other = (SiEntoncesSino)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
			&& (this.thenBody == null ? other.thenBody == null : this.thenBody.equals(other.thenBody))
			&& (this.elseBody == null ? other.elseBody == null : this.elseBody.equals(other.elseBody));
	}

	public static SiEntoncesSino generate(Random random, int min, int max) {
		ExpresionVerdad condition; Sentencia thenBody; Sentencia elseBody; 
		condition = ExpresionVerdad.generate(random, min-1, max-1);
		thenBody = Sentencia.generate(random, min-1, max-1);
		elseBody = Sentencia.generate(random, min-1, max-1);
		return new SiEntoncesSino(condition, thenBody, elseBody);
	}

}
