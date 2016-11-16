/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.*;

/**
 * Representacion de las concatenaciones. Son funciones del lenguaje.
 * @author Grupo_9
 * @version 0.0.1
 * @date 16 nov. 2016
 */
public class Concatenar extends Expresion {
	public final Expresion left;
	public final Expresion right;
	
	public static ChequearEstado globalEstado = new ChequearEstado();
	/**
	 * Constructor de la clase.
	 * @param left
	 * @param right
	 */
	public Concatenar(Expresion left, Expresion right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String unparse() {
		return "concatenar(" + left.unparse() + " , " + right.unparse() + ")";
	}

	@Override
	public Set<String> freeVariables(Set<String> vars) {
		return right.freeVariables(left.freeVariables(vars));
	}

	@Override
	public int maxStackIL() {
		return Math.max(left.maxStackIL(), right.maxStackIL() + 1);
	}

	/**
	 * FALTA PROBAR QUE CASO FUNCIONA.
	 */
	@Override
	public CompilationContextIL compileIL(CompilationContextIL ctx) {
		String izq = (String)(left.check(globalEstado));
		String der = (String)(right.check(globalEstado));
		Boolean entro = false;
		
		ctx = this.left.compileIL(ctx);
		if(izq.equals("entero")){
			entro = true;
			ctx.codeIL.append("box [mscorlib]System.Int32 \n");
		}
		ctx = this.right.compileIL(ctx);
		if(der.equals("entero")){
			entro = true;
			ctx.codeIL.append("box [mscorlib]System.Int32 \n");
		}
		if(entro){
			ctx.codeIL.append("call		string [mscorlib]System.String::Concat(object, object) \n");
		}else{
			ctx.codeIL.append("call 	string [mscorlib]System.String::Concat(string, string) \n");
		}
	
		return ctx;
	}

	/**
	 * Optimizaciones:
	 * - Pliegue de constantes.
	 * - Simplificaciones algebraicas.
	 */
	@Override
	public Expresion optimization(Estado state) {
		Expresion izq = left.optimization(state);
		Expresion der = right.optimization(state);

		// izq vacio y der con texto
		if (izq instanceof Expresion && der instanceof Texto) {
			if (((Texto) der).str.equals("")) {
				return izq;
			}
		}
		// izq con texto y der vacio
		if (izq instanceof Texto && der instanceof Expresion) {
			if (((Texto) izq).str.equals("")) {
				return der;
			}
		}
		// vacio y vacio
		if (izq instanceof Texto && der instanceof Texto) {
			if (((Texto) izq).str.equals("") && ((Texto) der).str.equals("")) {
				Texto textoRes = new Texto("");
				return textoRes;
			}
		}
		Concatenar expresionesAConcatenar = new Concatenar(izq, der);
		return expresionesAConcatenar;
	}

	@Override
	public String toString() {
		return "Concatenar(" + left + ", " + right + ")";
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = result * 31 + (this.left == null ? 0 : this.left.hashCode());
		result = result * 31 + (this.right == null ? 0 : this.right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Concatenar other = (Concatenar) obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
				&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	@Override
	public Object check(ChequearEstado checkstate) {
		if ((left.check(checkstate).equals("entero")) & (right.check(checkstate).equals("entero"))) {
			return new String("texto");
		}else if ((left.check(checkstate).equals("texto")) & (right.check(checkstate).equals("entero"))) {
				return new String("texto");
		}else if ((left.check(checkstate).equals("entero")) & (right.check(checkstate).equals("texto"))) {
				return new String("texto");
		}else if ((left.check(checkstate).equals("texto")) & (right.check(checkstate).equals("texto"))) {
				return new String("texto");
		} else {
			Errores.exceptionList.add(
					new Errores("Concatenar \"" + left.toString() + " , " + right.toString() + "\" tipos no son compatibles."));
			return checkstate;
		}
	}
}