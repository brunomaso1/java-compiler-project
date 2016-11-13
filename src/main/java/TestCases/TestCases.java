package TestCases;

import static org.junit.Assert.*;

import org.junit.Test;

import ast.*;
import parser.*;
import behaviour.*;

public class TestCases {

	private static String VARIABLE_A_TESTEAR = "$variable";
	private static String VARIABLE_A_TESTEAR_SEGUNDA = "$variable2";
	private static Expresion NUMERAL_A_TESTEAR = new Numeral(5.0);
	private static Expresion NUMERAL_A_TESTEAR_SEGUNDA = new Numeral(6.0);
	private static Expresion BOOLEAN_VERDADERO_A_TESTEAR = new ValorVerdad(true);
	private static Expresion BOOLEAN_FALSO_A_TESTEAR = new ValorVerdad(false);
	
	
	private DeclaracionIniciar declaracionIniciarNumeral(String variable, Expresion numeral){
		DeclaracionIniciar unaDeclaracion = new DeclaracionIniciar(numeral, Tipo.ENTERO, variable);
		return unaDeclaracion;
	}
	
	private DeclaracionIniciar declaracionIniciarBooleanVerdadero(){
		DeclaracionIniciar unaDeclaracion = new DeclaracionIniciar(BOOLEAN_VERDADERO_A_TESTEAR, Tipo.BOOLEAN, VARIABLE_A_TESTEAR);
		return unaDeclaracion;
	}
	
	@Test
	public void testDeclaracionIguales() throws Exception {
		System.out.println("poner 5 en entero $variable.");
		
		DeclaracionIniciar unaDeclaracion = declaracionIniciarNumeral(VARIABLE_A_TESTEAR,NUMERAL_A_TESTEAR);
		String cadena = "poner 5 en entero $variable.";
		
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;
		assertNotNull(resultado);
		assertNotNull(unaDeclaracion);
		assertTrue(unaDeclaracion.equals(resultado));
	}
	
	@Test
	public void testDeclaracionDistintos() throws Exception {
		System.out.println("poner 5 en entero $variable. distinto a  poner 6 en entero $variable.");
		
		DeclaracionIniciar unaDeclaracion = declaracionIniciarNumeral(VARIABLE_A_TESTEAR,NUMERAL_A_TESTEAR);
		String cadena = "poner 6 en entero $variable.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(unaDeclaracion);
		assertFalse(unaDeclaracion.equals(resultado));
	}
	
	@Test
	public void testDeclaracionTiposDistintos() throws Exception {
		System.out.println("poner esVerdadero en boolean $variable. distinto a  poner 6 en entero $variable.");
		
		DeclaracionIniciar unaDeclaracion = declaracionIniciarBooleanVerdadero();
		String cadena = "poner 6 en entero $variable.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(unaDeclaracion);
		assertFalse(unaDeclaracion.equals(resultado));
	}	
	
	@Test
	public void testDeclaracionesValoresIgualesDiferenteNombre() throws Exception {
		System.out.println("poner esVerdadero en boolean $variable. distinto a  poner esVerdadero en boolean $variable2.");
		
		DeclaracionIniciar unaDeclaracion = declaracionIniciarBooleanVerdadero();
		String cadena = "poner esVerdadero en boolean $variable2.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(unaDeclaracion);
		assertFalse(unaDeclaracion.equals(resultado));
	}	
	
	@Test
	public void testDeclaracionSentencias() throws Exception {
		System.out.println("{poner esFalso en boolean $variable. poner 6 en entero $variable2.}");
		
		String cadena = "{poner esFalso en boolean $variable. poner 6 en entero $variable2.}";
		Secuencia statementsAComparar = (Secuencia)Parser.parse(cadena).value;
		assertNotNull(statementsAComparar);

		DeclaracionIniciar variableAChequearBooleanda = new DeclaracionIniciar(BOOLEAN_FALSO_A_TESTEAR, Tipo.BOOLEAN, VARIABLE_A_TESTEAR);
		DeclaracionIniciar variableAChequearNumeral = declaracionIniciarNumeral(VARIABLE_A_TESTEAR_SEGUNDA,NUMERAL_A_TESTEAR_SEGUNDA);
		
		assertNotNull(variableAChequearBooleanda);
		assertNotNull(variableAChequearNumeral);
		
		assertTrue(statementsAComparar.statements[0].equals(variableAChequearBooleanda));
		assertTrue(statementsAComparar.statements[1].equals(variableAChequearNumeral));
	}
	
