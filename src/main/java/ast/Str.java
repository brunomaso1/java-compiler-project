package ast;

import java.util.*;

public class Str extends Exp{
	public final String str;

	public Str(String str) {
		this.str = str;
	}

	@Override public String unparse() {
		return str.toString();
	}

	@Override public String toString() {
		return "String("+ str +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.str == null ? 0 : this.str.hashCode());
		return result;
	}

	/* ESTA CLASE PUEDE TENER PROBLEMAS */
	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Str other = (Str)obj;
		return (this.str == null ? other.str == null : this.str.equals(other.str));
	}

	public static Str generate(Random random, int min, int max) {
		String str; 
		str = "String generado aleatoreamente.";
		return new Str(str);
	}

	@Override
	public Object evaluate(State state) {
		return new String(str);
	}
}
