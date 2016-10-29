package behaviour;

import java.util.*;

import ast.Stmt;

public class CompilationContextIL {
	public final List<String> variables = new ArrayList<String>();
	public final int maxStack;
	public final StringBuilder codeIL = new StringBuilder(); 
	
	private int currentLabel = 0;

	public CompilationContextIL(Stmt prog) {
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
		return Integer.toString(currentLabel++, 16);
	}
	
	/** Este método se utiliza para generar el código IL y obtener como un
	 *  String.
	 */
	public static String compileIL(Stmt prog) {
		CompilationContextIL ctx = new CompilationContextIL(prog);
		ctx.codeIL.append("// variables = "+ ctx.variables +"\n");
		ctx.codeIL.append("// maxStack =  "+ ctx.maxStack +"\n");
		
		/*TODO Agregar el código IL necesario para definir el assembly, la clase
		 * principal, método main e impresión del estado resultante de la 
		 * ejecución. 
		 */
		prog.compileIL(ctx);
		ctx.codeIL.append("ret");
		return ctx.codeIL.toString(); 
	}
}
