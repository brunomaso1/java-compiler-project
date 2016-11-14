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
	public final Expresion[] parametros;
	public final Variable resultado;

	public LlamarFuncion(String id, Expresion[] parametros, Variable resultado) {
		this.id = id;
		this.parametros = parametros;
		this.resultado = resultado;
	}

	@Override public String unparse() {		
		String text = "";
		for (Expresion expresionAritmetica : parametros) {
			text += expresionAritmetica.unparse()+",";
		}
		if(text!=""){
			text = text.substring(0,text.length()-1);
		}
		text = "llamar "+id +"("+text+")"+ " poner en "+resultado.unparse()+ ".";
		return text;
	}

	/*@Override public Estado evaluate(Estado state) {
		return state;
	}*/

	@Override public Set<String> freeVariables(Set<String> vars) {
		return vars;
	}

	@Override public int maxStackIL() {
		return 1;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		ctx.codeIL.append("ldarg.0"+"\n");	
		for (Expresion expresion : parametros) {
			if(expresion instanceof Numeral){
				ctx.codeIL.append("ldc.i4.s "+ ((Numeral)expresion).number.intValue() +"\n");
			}else{
				ctx= expresion.compileIL(ctx);	
			}			
		}
		ctx.codeIL.append("call UserQuery."+ id+ "\n");
		
		Integer index = ctx.variables.indexOf(resultado.id);
		ctx.codeIL.append("stloc." + index + "\n");
		
		return ctx;
		
		
	}
	
	
	@Override public LlamarFuncion optimization(Estado state){		
		Expresion[] aux = new Expresion[parametros.length]; 
		
		int i = 0;
		for (Expresion expresion : parametros) {
			Expresion auxOpt = expresion.optimization(state);
			aux[i] = auxOpt;
			i++;
		}
		return new LlamarFuncion(id, aux, resultado);
	}
	
	@Override public String toString() {
		return "Llamar("+ id +", "+ Arrays.toString(parametros)+", "+ resultado.toString()+")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		
		int result2 = 0;
		for (Expresion expression : parametros) result2 = Math.max(result2, expression.maxStackIL());
		
		result = result * 31 + result2 + resultado.hashCode();
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
				
		LlamarFuncion other = (LlamarFuncion)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id)) && Arrays.equals(this.parametros, other.parametros) && (this.resultado.equals(other.resultado));
	}
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		ParFunc parFunc = checkstate.devolverValorFunc(id);
		if (parFunc == null){
			Errores.exceptionList.add(new Errores("LlamarFuncion \"" + id + "\" no definida."));
		}
		else {
			int index = 0;
			for (Expresion expresion : parametros) {
				if (expresion.check(checkstate).equals(parFunc.getPar(index).getTipo()) == false){
					Errores.exceptionList.add(new Errores("LlamarFuncion parametro \"" + expresion.toString() + "\" tipo no compatible."));
				}				
			}
			if (resultado.check(checkstate).equals(parFunc.getResultado().getTipo()) == false){
				Errores.exceptionList.add(new Errores("LlamarFuncion resultado \"" + resultado.toString() + "\" tipo no compatible."));
			}
			
		}	
		return checkstate;		
	}
}