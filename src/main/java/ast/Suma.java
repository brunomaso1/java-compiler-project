/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.*;

public class Suma extends Expresion {
	public final Expresion left;
	public final Expresion right;

	public Suma(Expresion left, Expresion right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" + "+ right.unparse() +")";
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		return right.freeVariables(left.freeVariables(vars));
	}

	@Override public int maxStackIL() {
		return Math.max(left.maxStackIL(), right.maxStackIL() + 1);
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = left.compileIL(ctx);
		ctx = right.compileIL(ctx);
		ctx.codeIL.append("add \n");
		return ctx;
	}
	
	@Override public Expresion optimization(Estado state){
		Expresion izq = left.optimization(state);
		Expresion der = right.optimization(state);
		if(izq instanceof Expresion && der instanceof Numeral){
			if (((Numeral)der).number == 0){
				return izq;
			}
		}
		if(izq instanceof Numeral && der instanceof Expresion){
				if (((Numeral)izq).number == 0){
					return der;	
				}
		}
		
		if(izq instanceof Numeral && der instanceof Numeral){
			if (((Numeral)izq).number == 0 ){
				return der;
			}else if (((Numeral)der).number == 0){
				return izq;
			}
			Numeral numRes = new Numeral(((Numeral)izq).number + ((Numeral)der).number);
			return numRes;
		}
		Suma a = new Suma(izq, der);
		return a;	
	}

	@Override public String toString() {
		return "Suma("+ left +", "+ right +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.left == null ? 0 : this.left.hashCode());
		result = result * 31 + (this.right == null ? 0 : this.right.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Suma other = (Suma)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}
	
	@Override public Object check(ChequearEstado checkstate){        
		if ((left.check(checkstate).equals("entero")) & (right.check(checkstate).equals("entero")))
			return new String("entero");
		else {
			Errores.exceptionList.add(new Errores("Suma \"" + left.toString()+" + " + right.toString()+ "\" tipos no numericos."));
			return checkstate;
		}	
	}
}
