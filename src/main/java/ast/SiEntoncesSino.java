package ast;

import java.util.*;

/** Representación de las sentencias condicionales.
*/
public class SiEntoncesSino extends Stmt {
	public final Exp condition;
	public final Stmt thenBody;
	public final Stmt elseBody;

	public SiEntoncesSino(Exp condition, Stmt thenBody, Stmt elseBody) {
		this.condition = condition;
		this.thenBody = thenBody;
		this.elseBody = elseBody;
	}

	@Override public String unparse() {
		return "if "+ condition.unparse() +" then { "+ thenBody.unparse() +" } else { "+ elseBody.unparse() +" }";
	}

	@Override public String toString() {
		return "IfThenElse("+ condition +", "+ thenBody +", "+ elseBody +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.thenBody == null ? 0 : this.thenBody.hashCode());
		result = result * 31 + (this.elseBody == null ? 0 : this.elseBody.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		SiEntoncesSino other = (SiEntoncesSino)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
			&& (this.thenBody == null ? other.thenBody == null : this.thenBody.equals(other.thenBody))
			&& (this.elseBody == null ? other.elseBody == null : this.elseBody.equals(other.elseBody));
	}

	public static SiEntoncesSino generate(Random random, int min, int max) {
		Exp condition; Stmt thenBody; Stmt elseBody; 
		condition = Exp.generate(random, min-1, max-1);
		thenBody = Stmt.generate(random, min-1, max-1);
		elseBody = Stmt.generate(random, min-1, max-1);
		return new SiEntoncesSino(condition, thenBody, elseBody);
	}
	
	@Override public State evaluate(State state){
		Boolean resCond = (Boolean) condition.evaluate(state);
		if (resCond){
			state = thenBody.evaluate(state);
		}else{
			state = elseBody.evaluate(state);
		}
		return state;
	}	
}
