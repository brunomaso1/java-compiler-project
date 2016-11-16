/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

public class ParComp {
	private String id;
	private String tipo;

	public ParComp(String id, String tipo) {
		this.id = id;
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
