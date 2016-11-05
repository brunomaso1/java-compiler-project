package ast;

import java.util.*;

import behaviour.*;

/**
 * Representacion de las asignaciones de valores a variables.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class LlamarFuncion extends Sentencia {
	public final String id;
	public final ExpresionAritmetica[] parametros;
	public final Variable resultado;

	public LlamarFuncion(String id, ExpresionAritmetica[] parametros, Variable resultado) {
		this.id = id;
		this.parametros = parametros;
		this.resultado = resultado;
	}

	@Override public String unparse() {		
		String text = "";
		for (ExpresionAritmetica expresionAritmetica : parametros) {
			text += expresionAritmetica.unparse()+",";
		}
		if(text!=""){
			text = text.substring(0,text.length()-1);
		}
		text = "llamar "+id +"("+text+")"+ " poner en "+resultado.unparse()+ ".";
		return text;
	}

	@Override public State evaluate(State state) {
		return state;
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		return vars;
	}

	@Override public int maxStackIL() {
		return 1;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		//ctx= expression.compileIL(ctx);

		//Integer index = ctx.variables.indexOf(id);
		//ctx.codeIL.append("stloc." + index + "\n");
		return ctx;
	}
	@Override public LlamarFuncion optimization(State state){		
		//if(state.get(id) != null)
		//	return new Numeral(state.get(id));
		return this;
	}
	
	@Override public String toString() {
		return "Llamar("+ id +", "+ Arrays.toString(parametros)+", "+ resultado.toString()+")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		
		int result2 = 0;
		for (ExpresionAritmetica expression : parametros) result2 = Math.max(result2, expression.maxStackIL());
		
		result = result * 31 + result2 + resultado.hashCode();
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
				
		LlamarFuncion other = (LlamarFuncion)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id)) && Arrays.equals(this.parametros, other.parametros) && (this.resultado.equals(other.resultado));
	}

	public static LlamarFuncion generate(Random random, int min, int max) {
		String id; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		ExpresionAritmetica[] parametros;
		parametros = new ExpresionAritmetica[random.nextInt(Math.max(0, max)+1)];
		for (int i = 0; i < parametros.length; i++) {
			parametros[i] = ExpresionAritmetica.generate(random, min-1, max-1);
		}
		
		Variable resultado;
		String idVar; 
		idVar = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		resultado = new Variable(idVar);
		
		return new LlamarFuncion(id,parametros,resultado);	
	}
}