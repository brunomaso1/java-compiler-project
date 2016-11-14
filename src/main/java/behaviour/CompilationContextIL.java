package behaviour;

import java.util.*;

import ast.Definicion;
import ast.ParComp;
//import behaviour.LeeFichero;
import ast.Sentencia;

public class CompilationContextIL {
	public final List<String> variables = new ArrayList<String>();
	public final List<ParComp> variablesTipo = new ArrayList<ParComp>();
	public final List<String> parametros = new ArrayList<String>();
	public final List<String> funciones = new ArrayList<String>();
	
	public final int maxStack;
	public final StringBuilder codeIL = new StringBuilder(); 
	
	private int currentLabel = 0;

	public CompilationContextIL(Sentencia prog) {
		this(prog.freeVariables(new HashSet<String>()), prog.maxStackIL());
	}
	
	public CompilationContextIL(Definicion prog) {
		this(prog.freeVariables(new HashSet<String>()), prog.maxStackIL());
	}
	
	public CompilationContextIL(Collection<String> variables, int maxStack) {
		this.variables.addAll(variables);
		this.maxStack = maxStack;
	}
	
	/** Usar este método para obtener un número único de etiqueta a la hora de
	 *  compilar construcciones que usan saltos.
	 */
	public String newLabel() {
		return "IL_" + Integer.toString(currentLabel++, 16);
	}
	
	/** Este método se utiliza para generar el código IL y obtener como un
	 *  String.
	 */
	public static String compileIL(Sentencia prog) {
		CompilationContextIL ctx = new CompilationContextIL(prog);
		ctx.codeIL.append("// variables = "+ ctx.variables +"\n");
		ctx.codeIL.append("// maxStack =  "+ ctx.maxStack +"\n");
		//ctx.codeIL.append("nop \n");
		
		/*TODO Agregar el código IL necesario para definir el assembly, la clase
		 * principal, método main e impresión del estado resultante de la 
		 * ejecución. 
		 */
		// prog.compileIL(ctx);
		
		ctx = prog.compileIL(ctx);
		
		/*for (String variable : ctx.variables) {
			int index = ctx.variables.indexOf(variable);
			ctx.codeIL.append("ldloc " + index + " \n");
			ctx.codeIL.append("call       void [mscorlib]System.Console::WriteLine(string) \n");			
		}*/
		
		for (ParComp variable : ctx.variablesTipo) {
			int index = ctx.variablesTipo.indexOf(variable);
			if (variable.getTipo().equals("entero")){
				ctx.codeIL.append("ldloc " + index + " \n");
				ctx.codeIL.append("call       void [mscorlib]System.Console::WriteLine(int32) \n");			
			}else{
				if (variable.getTipo().equals("texto")){
					ctx.codeIL.append("ldloc " + index + " \n");
					ctx.codeIL.append("call       void [mscorlib]System.Console::WriteLine(string) \n");			
				}else{
					/*if (variable.getTipo().equals("boolean")){
						ctx.codeIL.append("call       void [mscorlib]System.Console::WriteLine(string) \n");			
					}else{
						
					}*/
				}
			}
		}
		
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
	    //int32 V_0, int32 V_1, bool V_2)";
		
		String texto = LeeFichero.escribirArch(ctx.maxStack+"", local, ctx.codeIL.toString());
		LeeFichero.escribirArch(texto);
		return ctx.codeIL.toString(); 
		
		
		//antes
		/*CompilationContextIL ctx = new CompilationContextIL(prog);
		ctx.codeIL.append("nop \n");
		ctx.codeIL.append("// variables = "+ ctx.variables +"\n");
		ctx.codeIL.append("// maxStack =  "+ ctx.maxStack +"\n");
		
		

		prog.compileIL(ctx);
		ctx.codeIL.append("ret");
		return ctx.codeIL.toString(); */
		
		 
	}
	public static String compileIL(Definicion prog) {
		CompilationContextIL ctx = new CompilationContextIL(prog);
		ctx.codeIL.append("// variables = "+ ctx.variables +"\n");
		ctx.codeIL.append("// maxStack =  "+ ctx.maxStack +"\n");
		

		prog.compileIL(ctx);
		ctx.codeIL.append("ret");
		return ctx.codeIL.toString(); 
		
		 
	}
}
