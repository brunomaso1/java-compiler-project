/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.*;

import java.io.*;


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

	@Override public Set<String> freeVariables(Set<String> vars) {
		vars = condition.freeVariables(vars); return thenBody.freeVariables(vars);
	}

	@Override public int maxStackIL() {
		return Math.max(condition.maxStackIL(), thenBody.maxStackIL());
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = condition.compileIL(ctx);
		
		String etiqueta = ctx.newLabel();
		ctx.codeIL.append("brfalse " +etiqueta+ "\n");
		
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
	}

	@Override public ChequearEstado check(ChequearEstado checkstate){
		if (condition.check(checkstate).equals("boolean")){
			return thenBody.check(checkstate);
		}else{
			Errores.exceptionList.add(new Errores("SiEntonces Condicion \"" + condition.toString() + "\" no es booleana"));
		}			
		return checkstate;		
	}
}
