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
		
		//try {
			//Definicion prog = (Definicion)(Parser.parse(source.toString()).value);
			System.out.println("=============");
		
			//System.out.println(Parser.parse(source.toString()).value);
			System.out.println("=============");
			
		
			Definicion def = (Definicion)(Parser.parse(source.toString()).value);
			String il = CompilationContextIL.compileIL(def);
	}
}