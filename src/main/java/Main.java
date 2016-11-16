/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
import java.io.*;

import ast.*;
import parser.*;
import behaviour.*;

public class Main {
		
	public static void main(String[] args) throws Exception {		
		String funcionesIL = "";
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder source = new StringBuilder();
		
		ChequearEstado estadoChequeo = new ChequearEstado();
		
		String intro = new String("Bienvenidos al compilador de EASY_LANGUAGE!\n"+"Por favor, ingrese sus funciones.");
		System.out.println(intro);
		System.out.print("> ");
		
		for (String line; (line = in.readLine()) != null && line.length() > 0 ;) {
			source.append(line).append("\n");
		} 
			
		try {
					
			Definicion prog = (Definicion)(Parser.parse(source.toString()).value);
						
			estadoChequeo = prog.check(estadoChequeo);
			Mostrar.globalEstado = estadoChequeo;
			CompararIgual.globalEstado = estadoChequeo;
			LlamarFuncion.globalEstado = estadoChequeo;
			Concatenar.globalEstado = estadoChequeo;
			
			estadoChequeo.print();
			
			if (Errores.exceptionList.isEmpty()){
				funcionesIL = CompilationContextIL.compileIL(prog);
				System.out.println("==========" );

				System.out.println(funcionesIL);
				
				System.out.println("==========");

			}else{
				Errores.imprimirErrores();
			}
		}catch (Exception e) {
			
			System.out.println(e.toString());
			System.out.println(e.getMessage());
		}
		
		estadoChequeo.borrar();
		
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
			CompararIgual.globalEstado = estadoChequeo;
			LlamarFuncion.globalEstado = estadoChequeo;
			Concatenar.globalEstado = estadoChequeo;
			
			estadoChequeo.print();
			
			if (Errores.exceptionList.isEmpty()){
				String il = CompilationContextIL.compileIL(prog2,funcionesIL);
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
		
		/*try{
        	Runtime.getRuntime().exec("cmd /c start generarExe.bat");
        }catch(Exception e){
        	System.out.println(e);
        }*/
	}
}