package behaviour;
import java.io.*;

public class LeeFichero {
   public static void main(String [] arg) {
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;

      try {
         archivo = new File ("salida.il");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null){
        	 linea = linea.replace("#replaceLocal", ".local");
        	 linea = linea.replace("#replaceCode", "code");
        	 System.out.println(linea);
         }
         
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
      
   }
   
   public static String escribirArch(String maxStack, String locals, String code){
	   File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;
	      String texto = "";
	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File ("entrada.il");
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         
	         while((linea=br.readLine())!=null){
	        	 linea = linea.replace("#maxStack", maxStack);
	        	 linea = linea.replace("#replaceLocal", locals);
	        	 linea = linea.replace("#replaceCode", code);
	        	 System.out.println(linea);
	        	 texto += linea + "\n"; 
	         }
	         
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
	      return texto;
   }
   
   public static void escribirArch(String texto){
	   FileWriter fichero = null;
       PrintWriter pw = null;
       try
       {
           fichero = new FileWriter("salida.il");
           pw = new PrintWriter(fichero);

           pw.print(texto);


       } catch (Exception e) {
           e.printStackTrace();
       } finally {
          try {
          // Nuevamente aprovechamos el finally para 
          // asegurarnos que se cierra el fichero.
          if (null != fichero)
             fichero.close();
          } catch (Exception e2) {
             e2.printStackTrace();
          }
       }
   }
}