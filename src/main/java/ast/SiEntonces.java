package ast;

import java.util.*;

/** Representación de las sentencias condicionales.
*/
public class SiEntonces extends Stmt {
	public final Exp condition;
	public final Stmt thenBody;

	public SiEntonces(Exp condition, Stmt thenBody) {
		this.condition = condition;
		this.thenBody = thenBody;
	}

	@Override public String unparse() {
		return "if "+ condition.unparse() +" then { "+ thenBody.unparse() +" }";
	}

	@Override public String toString() {
		return "IfThen("+ condition +", "+ thenBody + ")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.thenBody == null ? 0 : this.thenBody.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		SiEntonces other = (SiEntonces)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
			&& (this.thenBody == null ? other.thenBody == null : this.thenBody.equals(other.thenBody));
	}

	public static SiEntonces generate(Random random, int min, int max) {
		Exp condition; Stmt thenBody; 
		condition = Exp.generate(random, min-1, max-1);
		thenBody = Stmt.generate(random, min-1, max-1);
		return new SiEntonces(condition, thenBody);
	}
		
	@Override public State evaluate(State state){
		Boolean resCond = (Boolean) condition.evaluate(state);
		if (resCond){
			state = thenBody.evaluate(state);
		}
		return state;
	}		

}
