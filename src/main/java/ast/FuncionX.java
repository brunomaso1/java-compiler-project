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
public class FuncionX extends Definicion {
	public final String id;
	public final Variable[] parametros;
	public final Sentencia cuerpo;
	public final Variable resultado;

	
//public final ExpresionAritmetica expression;

	public FuncionX(String id, Variable[] parametros, Sentencia cuerpo) {
		this.id = id;
		this.parametros = parametros;
		this.cuerpo = cuerpo;
		this.resultado = new Variable("$resultado");
	}
	
	@Override public String unparse() {
		String text = "";
		for (Variable variable : parametros) {
			text += variable.unparse()+",";
		}
		if(text!=""){
			text = text.substring(0,text.length()-1);
		}
		text = "funcion "+ id +"("+text+") ";
		text += cuerpo.unparse();
		return text;
	}

	@Override public State evaluate(State state) {
		return state;
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		for (Variable parametro : parametros) vars = parametro.freeVariables(vars);
		vars = cuerpo.freeVariables(vars);
		vars = resultado.freeVariables(vars);
		return vars;
	}

	@Override public int maxStackIL() {
		int result = 0;
		for (Variable parametro : parametros){ 
			result = Math.max(result, parametro.maxStackIL());
		}
		//result = Math.max(result, cuerpo.maxStackIL());
		//result = Math.max(result, resultado.maxStackIL());
		return result;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		if(!ctx.variables.contains(id)){
			ctx.funciones.add(id);
		}else{
			//TODO Agregar ERROR.
		}
		for (Variable variable : parametros) {
			ctx.parametros.add(variable.id);
		}
		ctx.variables.add(resultado.id);
		
		String etiqueta = id;
		ctx.codeIL.append(etiqueta + ":"+"\n");
		ctx.codeIL.append("nop" + "\n");
		ctx = cuerpo.compileIL(ctx);
		
		//br.s        IL_0009
		String saltoFinal = ctx.newLabel();
		ctx.codeIL.append("br.s " +saltoFinal+ "\n");
		//IL_0009:  ldloc.1     
		ctx.codeIL.append(saltoFinal + ":");
		Integer index = ctx.variables.indexOf(resultado.id);
		ctx.codeIL.append(" ldloc " +  index + "\n");
		//IL_000A:  ret
		ctx.codeIL.append("ret" + "\n\n");   
				
		ctx.parametros.clear();
		ctx.variables.clear();

		return ctx;
	}
	
	@Override public Definicion optimization(State state){
		State newStateVacio = new State();
		Sentencia stmtBodyOpt = cuerpo.optimization(newStateVacio);
		state.variables.clear();//Porque no sabemos cuales constantes se invalidaron dentro del cuerpo del while.
		return new FuncionX(id,parametros,cuerpo);
	}
	
	@Override public String toString() {
		return "funcion("+ id +", "+ Arrays.toString(parametros) +", "+cuerpo.toString()+" )";
		
	}

	@Override public int hashCode() {
		return 0;
	}

	@Override public boolean equals(Object obj) {
		return true;}

	public static FuncionX generate(Random random, int min, int max) {
		return null;
	}
}