package ast;

import java.util.*;

import behaviour.*;


public class SiEntoncesSino extends Sentencia {
	public final Expresion condition;
	public final Sentencia thenBody;
	public final Sentencia elseBody;

	public SiEntoncesSino(Expresion condition, Sentencia thenBody, Sentencia elseBody) {
		this.condition = condition;
		this.thenBody = thenBody;
		this.elseBody = elseBody;
	}

	@Override public String unparse() {
		return "Si "+ condition.unparse() +" entonces { "+ thenBody.unparse() +" } sino { "+ elseBody.unparse() +" }";
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
		ctx.codeIL.append("brsfalse.s " +etiqueta+ "\n");
		ctx = thenBody.compileIL(ctx);
		String etiqueta2 = ctx.newLabel(); 
		ctx.codeIL.append("br.s " + etiqueta2 + "\n");
		ctx.codeIL.append(etiqueta + ":" + "\n");
		ctx = elseBody.compileIL(ctx);
		ctx.codeIL.append(etiqueta2 + ":nop" + "\n");
		return ctx;	
	
	}
	
	@Override public Sentencia optimization(Estado state) {
		Expresion con = condition.optimization(state);
		Sentencia thenB = thenBody.optimization(state);
		Estado copiaThenB = state;
		Sentencia elseB = elseBody.optimization(state);
		Estado copiaElseB = state;
		if(con instanceof ValorVerdad){
			if(((ValorVerdad)con).value){
				return thenB;
			}else{
				return elseB;
			}
		}else{
			//intersectar el state optimizado con el copiado
			ArrayList<String> clavesThen = copiaThenB.devolverClaves();
			Estado resultado = new Estado();
			String z;
			for (int i = 0; i< clavesThen.size();i++) {
				z = clavesThen.get(i);
				if (copiaThenB.get(z) == copiaElseB.get(z)) {
					resultado.set(z, copiaThenB.get(z));
				}
			}
			state = resultado;
			return new SiEntoncesSino(con, thenB, elseB);	
		}
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
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		if (condition.check(checkstate).equals("boolean")){ 
			ChequearEstado a = thenBody.check(checkstate); 
			ChequearEstado b = elseBody.check(checkstate);
			ChequearEstado resultado = new ChequearEstado();
			ArrayList<String> clavesA = a.devolverClaves();
			String z;
			for (int i = 0; i< clavesA.size();i++) {
				z = clavesA.get(i);
				if (paresIguales(a.devolverValor(z), b.devolverValor(z))) {
					resultado.agregar(z, a.devolverValor(z));
				}
			}
			return resultado;					
		}else{
			Errores.exceptionList.add(new Errores("SiEntoncesSino Condicion \"" + condition.toString() + "\" no es booleana"));
		}
		return checkstate;
	}
	
	private boolean paresIguales(Par a, Par b){
		if ((a.getTipo()).equals(b.getTipo()) && (a.isInicializada() == b.isInicializada())) {
			return true;
		}return false;
	}
}
