package ast;

import java.util.*;

import behaviour.*;

import java.io.*;

/**
 * Representacion de las secuencias de instrucciones.
 *
 * @author Grupo_9
 * @version 0.0.1
 * @date 30 oct. 2016
 */
public class Secuencia extends Sentencia {
	public final Sentencia[] statements;

	public Secuencia(Sentencia[] statements) {
		this.statements = statements;
	}

	@Override public String unparse() {
		String result = "{ ";
		for (Sentencia statement : statements) result += statement.unparse() +" ";
		return result +"}";
	}

	@Override public Estado evaluate(Estado state) {
		for (Sentencia statement : statements) state = statement.evaluate(state);
					return state;
	}

	@Override public Set<String> freeVariables(Set<String> vars) {
		for (Sentencia statement : statements) vars = statement.freeVariables(vars);
					return vars;
	}

	@Override public int maxStackIL() {
		int result = 0;
		for (Sentencia statement : statements) result = Math.max(result, statement.maxStackIL());
		return result;
	}

	@Override public CompilationContextIL compileIL(CompilationContextIL ctx) {
		for (Sentencia stmt : statements) {
			ctx = stmt.compileIL(ctx);
		}
		return ctx;
	}

	@Override public Sentencia optimization(Estado state){
		ArrayList<Sentencia> newStatements = new ArrayList<Sentencia>();
		
		for (Sentencia stmt : statements) {
			Sentencia stmOpt = (Sentencia)stmt.optimization(state);
			if(stmOpt instanceof Secuencia){
				if(((Secuencia)stmOpt).statements.length > 0)
					newStatements.add(stmOpt);
			}else{
				newStatements.add(stmOpt);
			}
		}
		
		Sentencia [] a = new Sentencia[newStatements.size()];
		return new Secuencia(newStatements.toArray(a));
	}


	@Override public String toString() {
		return "Sequencia("+ Arrays.toString(statements) +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + Arrays.hashCode(this.statements);
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Secuencia other = (Secuencia)obj;
		return Arrays.equals(this.statements, other.statements);
	}

	public static Secuencia generate(Random random, int min, int max) {
		Sentencia[] statements; 
		statements = new Sentencia[random.nextInt(Math.max(0, max)+1)];
		for (int i = 0; i < statements.length; i++) {
			statements[i] = Sentencia.generate(random, min-1, max-1);
		}
		return new Secuencia(statements);
	}
	
	@Override public ChequearEstado check(ChequearEstado checkstate){
		for (Sentencia statement : statements) {
			checkstate = statement.check(checkstate);
		}
		return checkstate;
	}
}
