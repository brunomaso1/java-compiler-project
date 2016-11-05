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
	
	//todo:
	//crear una lista de parametros en el State. 
	// luego en el compile de cada cosa, chequear si es variable o parametro.
	
	//Crear SuperClase Definicion de la que hereda Funcion.
		
	public final String id;
	public final Variable parametro;
	public final Variable[] parametros;
	public final Sentencia cuerpo;
	public final Variable resultado;

	public Funcion(String id, Variable parametro, Sentencia cuerpo) {
		this.id = id;
		this.parametro = parametro;
		this.parametros = null;
		this.cuerpo = cuerpo;
		this.resultado = new Variable("$resultado");
	}
	

	@Override public String unparse() {
		String text = "";
		for (ExpresionAritmetica expresionAritmetica : parametros) {
			text += expresionAritmetica+",";
		}
		if(text!=""){
			text = text.substring(0,text.length()-1);
		}
		text = id +"("+text+") ";
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
		result = Math.max(result, cuerpo.maxStackIL());
		result = Math.max(result, resultado.maxStackIL());
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
		
		String etiqueta = ctx.newLabel();
		ctx.codeIL.append(etiqueta + ":"+"\n");
		ctx.codeIL.append("nop" + "\n");
		ctx = cuerpo.compileIL(ctx);
		
		//br.s        IL_0009
		String saltoFinal = ctx.newLabel();
		ctx.codeIL.append("br.s " +saltoFinal+ "\n");
		//IL_0009:  ldloc.1     
		ctx.codeIL.append(saltoFinal + ":");
		Integer index = ctx.variables.indexOf(resultado);
		ctx.codeIL.append(" ldloc " +  index + "\n");
		//IL_000A:  ret
		ctx.codeIL.append("ret" + "\n\n");   
				
		ctx.parametros.clear();
		ctx.variables.clear();

		return ctx;
	}
	
	@Override public Funcion optimization(State state){		
		//if(state.get(id) != null)
		//	return new Numeral(state.get(id));
		return this;
	}
	
	@Override public String toString() {
		return "Funcion("+ id +", "+ Arrays.toString(parametros) +", "+cuerpo.toString()+" )";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		
		int result2 = 0;
		for (ExpresionAritmetica expression : parametros) result2 = Math.max(result2, expression.maxStackIL());
		
		int result3 = 0;
		result3 = Math.max(result3, cuerpo.maxStackIL());
				
		result = result * 31 + result2 + result3;
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
				
		Funcion other = (Funcion)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id)) && Arrays.equals(this.parametros, other.parametros) && this.cuerpo.equals(other.cuerpo);
	}

	public static Funcion generate(Random random, int min, int max) {
		String id; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		Variable[] parametros;
		parametros = new Variable[random.nextInt(Math.max(0, max)+1)];
		for (int i = 0; i < parametros.length; i++) {
			parametros[i] = Variable.generate(random, min-1, max-1);
		}
		
		Sentencia cuerpo; 
		cuerpo = Sentencia.generate(random, min-1, max-1);
		return new Funcion(id,new Variable("$a"),cuerpo);	
		
	}
}