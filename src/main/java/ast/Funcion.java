/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
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

	
	/*private final String funcionMaqueta = "//		.method public hidebysig static #tipoRetorno#  #nombreFuncion# ( #parametros# ) cil managed \n"+
			"{ \n // Code size       11 (0xb)"+
			".maxstack  #maxStack# \n"+
			".locals init (#localVars#) \n \n"+
			"#funcionCode# \n"+
			"} // end of method Program::#nombreFuncion#";*/
	
	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
			if(!ctx.funciones.contains(id)){
				ctx.funciones.add(id);
			}else{
				Errores.exceptionList.add(new Errores("Funcion(compileIL) \"" + id + "\" ya definida."));
			}
			String parametrosTexto = "";
			for (Parametro variable : parametros) {
				ctx.parametros.add(variable.id);
				
				if(variable.tipo.toString().toLowerCase().equals("entero")){
					parametrosTexto += "int32 "+variable.id.replace("$", "")+",";
				}else{
					if(variable.tipo.toString().toLowerCase().equals("texto")){
						parametrosTexto += "String "+variable.id.replace("$", "")+",";
					}else{
						if(variable.tipo.toString().toLowerCase().equals("boolean")){
							parametrosTexto += "Boolean "+variable.id.replace("$", "")+",";
						}
					}
				}
			}
			if(parametrosTexto.length()>0){
				parametrosTexto = parametrosTexto.substring(0,parametrosTexto.length()-1);
			}
			ctx.variables.add(resultado.id);
			ctx.variablesTipo.add(new ParComp(resultado.id,resultado.tipo.toString().toLowerCase()));
			
			// formar cabezal
			String tipoRetorno = "";
			if(resultado.tipo.toString().toLowerCase().equals("entero")){
				tipoRetorno = "int32";
			}else{
				if(resultado.tipo.toString().toLowerCase().equals("texto")){
					tipoRetorno = "String";
				}else{
					if(resultado.tipo.toString().toLowerCase().equals("boolean")){
						tipoRetorno = "Boolean";
					}
				}
			}			
			
			String nombreFuncion = id.replace("%", "");
			 
			String cabezal = "		.method public hidebysig static #tipoRetorno#  #nombreFuncion# ( #parametrosTexto# ) cil managed \n { \n";
			cabezal = cabezal.replace("#tipoRetorno#",tipoRetorno);
			cabezal = cabezal.replace("#nombreFuncion#",nombreFuncion);
			cabezal = cabezal.replace("#parametrosTexto#",parametrosTexto);
			
			ctx.codeIL.append(cabezal);
			
			//formar locals y maxstack
			
			ctx.codeIL.append("#localsFuncion# \n");
						
			//funcion codeIL
			String etiqueta = ctx.newLabel();
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
			
			String finFuncion = "} // end of method Program::"+nombreFuncion+" \n\n";
			ctx.codeIL.append(finFuncion);  	
			
			// format locals variables -----------------
			
			//ctx.codeIL.append("// variables = "+ ctx.variables +"\n");
			//ctx.codeIL.append("// maxStack =  "+ ctx.maxStack +"\n");
			
			String maxStack = ".maxstack "+ctx.maxStack+" \n";
			String local = ".locals init (";
		    for (int i = 0; i < ctx.variablesTipo.size(); i++) {
		    	ParComp aux = ctx.variablesTipo.get(i);
		    	if (aux.getTipo().equals("entero")){
		    		local += "int32 V_" + i;	
		    	}else{
		    		if (aux.getTipo().equals("texto")){
			    		local += "string V_" + i;	
			    	}else{
			    		if (aux.getTipo().equals("boolean")){
				    		local += "bool V_" + i;	
				    	}else{
				        	if (aux.getTipo().equals("listaentero")){
					    		local += "int32[] V_" + i;	
					    	}else{
					    		if (aux.getTipo().equals("listatexto")){
						    		local += "string[] V_" + i;	
						    	}else{
						    		if (aux.getTipo().equals("listaboolean")){
							    		local += "bool[] V_" + i;	
							    	}else{
							    		
							    	}
						    	}
					    	}
				    	}
			    	}
		    	}
		    		
				if (i != ctx.variablesTipo.size()-1)
					local += ",";
			}
		    local += ")";
		    
		    replaceAll(ctx.codeIL, "#localsFuncion#", maxStack+local);
			
			// -----------------------------------------
			ctx.parametros.clear();
			ctx.variables.clear();
			ctx.variablesTipo.clear();

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
					Errores.exceptionList.add(new Errores("Funcion parametro\"" + variable.toString() + "\" ya definido."));
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
			//checkstate.borrar();	
			//se fue al main
			
		}
		return checkstate;
	}

	/*public static Funcion generate(Random random, int min, int max) {
		return null;
	}*/
	
	public static void replaceAll(StringBuilder builder, String from, String to)
	{
	    int index = builder.indexOf(from);
	    while (index != -1)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
	}
}