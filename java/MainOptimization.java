
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import parser.Parser;
import ast.Sentencia;
import ast.Estado;
import behaviour.CompilationContextIL;


public class MainOptimization {

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder source = new StringBuilder();
		for (String line; (line = in.readLine()) != null && line.length() > 0 ;) {
			source.append(line).append("\n");
		}
		
		Estado state = new Estado();
		try {
			Sentencia prog = (Sentencia)(Parser.parse(source.toString()).value);
			state = prog.evaluate(state);
			System.out.println("// evaluation = "+ state);
			System.out.println("AST sin optimizar:");
			System.out.println(prog.unparse());
			Sentencia result = prog.optimization(state);
			System.out.println("AST optimizado:");
			System.out.println(result.unparse());
		} catch (Exception err) {
			System.err.print(err);
			err.printStackTrace();
		}
	}
}
