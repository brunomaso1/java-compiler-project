package behaviour;

import java.util.*;

import behaviour.LeeFichero;
import ast.Sentencia;

public class CompilationContextIL {
	public final List<String> variables = new ArrayList<String>();
	public final int maxStack;
	public final StringBuilder codeIL = new StringBuilder(); 
	
	private int currentLabel = 0;

	public CompilationContextIL(Sentencia prog) {
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
		/*CompilationContextIL ctx = new CompilationContextIL(prog);
		ctx.codeIL.append("// variables = "+ ctx.variables +"\n");
		ctx.codeIL.append("// maxStack =  "+ ctx.maxStack +"\n");
		
		/*TODO Agregar el código IL necesario para definir el assembly, la clase
		 * principal, método main e impresión del estado resultante de la 
		 * ejecución. 
		 */
		/*prog.compileIL(ctx);
		ctx.codeIL.append("ret");
		return ctx.codeIL.toString(); */
		
		CompilationContextIL ctx = new CompilationContextIL(prog);
		ctx.codeIL.append("// variables = "+ ctx.variables +"\n");
		ctx.codeIL.append("// maxStack =  "+ ctx.maxStack +"\n");
		
		/*TODO Agregar el código IL necesario para definir el assembly, la clase
		 * principal, método main e impresión del estado resultante de la 
		 * ejecución. 
		 */
		// prog.compileIL(ctx);
		
		ctx = prog.compileIL(ctx);
		
		for (String variable : ctx.variables) {
			int index = ctx.variables.indexOf(variable);
			ctx.codeIL.append("ldloc " + index + " \n");
			ctx.codeIL.append("call       void [mscorlib]System.Console::WriteLine(int32) \n");			
		}
		
	    String local = ".locals init (";
	    for (int i = 0; i < ctx.variables.size(); i++) {
			local += "int32 V_" + i;
			if (i != ctx.variables.size()-1)
				local += ",";
		}
	    local += ")";
	    //int32 V_0, int32 V_1, bool V_2)";
		
		String texto = LeeFichero.escribirArch(ctx.maxStack+"", local, ctx.codeIL.toString());
		LeeFichero.escribirArch(texto);
		return ctx.codeIL.toString(); 
	}
}
