/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.CompilationContextIL;

/** Función Imprimir.
*/
public class Mostrar extends Sentencia {
	public final Expresion exp;
	public static ChequearEstado globalEstado = new ChequearEstado();

	public Mostrar(Expresion exp) {
		this.exp = exp;
	}

	@Override public String unparse() {
		return "mostrar "+ exp.unparse()+" }";
	}

	@Override public String toString() {
		return "Mostrar("+ exp +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.exp == null ? 0 : this.exp.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Mostrar other = (Mostrar)obj;
		return (this.exp == null ? other.exp == null : this.exp.equals(other.exp));
	}

	/*public static Mostrar generate(Random random, int min, int max) {
		Expresion exp;
		exp = Expresion.generate(random, min-1, max-1);
		return new Mostrar(exp);		
	}*/
	
	/*@Override public Estado evaluate(Estado state){
		Object aState = this.exp.evaluate(state);
		System.out.println(aState.toString());
		return state;	
	}*/

	@Override
	public Set<String> freeVariables(Set<String> vars) {
		return exp.freeVariables(vars);
	}

	@Override
	public int maxStackIL() {
		return exp.maxStackIL();
	}

	@Override
	public CompilationContextIL compileIL(CompilationContextIL ctx) {
		String aux = (String)(exp.check(globalEstado));
		ctx = exp.compileIL(ctx);
		if(aux.equals("entero"))
			ctx.codeIL.append("call       void [mscorlib]System.Console::Write(int32) "+"// "+exp.toString()+"\n");
		if(aux.equals("texto"))
			ctx.codeIL.append("call       void [mscorlib]System.Console::Write(string)"+"// "+exp.toString()+"\n");
		ctx.codeIL.append("nop \n");
		return ctx;
	}

	@Override
	public Sentencia optimization(Estado state) {
		Expresion aux = exp.optimization(state);
		
		return new Mostrar(aux);
	}	
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		
		if (!((exp.check(checkstate).equals("entero")) || (exp.check(checkstate).equals("texto")))){
		
			Errores.exceptionList.add(new Errores("Mostrar \"" + exp.toString()+ "\" no es texto ni numerico."));
		}	
		return checkstate;
	}
}
