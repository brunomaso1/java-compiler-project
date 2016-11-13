package TestCases;

import static org.junit.Assert.*;

import org.junit.Test;

import ast.*;
import parser.*;

public class TestCases {

	private static String VARIABLE_A_TESTEAR = "$variable";
	private static String VARIABLE_A_TESTEAR_SEGUNDA = "$varible2";
	private static Expresion NUMERAL_A_TESTEAR = new Numeral(5.0);
	private static Expresion NUMERAL_A_TESTEAR_SEGUNDA = new Numeral(6.0);
	private static Expresion BOOLEAN_VERDADERO_A_TESTEAR = new ValorVerdad(true);
	private static Expresion BOOLEAN_FALSO_A_TESTEAR = new ValorVerdad(false);
	
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
	public void inicializarVariable() throws Exception {
		System.out.println("crearVariable $a tipo entero.");
		
//		crearVariable $a tipo entero.
//		key :$a (tipo:ENTERO inicializada:false)
		
		DeclaracionIniciar resEsp = new DeclaracionIniciar(BOOLEAN_VERDADERO_A_TESTEAR, Tipo.BOOLEAN, VARIABLE_A_TESTEAR);
		String cadena = "poner esVerdadero en boolean $variable2.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(resEsp);
		assertFalse(resEsp.equals(resultado));
	}  
	
	
	@Test
	public void testSiEntoncesConDosVariables() throws Exception {
		System.out.println("{poner 5 en entero $variable. poner 6 en entero $variable2. si $variable < $variable2 entonces poner 8 en entero $variable3.}");

		DeclaracionIniciar variableAChequear = new DeclaracionIniciar(NUMERAL_A_TESTEAR,Tipo.ENTERO,VARIABLE_A_TESTEAR);
		DeclaracionIniciar variableAChequearSegunda = new DeclaracionIniciar(NUMERAL_A_TESTEAR_SEGUNDA,Tipo.ENTERO,VARIABLE_A_TESTEAR_SEGUNDA);
		
		assertNotNull(variableAChequear);
		assertNotNull(variableAChequearSegunda);
		
		//Sentencia son: asignacion, declaración, etc etc		
		
		Sentencia [] listaSentencias = new Sentencia[2];		
		listaSentencias[0] = variableAChequear;
		listaSentencias[1] = variableAChequearSegunda;
		Secuencia unaSecuencia = new Secuencia(listaSentencias);
				
        boolean variableBooleana = (variableAChequear < variableAChequearSegunda)? true:false;
		
		Expresion exp = new Expresion()
//		SiEntonces()
		
//		public SiEntonces(Expresion condition, Sentencia thenBody) {
//			this.condition = condition;
//			this.thenBody = thenBody;
//		}
				
		DeclaracionIniciar resEsp = new DeclaracionIniciar(BOOLEAN_VERDADERO_A_TESTEAR, Tipo.BOOLEAN, VARIABLE_A_TESTEAR);
		String sentencia = "{poner 5 en entero $variable. poner 6 en entero $variable2. si $variable < $variable2 entonces poner 8 en entero $variable3.}";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(sentencia).value;

		assertNotNull(resultado);
		assertNotNull(resEsp);
		assertFalse(resEsp.equals(resultado));
	}


	

	
	
	
	
//	> {poner esFalso en boolean $a. si $a entonces poner 5 en entero $b.}
//
//	=============
//	=============
//	key :$a (tipo:boolean inicializada:true)
//	key :$b (tipo:entero inicializada:true)
	
//	// variables = [$a, $b]
//	// maxStack =  1
//	ldloc 0
//	brsfalse IL_0
//	IL_0:nop
//	ret

	
	
	
	
	
	
	
	
	
	
	
	
	
//{ crearVariable $a tipo boolean. crearVariable $b tipo boolean. poner esVerdadero en $a. poner esFalso en $b. si $a y $b entonces nada. }
//
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion 1. poner 3 en $l1 posicion 2. poner 8 en $l1 posicion 3. }
//
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion 0. poner 3 en $l1 posicion 1. poner 8 en $l1 posicion 2. }
//
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion -0. poner 3 en $l1 posicion -1. poner 8 en $l1 posicion -2. }
}