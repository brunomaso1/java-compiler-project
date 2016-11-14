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
	private static Expresion TEXTO_A_TESTEAR = new Texto("hola");
	private static Expresion TEXTO_A_TESTEAR_SEGUNDA = new Texto("hola");
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
	 * Se optimiza la sentencia if porque la condicion siempre da TRUE
	 * @throws Exception
	 */
	@Test
	public void testSiEntoncesConDosVariablesYSentenciaOptimizada() throws Exception {
		System.out.println("Optimizando --> {poner 5 en entero $variable. poner 6 en entero $variable2. si TRUE entonces poner 8 en entero $variable3.}");
		String cadenaAComparar = "{poner 5 en entero $variable. poner 6 en entero $variable2. si $variable < $variable2 entonces poner 8 en entero $variable3.}";
		Secuencia statementsAComparar = (Secuencia)Parser.parse(cadenaAComparar).value;
		assertNotNull(statementsAComparar);
		Estado state = new Estado();
		Secuencia secuenciaOptimizada = (Secuencia)(statementsAComparar.optimization(state));
		assertNotNull(secuenciaOptimizada);
		int ultimoValor = statementsAComparar.statements.length-1;
		assertNotNull(ultimoValor);
		DeclaracionIniciar variableAChequear = declaracionIniciarNumeral(VARIABLE_A_TESTEAR,NUMERAL_A_TESTEAR);
		DeclaracionIniciar variableAChequearSegunda = declaracionIniciarNumeral(VARIABLE_A_TESTEAR_SEGUNDA,NUMERAL_A_TESTEAR_SEGUNDA); 
		assertNotNull(variableAChequear);
		assertNotNull(variableAChequearSegunda);
		String cadena = "poner 8 en entero $variable3.";
		DeclaracionIniciar cuerpoCondicion = (DeclaracionIniciar)Parser.parse(cadena).value;
		assertNotNull(cuerpoCondicion);
		int valorUltimoSentenciaOptimizada = secuenciaOptimizada.statements.length-1; 
		assertTrue(secuenciaOptimizada.statements[valorUltimoSentenciaOptimizada].equals(cuerpoCondicion));
	}
	
	 /***
	  * Metodo que testea las condiciones SiEntonces con los valores declarados, deberían chequear 
	  * Condicion -> CompararMeno(Numeral(5.0),Numeral(6.0))
	  * ThenBody -> DeclaracionIniciar(ENTERO,Numeral(8.0)) 
	  * @throws Exception
	  */
		@Test
		public void testSiEntoncesConDosVariablesYSentenciaOptimizadaFALSA() throws Exception {
			System.out.println("Optimizando --> {poner 5 en entero $variable. poner 6 en entero $variable2. si FALSE entonces nada. sino poner 8 en entero $variable3.}");
			String cadenaAComparar = "{poner 5 en entero $variable. poner 6 en entero $variable2. si $variable > $variable2 entonces poner 14 en entero $variable3. sino poner 8 en entero $variable3.}";
			Secuencia statementsAComparar = (Secuencia)Parser.parse(cadenaAComparar).value;
			assertNotNull(statementsAComparar);
			Estado state = new Estado();
			Secuencia secuenciaOptimizada = (Secuencia)(statementsAComparar.optimization(state));
			assertNotNull(secuenciaOptimizada);
			int ultimoValor = statementsAComparar.statements.length-1;
			assertNotNull(ultimoValor);
			DeclaracionIniciar variableAChequear = declaracionIniciarNumeral(VARIABLE_A_TESTEAR,NUMERAL_A_TESTEAR);
			DeclaracionIniciar variableAChequearSegunda = declaracionIniciarNumeral(VARIABLE_A_TESTEAR_SEGUNDA,NUMERAL_A_TESTEAR_SEGUNDA); 
			assertNotNull(variableAChequear);
			assertNotNull(variableAChequearSegunda);
			String cadena = "poner 8 en entero $variable3.";
			DeclaracionIniciar cuerpoCondicion = (DeclaracionIniciar)Parser.parse(cadena).value;
			assertNotNull(cuerpoCondicion);
			int valorUltimoSentenciaOptimizada = secuenciaOptimizada.statements.length-1; 
			assertTrue(secuenciaOptimizada.statements[valorUltimoSentenciaOptimizada].equals(cuerpoCondicion));
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
	
	@Test
	public void testWhileOptimizandoLasSentenciasConTrue() throws Exception {	
		System.out.println("{poner 5 en entero $variable. poner 6 en entero $variable2. poner 10 en entero $variable3. poner 10 en entero $variable4. poner $variable + $variable2 en entero $variable5. poner $variable3 + $variable4 en entero $variable6. mientras $variable5 < $variable6 hacer poner $variable5 + 10 en entero $variable5.");
		
		String cadenaAComparar = "{poner 5 en entero $variable. poner 6 en entero $variable2. poner 10 en entero $variable3. poner 10 en entero $variable4. poner $variable + $variable2 en entero $variable5. poner $variable3 + $variable4 en entero $variable6. mientras $variable5 < $variable6 hacer poner $variable5 + 10 en entero $variable7.}";
		Sentencia statementsAComparar = (Sentencia)Parser.parse(cadenaAComparar).value;
		assertNotNull(statementsAComparar);
		
		ChequearEstado estadoAChequear = new ChequearEstado();
		estadoAChequear = statementsAComparar.check(estadoAChequear);
		assertNotNull(estadoAChequear);
		
		
		Expresion unaExpresion    = new Numeral(10.0);
		Expresion expresionDos    = new Numeral(10.0);
		Expresion expresionTres   = new Suma(NUMERAL_A_TESTEAR,NUMERAL_A_TESTEAR_SEGUNDA);
		Expresion expresionCuatro = new Suma(unaExpresion, expresionDos);
		
		assertNotNull(unaExpresion);
		assertNotNull(expresionDos);
		assertNotNull(expresionTres);
		assertNotNull(expresionCuatro);
		
		
		DeclaracionIniciar variable       = declaracionIniciarNumeral(VARIABLE_A_TESTEAR, NUMERAL_A_TESTEAR);
		DeclaracionIniciar variableDos    = declaracionIniciarNumeral(VARIABLE_A_TESTEAR_SEGUNDA, NUMERAL_A_TESTEAR_SEGUNDA);
		DeclaracionIniciar variableTres   = declaracionIniciarNumeral("$variable3",unaExpresion);
		DeclaracionIniciar variableCuatro = declaracionIniciarNumeral("$variable4",expresionDos);
		DeclaracionIniciar variableCinco  = declaracionIniciarNumeral("$variable5",expresionTres);
		DeclaracionIniciar variableSeis   = declaracionIniciarNumeral("$variable6",expresionCuatro);
		Sentencia body			          = new Asignacion(variableCinco.id,new Suma(variableCinco.expresion,unaExpresion)); 
		Expresion condition 		      = new CompararMenorOIgual(expresionTres,expresionCuatro);
		
		assertNotNull(variable);
		assertNotNull(variableDos);
		assertNotNull(variableTres);
		assertNotNull(variableCuatro);
		assertNotNull(variableCinco);
		assertNotNull(variableSeis);
		assertNotNull(body);
		assertNotNull(condition);
		
		MientrasHacer mientrasHacer = new MientrasHacer(condition, body);
		
		ChequearEstado estadoChequearMientras = new ChequearEstado();
		estadoChequearMientras = mientrasHacer.check(estadoChequearMientras);
		
		assertNotNull(mientrasHacer);
		//hay que hacer el assertequals
		Par parAValidar = estadoAChequear.devolverValor(VARIABLE_A_TESTEAR);
		Par parAValidar2 = estadoAChequear.devolverValor(VARIABLE_A_TESTEAR_SEGUNDA);
		Par parAValidar3 = estadoAChequear.devolverValor("$variable3");
		Par parAValidar4 = estadoAChequear.devolverValor("$variable4");
		Par parAValidar5 = estadoAChequear.devolverValor("$variable5");
		Par parAValidar6 = estadoAChequear.devolverValor("$variable6");
		Par parAValidar7 = estadoAChequear.devolverValor("$variable7");
//		Par parAValidar8 = estadoChequearMientras.devolverValor("");
		
		
		
		assertNotNull(parAValidar);
		assertNotNull(parAValidar2);
		assertNotNull(parAValidar3);
		assertNotNull(parAValidar4);
		assertNotNull(parAValidar5);
		assertNotNull(parAValidar6);
		assertNotNull(parAValidar7);
//		assertNotNull(parAValidar8);
		
		assertTrue(parAValidar.getTipo().equals(variable.tipo.toString().toLowerCase()));
		assertTrue(parAValidar2.getTipo().equals(variableDos.tipo.toString().toLowerCase()));
		assertTrue(parAValidar3.getTipo().equals(variableTres.tipo.toString().toLowerCase()));
		assertTrue(parAValidar4.getTipo().equals(variableCuatro.tipo.toString().toLowerCase()));
		assertTrue(parAValidar5.getTipo().equals(variableCinco.tipo.toString().toLowerCase()));
		assertTrue(parAValidar6.getTipo().equals(variableSeis.tipo.toString().toLowerCase()));
	}
	
	
	@Test
	public void testWhileOptimizandoLasSentenciasConFalse() throws Exception {	
		System.out.println("{poner 5 en entero $variable. poner 6 en entero $variable2. poner 10 en entero $variable3. poner 10 en entero $variable4. poner $variable + $variable2 en entero $variable5. poner $variable3 + $variable4 en entero $variable6. mientras $variable5 > $variable6 hacer poner $variable5 + 10 en entero $variable5.");
		
		String cadenaAComparar = "{poner 5 en entero $variable. poner 6 en entero $variable2. poner 10 en entero $variable3. poner 10 en entero $variable4. poner $variable + $variable2 en entero $variable5. poner $variable3 + $variable4 en entero $variable6. mientras $variable > $variable6 hacer poner $variable5 + 10 en entero $variable7.}";
		Sentencia statementsAComparar = (Sentencia)Parser.parse(cadenaAComparar).value;
		assertNotNull(statementsAComparar);
		
		ChequearEstado estadoAChequear = new ChequearEstado();
		estadoAChequear = statementsAComparar.check(estadoAChequear);
		assertNotNull(estadoAChequear);
		
		
		Expresion unaExpresion    = new Numeral(10.0);
		Expresion expresionDos    = new Numeral(10.0);
		Expresion expresionTres   = new Suma(NUMERAL_A_TESTEAR,NUMERAL_A_TESTEAR_SEGUNDA);
		Expresion expresionCuatro = new Suma(unaExpresion, expresionDos);
		
		assertNotNull(unaExpresion);
		assertNotNull(expresionDos);
		assertNotNull(expresionTres);
		assertNotNull(expresionCuatro);
		
		
		DeclaracionIniciar variable       = declaracionIniciarNumeral(VARIABLE_A_TESTEAR, NUMERAL_A_TESTEAR);
		DeclaracionIniciar variableDos    = declaracionIniciarNumeral(VARIABLE_A_TESTEAR_SEGUNDA, NUMERAL_A_TESTEAR_SEGUNDA);
		DeclaracionIniciar variableTres   = declaracionIniciarNumeral("$variable3",unaExpresion);
		DeclaracionIniciar variableCuatro = declaracionIniciarNumeral("$variable4",expresionDos);
		DeclaracionIniciar variableCinco  = declaracionIniciarNumeral("$variable5",expresionTres);
		DeclaracionIniciar variableSeis   = declaracionIniciarNumeral("$variable6",expresionCuatro);
		Sentencia body			          = new Asignacion(variableCinco.id,new Suma(variableCinco.expresion,unaExpresion)); 
		Expresion condition 		      = new CompararMenorOIgual(expresionTres,expresionCuatro);
		
		assertNotNull(variable);
		assertNotNull(variableDos);
		assertNotNull(variableTres);
		assertNotNull(variableCuatro);
		assertNotNull(variableCinco);
		assertNotNull(variableSeis);
		assertNotNull(body);
		assertNotNull(condition);
		
		MientrasHacer mientrasHacer = new MientrasHacer(condition, body);
		
		ChequearEstado estadoChequearMientras = new ChequearEstado();
		estadoChequearMientras = mientrasHacer.check(estadoChequearMientras);
		
		assertNotNull(mientrasHacer);
		//hay que hacer el assertequals
		Par parAValidar = estadoAChequear.devolverValor(VARIABLE_A_TESTEAR);
		Par parAValidar2 = estadoAChequear.devolverValor(VARIABLE_A_TESTEAR_SEGUNDA);
		Par parAValidar3 = estadoAChequear.devolverValor("$variable3");
		Par parAValidar4 = estadoAChequear.devolverValor("$variable4");
		Par parAValidar5 = estadoAChequear.devolverValor("$variable5");
		Par parAValidar6 = estadoAChequear.devolverValor("$variable6");
		Par parAValidar7 = estadoAChequear.devolverValor("$variable7");
//		Par parAValidar8 = estadoChequearMientras.devolverValor("");
		
		
		
		assertNotNull(parAValidar);
		assertNotNull(parAValidar2);
		assertNotNull(parAValidar3);
		assertNotNull(parAValidar4);
		assertNotNull(parAValidar5);
		assertNotNull(parAValidar6);
		assertNotNull(parAValidar7);
//		assertNotNull(parAValidar8);
		
		assertTrue(parAValidar.getTipo().equals(variable.tipo.toString().toLowerCase()));
		assertTrue(parAValidar2.getTipo().equals(variableDos.tipo.toString().toLowerCase()));
		assertTrue(parAValidar3.getTipo().equals(variableTres.tipo.toString().toLowerCase()));
		assertTrue(parAValidar4.getTipo().equals(variableCuatro.tipo.toString().toLowerCase()));
		assertTrue(parAValidar5.getTipo().equals(variableCinco.tipo.toString().toLowerCase()));
		assertTrue(parAValidar6.getTipo().equals(variableSeis.tipo.toString().toLowerCase()));
	}
	
	
// optimization de 2+3 hacer que me de un numeral de 5 siempre.
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion 1. poner 3 en $l1 posicion 2. poner 8 en $l1 posicion 3. }	
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion 0. poner 3 en $l1 posicion 1. poner 8 en $l1 posicion 2. }
//{ crearLista $l1 tipo entero cantidad 3. poner 4 en $l1 posicion -0. poner 3 en $l1 posicion -1. poner 8 en $l1 posicion -2.}

}