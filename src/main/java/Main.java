
import java.io.*;

import ast.*;
import parser.*;
import behaviour.*;

public class Main {
		
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder source = new StringBuilder();
		
		ChequearEstado estadoChequeo = new ChequearEstado();
		
		/*String intro = new String("Bienvenidos al compilador de EASY_LANGUAGE!\n"+"Por favor, ingrese sus funciones.");
		System.out.println(intro);
		System.out.print("> ");
		
		for (String line; (line = in.readLine()) != null && line.length() > 0 ;) {
			source.append(line).append("\n");
		} 
			
		ChequearEstado estadoChequeo = new ChequearEstado();
		try {
					
			Definicion prog = (Definicion)(Parser.parse(source.toString()).value);
						
			estadoChequeo = prog.check(estadoChequeo);
			estadoChequeo.print();
			
			if (Errores.exceptionList.isEmpty()){
				String il = CompilationContextIL.compileIL(prog);
				System.out.println(il);
			}else{
				Errores.imprimirErrores();
			}
			//PrintWriter out = new PrintWriter("src\\main\\result\\compilador.il");
			//out.print(il);
			//out.close();
		}catch (Exception e) {
			
			System.out.println(e.toString());
			System.out.println(e.getMessage());
		}*/
		
		BufferedReader inStmt = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sourceStmt = new StringBuilder();
		
		String introStmt = new String("Por favor, ingrese su programa main!\n");
		System.out.println(introStmt);
		System.out.print("> ");
		
		for (String line; (line = inStmt.readLine()) != null && line.length() > 0 ;) {
			sourceStmt.append(line).append("\n");
		} 	
		
		try {
			
			Sentencia prog2 = (Sentencia)(Parser.parse(sourceStmt.toString()).value);
						
			estadoChequeo = prog2.check(estadoChequeo);
			Mostrar.globalEstado = estadoChequeo;
			estadoChequeo.print();
			
			if (Errores.exceptionList.isEmpty()){
				String il = CompilationContextIL.compileIL(prog2);
				System.out.println(il);
			}else{
				Errores.imprimirErrores();
			}
			//PrintWriter out = new PrintWriter("src\\main\\result\\compilador.il");
			//out.print(il);
			//out.close();
		}catch (Exception e) {
			
			System.out.println(e.toString());
			System.out.println(e.getMessage());
		}
	}
}