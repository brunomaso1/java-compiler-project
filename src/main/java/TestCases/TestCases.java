package TestCases;

import static org.junit.Assert.*;

import org.junit.Test;

import ast.*;
import parser.*;

public class TestCases {

	private static String VARIABLE_A_TESTEAR = "$variable";
	private static Expresion NUMERAL_A_TESTEAR = new Numeral(5.0);
	private static Expresion BOOLEAN_VERDADERO_A_TESTEAR = new ValorVerdad(true);
	
	@Test
	public void testDeclaracionIguales() throws Exception {
		System.out.println("poner 5 en entero $variable.");
		
		DeclaracionIniciar resEsp = new DeclaracionIniciar(NUMERAL_A_TESTEAR, Tipo.ENTERO, VARIABLE_A_TESTEAR);
		String cadena = "poner 5 en entero $variable.";
		
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;
		
		assertNotNull(resultado);
		assertNotNull(resEsp);
		assertTrue(resEsp.equals(resultado));
	}
	
	@Test
	public void testDeclaracionDistintos() throws Exception {
		System.out.println("poner 5 en entero $variable. distinto a  poner 6 en entero $variable.");
		
		DeclaracionIniciar resEsp = new DeclaracionIniciar(NUMERAL_A_TESTEAR, Tipo.ENTERO, VARIABLE_A_TESTEAR);
		String cadena = "poner 6 en entero $variable.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(resEsp);
		assertFalse(resEsp.equals(resultado));
	}
	
	@Test
	public void testDeclaracionTiposDistintos() throws Exception {
		System.out.println("poner esVerdadero en boolean $variable. distinto a  poner 6 en entero $variable.");
		
		DeclaracionIniciar resEsp = new DeclaracionIniciar(BOOLEAN_VERDADERO_A_TESTEAR, Tipo.BOOLEAN, VARIABLE_A_TESTEAR);
		String cadena = "poner 6 en entero $variable.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(resEsp);
		assertFalse(resEsp.equals(resultado));
	}	
	
	@Test
	public void testDeclaracionesValoresIgualesDiferenteNombre() throws Exception {
		System.out.println("poner esVerdadero en boolean $variable. distinto a  poner esVerdadero en boolean $variable2.");
		
		DeclaracionIniciar resEsp = new DeclaracionIniciar(BOOLEAN_VERDADERO_A_TESTEAR, Tipo.BOOLEAN, VARIABLE_A_TESTEAR);
		String cadena = "poner esVerdadero en boolean $variable2.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(resEsp);
		assertFalse(resEsp.equals(resultado));
	}	
	
	@Test
	public void test5() throws Exception {
		System.out.println("poner esVerdadero en boolean $variable. distinto a  poner esVerdadero en boolean $variable2.");
		
		DeclaracionIniciar resEsp = new DeclaracionIniciar(BOOLEAN_VERDADERO_A_TESTEAR, Tipo.BOOLEAN, VARIABLE_A_TESTEAR);
		String cadena = "poner esVerdadero en boolean $variable2.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(resEsp);
		assertFalse(resEsp.equals(resultado));
	}
//	poner 1 en entero $variable1. 
//	poner 6 en entero $variable2. 
//
//
//	si $variable1 < $variable2 entonces
//	poner $variable1 + 1 en $variable1.  
	

}
