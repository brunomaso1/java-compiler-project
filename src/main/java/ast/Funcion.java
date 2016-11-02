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
	public final ExpresionAritmetica[] parametros;
	public final Secuencia cuerpo;
	public final Variable resultado;

	public Funcion(String id, ExpresionAritmetica[] parametros, Secuencia cuerpo) {
		this.id = id;
		this.parametros = parametros; 
		this.cuerpo = cuerpo;
		this.resultado = null;
	}
	

	@Override public String unparse() {
		String text = "";
		for (ExpresionAritmetica expresionAritmetica : parametros) {
			text += expresionAritmetica+",";
		}
		if(text!=""){
			text = text.substring(0,text.length()-1);
		}
		text = id +"("+text+"){";
		for (Sentencia statement : cuerpo.statements) text += statement.unparse() +"\n";
		text += "}";
		return text;
	}

	@Override public State evaluate(State state) {
		return state;
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		for (Sentencia statement : cuerpo.statements) vars = statement.freeVariables(vars);
		return vars;
	}

	@Override public int maxStackIL() {
		int result = 0;
		for (Sentencia statement : cuerpo.statements) result = Math.max(result, statement.maxStackIL());
		return result;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
	/*	ctx.codeIL.append(this.id+":"+"\n");
		int index = 0;
		for (ExpresionAritmetica expresionAritmetica : parametros) {
			ctx.codeIL.append(this.id+":"+"\n");
		}
		
		
		ctx= expression.compileIL(ctx);

		Integer index = ctx.variables.indexOf(id);
		ctx.codeIL.append("stloc." + index + "\n");
		return ctx;
		
		for (Sentencia stmt : statements) {
			ctx = stmt.compileIL(ctx);
		}
		return ctx;
		
	*/return ctx;}
	
	@Override public Funcion optimization(State state){		
		//if(state.get(id) != null)
		//	return new Numeral(state.get(id));
		return this;
	}
	
	@Override public String toString() {
		return "Funcion("+ id +", "+ Arrays.toString(parametros) +", "+Arrays.toString(cuerpo.statements) +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		
		int result2 = 0;
		for (ExpresionAritmetica expression : parametros) result2 = Math.max(result2, expression.maxStackIL());
		
		int result3 = 0;
		for (Sentencia statement : cuerpo.statements) result3 = Math.max(result3, statement.maxStackIL());
				
		result = result * 31 + result2 + result3;
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
				
		Funcion other = (Funcion)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id)) && Arrays.equals(this.parametros, other.parametros) && Arrays.equals(this.cuerpo.statements, other.cuerpo.statements);
	}

	public static Funcion generate(Random random, int min, int max) {
		String id; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		ExpresionAritmetica[] parametros;
		parametros = new ExpresionAritmetica[random.nextInt(Math.max(0, max)+1)];
		for (int i = 0; i < parametros.length; i++) {
			parametros[i] = ExpresionAritmetica.generate(random, min-1, max-1);
		}
		
		Sentencia[] statements; 
		statements = new Sentencia[random.nextInt(Math.max(0, max)+1)];
		for (int i = 0; i < statements.length; i++) {
			statements[i] = Sentencia.generate(random, min-1, max-1);
		}
		Secuencia cuerpo = new Secuencia(statements);
		return new Funcion(id,parametros,cuerpo);	
		
	}
}