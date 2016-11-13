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
public class Funcion extends Definicion {
	public final String id;
	public final Parametro[] parametros;
	public final Parametro resultado;
	public final Sentencia cuerpo;

	public Funcion(String id, Parametro[] parametros, Parametro resultado, Sentencia cuerpo) {
		this.id = id;
		this.parametros = parametros;
		this.cuerpo = cuerpo;
		this.resultado = resultado;
	}
	
	@Override public String unparse() {
		String text = "";
		for (Parametro variable : parametros) {
			text += variable.unparse()+",";
		}
		if(text!=""){
			text = text.substring(0,text.length()-1);
		}
		text = "funcion "+ id +"("+text+") "+resultado.unparse()+" ";
		text += cuerpo.unparse();
		return text;
	}

	/*@Override public Estado evaluate(Estado state) {
		return state;
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		for (Parametro parametro : parametros) vars = parametro.freeVariables(vars);
		vars = cuerpo.freeVariables(vars);
		vars = resultado.freeVariables(vars);
		return vars;
	}

	@Override public int maxStackIL() {
		int result = 0;
		for (Parametro parametro : parametros){ 
			result = Math.max(result, parametro.maxStackIL());
		}
		//result = Math.max(result, cuerpo.maxStackIL());
		//result = Math.max(result, resultado.maxStackIL());
		return result;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		//TODO revisar
		if(!ctx.variables.contains(id)){
			ctx.funciones.add(id);
		}else{
			//TODO Agregar ERROR.
		}
		for (Parametro variable : parametros) {
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
	
	@Override public Definicion optimization(Estado state){
		Estado newStateVacio = new Estado();
		Sentencia stmtBodyOpt = cuerpo.optimization(newStateVacio);
		return new Funcion(id,parametros,resultado,stmtBodyOpt);
	}
	
	@Override public String toString() {
		return "funcion("+ id +", "+ Arrays.toString(parametros) +", "+resultado.toString()+", "+cuerpo.toString()+" )";
		
	}

	@Override public int hashCode() {
		return 0;
	}

	@Override public boolean equals(Object obj) {
		return true;}

	@Override
	public ChequearEstado check(ChequearEstado checkstate) {
		ParFunc parFunc = checkstate.devolverValorFunc(id);
		if (parFunc != null){
			Errores.exceptionList.add(new Errores("Funcion \"" + id + "\" ya definida."));
		}
		else {
			Par[] params = new Par[parametros.length];
			int index = 0;
			for (Parametro variable : parametros) {
				if (checkstate.devolverValor(variable.id)==null){
					checkstate.agregar(variable.id, new Par(variable.tipo.toString(),true,false));
					params[index] = new Par(variable.tipo.toString(),true,false); 
				}else{
					Errores.exceptionList.add(new Errores("Funcion parámetro\"" + variable.toString() + "\" ya definido."));
				}
				index++;
			}
			Par res = null;
			if (checkstate.devolverValor(resultado.id)==null){
				checkstate.agregar(resultado.id, new Par(resultado.tipo.toString(),true,false));
				res = new Par(resultado.tipo.toString(),true,false); 
			}else{
				Errores.exceptionList.add(new Errores("Funcion resultado\"" + resultado.toString() + "\" ya definido."));
			}
			ParFunc parFunc2 = new ParFunc(params,res);
			checkstate.agregarFunc(id, parFunc2);
			checkstate = cuerpo.check(checkstate);
			checkstate.borrar();		
			
		}
		return checkstate;
	}

	/*public static Funcion generate(Random random, int min, int max) {
		return null;
	}*/
}