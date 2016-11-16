/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.*;

/**
 * Representacion de diyunciones booleanas.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Disyuncion extends Expresion {
	public final Expresion left;
	public final Expresion right;

	public Disyuncion(Expresion left, Expresion right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" | "+ right.unparse() +")";
	}

	/*@Override
	public Object evaluate(Estado state) {
		if ((left.evaluate(state) instanceof Boolean) & (right.evaluate(state) instanceof Boolean))
			return new Boolean((Boolean)left.evaluate(state) || (Boolean)right.evaluate(state));
		else {
			System.out.print("Estas haciendo una conjuncion mal. Numero1 -> " + left.evaluate(state) + " Numero2 -> " + right.evaluate(state));
			return null;
		}
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		return right.freeVariables(left.freeVariables(vars));
	}

	@Override public int maxStackIL() {
		return Math.max(left.maxStackIL(), right.maxStackIL() + 1);
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx = left.compileIL(ctx);
		ctx = right.compileIL(ctx);
		ctx.codeIL.append("or \n");
		return ctx;
	}
	
	@Override public Expresion optimization(Estado state){
		//OR
		Expresion izq = left.optimization(state);
		Expresion der = right.optimization(state);
		
		if(izq instanceof Expresion && ((ValorVerdad)der).value){
			return new ValorVerdad (true);
		}
		
		if(((ValorVerdad)izq).value && der instanceof Expresion){
			return new ValorVerdad(true);
		}
	
		if(izq instanceof ValorVerdad && der instanceof ValorVerdad){
			if(((ValorVerdad)izq).value == false && ((ValorVerdad)der).value == false){
				return new ValorVerdad(false);
			}
		}
		Conjuncion b = new Conjuncion(izq, der);
		return b;
	}

	@Override public String toString() {
		return "Disyuncion("+ left +", "+ right +")";
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
		Disyuncion other = (Disyuncion)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	/*public static Disyuncion generate(Random random, int min, int max) {
		Expresion left; Expresion right; 
		left = Expresion.generate(random, min-1, max-1);
		right = Expresion.generate(random, min-1, max-1);
		return new Disyuncion(left, right);
	}*/
	
	@Override
	public Object check(ChequearEstado checkstate) {
		if ((left.check(checkstate).equals("boolean")) & (right.check(checkstate).equals("boolean"))){
			return new String("boolean");
		}else {
			Errores.exceptionList.add(new Errores("Disyuncion \"" + this.toString() + "\" tipos no booleanos."));
		}
		return checkstate;
	}

}
