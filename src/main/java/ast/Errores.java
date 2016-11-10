package ast;

import java.util.ArrayList;

/**
 * My custom exception class.
 */
public class Errores extends Exception
{
  public static ArrayList<Exception> exceptionList = new ArrayList<Exception>();
  public Errores(String message)
  {
    super(message);
  }
  public static void agregar(String error) {
	  exceptionList.add(new Errores(error));
  }
  public static void imprimirErrores(){
	  for (int i=0; i<exceptionList.size(); i++) {
		  System.out.println(exceptionList.get(i));
	  }
	  
  }
}
