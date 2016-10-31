import java.io.*;
import ast.*;
import parser.*;
import behaviour.*;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder source = new StringBuilder();
		
		String intro = new String("Bienvenidos al compilador de EASY_LANGUAGE!\n");
		System.out.println(intro);
		System.out.print("> ");
		
		for (String line; (line = in.readLine()) != null && line.length() > 0 ;) {
			source.append(line).append("\n");
		} 
		
		Sentencia prog = (Sentencia)(Parser.parse(source.toString()).value);
		String il = CompilationContextIL.compileIL(prog);
		
		System.out.println(il);

		PrintWriter out = new PrintWriter("src\\main\\result\\compilador.il");
		out.print(il);
		out.close();
	}
}