	@Test
	public void inicializarVariable() throws Exception {
		System.out.println("crearVariable $a tipo entero.");
		
		DeclaracionIniciar resEsp = declaracionIniciarBooleanVerdadero();
		String cadena = "poner esVerdadero en boolean $variable2.";
		DeclaracionIniciar resultado = (DeclaracionIniciar)Parser.parse(cadena).value;

		assertNotNull(resultado);
		assertNotNull(resEsp);
		assertFalse(resEsp.equals(resultado));
	}  
	
	@Test
	public void testSiEntoncesConDosVariablesSinOptimizar() throws Exception {
		System.out.println("{poner 5 en entero $variable. poner 6 en entero $variable2. si $variable < $variable2 entonces poner 8 en entero $variable3.}");
		String cadenaAComparar = "{poner 5 en entero $variable. poner 6 en entero $variable2. si $variable < $variable2 entonces poner 8 en entero $variable3.}";
		Secuencia statementsAComparar = (Secuencia)Parser.parse(cadenaAComparar).value;
		assertNotNull(statementsAComparar);
		
		int ultimoValor = statementsAComparar.statements.length-1;
		assertNotNull(ultimoValor);

		DeclaracionIniciar variableAChequear = declaracionIniciarNumeral(VARIABLE_A_TESTEAR,NUMERAL_A_TESTEAR);
		DeclaracionIniciar variableAChequearSegunda = declaracionIniciarNumeral(VARIABLE_A_TESTEAR_SEGUNDA,NUMERAL_A_TESTEAR_SEGUNDA);
		assertNotNull(variableAChequear);
		assertNotNull(variableAChequearSegunda);

		String cadena = "poner 8 en entero $variable3.";
		DeclaracionIniciar cuerpoCondicion = (DeclaracionIniciar)Parser.parse(cadena).value;
		assertNotNull(cuerpoCondicion);

		CompararMenor condicion = new CompararMenor(variableAChequear.expresion,variableAChequearSegunda.expresion);
		SiEntonces siEntonces = new SiEntonces(condicion, cuerpoCondicion);
		assertNotNull(condicion);
		assertNotNull(siEntonces);

		assertFalse(siEntonces.equals(statementsAComparar.statements[ultimoValor]));
	}

	/***
	 * Metodo que testea las condiciones SiEntonces con los valores declarados, deberían chequear 
	 * Condicion -> CompararMeno(Numeral(5.0),Numeral(6.0))
	 * ThenBody -> DeclaracionIniciar(ENTERO,Numeral(8.0))
	 * @throws Exception
	 */
	@Test
	public void testSiEntoncesConDosVariablesYSentenciaOptimizada() throws Exception {
		System.out.println("Optimizando --> {poner 5 en entero $variable. poner 6 en entero $variable2. si $variable < $variable2 entonces poner 8 en entero $variable3.}");
		String cadenaAComparar = "{poner 5 en entero $variable. poner 6 en entero $variable2. si $variable < $variable2 entonces poner 8 en entero $variable3.}";
		Secuencia statementsAComparar = (Secuencia)Parser.parse(cadenaAComparar).value;
		assertNotNull(statementsAComparar);
		
		
		//Para que funcione el optimization debería bajarme lo que subió Jorge que estaba trabajando en lo ultimo
		Estado state = new Estado();
		Secuencia secuenciaOptimizada = (Secuencia)(statementsAComparar.optimization(state));
		assertNotNull(secuenciaOptimizada);
		
		
		int ultimoValor = statementsAComparar.statements.length-1;
		assertNotNull(ultimoValor);

		DeclaracionIniciar variableAChequear = new DeclaracionIniciar(NUMERAL_A_TESTEAR,Tipo.ENTERO,VARIABLE_A_TESTEAR);
		DeclaracionIniciar variableAChequearSegunda = new DeclaracionIniciar(NUMERAL_A_TESTEAR_SEGUNDA,Tipo.ENTERO,VARIABLE_A_TESTEAR_SEGUNDA);
		assertNotNull(variableAChequear);
		assertNotNull(variableAChequearSegunda);

		String cadena = "poner 8 en entero $variable3.";
		DeclaracionIniciar cuerpoCondicion = (DeclaracionIniciar)Parser.parse(cadena).value;
		assertNotNull(cuerpoCondicion);

		CompararMenor condicion = new CompararMenor(variableAChequear.expresion,variableAChequearSegunda.expresion);
		SiEntonces siEntonces = new SiEntonces(condicion, cuerpoCondicion);
		assertNotNull(condicion);
		assertNotNull(siEntonces);

		// Esta condicion debería dar true
		assertTrue(siEntonces.equals(statementsAComparar.statements[ultimoValor]));
	}
	
