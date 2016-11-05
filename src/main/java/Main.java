import java.io.*;

import ast.*;
import parser.*;
import behaviour.*;

public class Main {
	public static void main(String[] args) throws Exception {
		/*Variable[] parametros = new Variable[2];
		Variable p1 = new Variable("$a");
		Variable p2 = new Variable("$b");
		parametros[0] = p1;
		parametros[1] = p2;
		Sentencia[] sen = new Sentencia[2];
		sen[0] = new Asignacion("$resultado", new Suma(p1,p2));
		sen[1] = new Asignacion("$resultado", new Suma(p1,p2));
				
		FuncionX f = new FuncionX("%suma",parametros,new Secuencia(sen));
		System.out.print(f.toString());*/
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder source = new StringBuilder();
		
		String intro = new String("Bienvenidos al compilador de EASY_LANGUAGE!\n");
		System.out.println(intro);
		System.out.print("> ");
		
		for (String line; (line = in.readLine()) != null && line.length() > 0 ;) {
			source.append(line).append("\n");
		} 
		
		//try {
			//Definicion prog = (Definicion)(Parser.parse(source.toString()).value);
			System.out.println("=============");
		
			//System.out.println(Parser.parse(source.toString()).value);
			System.out.println("=============");
			
			//Sentencia def = (Sentencia)(Parser.parse(source.toString()).value);
			
			//Definicion def = (Definicion)(Parser.parse(source.toString()).value);
			//String il = CompilationContextIL.compileIL(def);
		
			//System.out.println(il);
			
			//PrintWriter out = new PrintWriter("src\\main\\result\\compilador.il");
			//out.print(il);
			//out.close();
		/*}catch (Exception e) {
			
			System.out.println(e.toString());
			System.out.println(e.getMessage());
		}*/
	}
}