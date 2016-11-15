package behaviour;
import java.io.*;

public class Archivo {
    public static String generarTemplate(String maxStack, String locals, String code, String codeFunctions){
	   File archivo = null;
	      FileReader fileReader = null;
	      BufferedReader bufferedReader = null;
	      String file = "";
	      try {
	         archivo = new File ("Archivos\\Template.il");
	         fileReader = new FileReader (archivo);
	         bufferedReader = new BufferedReader(fileReader);
	         String line;
	         while((line=bufferedReader.readLine())!=null){
	        	 line = line.replace("#maxStack", maxStack);
	        	 line = line.replace("#Locals", locals);
	        	 line = line.replace("#Code", code);
	        	 line = line.replace("#FunctionsCode", codeFunctions);
	        	 System.out.println(line);
	        	 file += line + "\n"; 
	         }
	         
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                    
	            if( null != fileReader ){   
	            	fileReader.close();     
	            }                  
	         }catch (Exception ee){ 
	        	 ee.printStackTrace();
	         }
	      }
	      return file;
   }
   
   public static void generarArchivo(String file){
	   FileWriter fileWriter = null;
       PrintWriter printWriter = null;
       try
       {
           fileWriter = new FileWriter("Archivos\\easyLanguage.il");
           printWriter = new PrintWriter(fileWriter);
           printWriter.print(file);
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
          try {
	          if (null != fileWriter)
	        	  fileWriter.close();
	          } catch (Exception ee) {
	             ee.printStackTrace();
	          }
       }
   }
}