	@Test
	public void testCrearVariablesYAsignarlas() throws Exception {	
		System.out.println("{crearVariable $variable tipo boolean. crearVariable $variable2 tipo boolean. poner esVerdadero en $variable. poner esFalso en $variable2. si $variable y $variable2 entonces poner 3 en entero $c. sino nada.}");
		String cadenaAComparar = "{crearVariable $variable tipo boolean. crearVariable $variable2 tipo boolean. poner esVerdadero en $variable. poner esFalso en $variable2. si $variable y $variable2 entonces poner 3 en entero $c. sino nada.}";
		Sentencia statementsAComparar = (Sentencia)Parser.parse(cadenaAComparar).value;
		assertNotNull(statementsAComparar);
		
		ChequearEstado estadoAChequear = new ChequearEstado();
		estadoAChequear = statementsAComparar.check(estadoAChequear);
		assertNotNull(estadoAChequear);
		
		Par parAValidar = estadoAChequear.devolverValor(VARIABLE_A_TESTEAR);
		Par parAValidar2 = estadoAChequear.devolverValor(VARIABLE_A_TESTEAR_SEGUNDA);
		Par parVariableCAValidar = estadoAChequear.devolverValor("$c");
		assertNotNull(parAValidar);
		assertNotNull(parAValidar2);
		assertNotNull(parVariableCAValidar);
		
		Declaracion declaracionVariable = new Declaracion(VARIABLE_A_TESTEAR, Tipo.BOOLEAN);
		Declaracion declaracionVariable2 = new Declaracion(VARIABLE_A_TESTEAR_SEGUNDA,Tipo.BOOLEAN);
		assertNotNull(declaracionVariable);
		assertNotNull(declaracionVariable2);
		
		assertTrue(parAValidar.getTipo().equals(declaracionVariable.tipo.toString().toLowerCase()));
		assertTrue(parAValidar2.getTipo().equals(declaracionVariable2.tipo.toString().toLowerCase()));
		
		Asignacion asignacionVariable = new Asignacion(declaracionVariable.variable, BOOLEAN_VERDADERO_A_TESTEAR);
		Asignacion asignacionVariable2 = new Asignacion(declaracionVariable2.variable,BOOLEAN_FALSO_A_TESTEAR);
		assertNotNull(asignacionVariable);
		assertNotNull(asignacionVariable2);
		
		Expresion unaExpresion = new Numeral(3.0);
		DeclaracionIniciar variableCDeclarada = declaracionIniciarNumeral("$c",unaExpresion);
		
		assertNotNull(variableCDeclarada);
		assertTrue(parVariableCAValidar.getTipo().equals(variableCDeclarada.tipo.toString().toLowerCase()));
	}
	
	
	
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion 1. poner 3 en $l1 posicion 2. poner 8 en $l1 posicion 3. }

	
	
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion 0. poner 3 en $l1 posicion 1. poner 8 en $l1 posicion 2. }
	
	
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion -0. poner 3 en $l1 posicion -1. poner 8 en $l1 posicion -2.}

}