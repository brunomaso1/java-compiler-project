package ast;

public class Par {
	private String tipo;
	private boolean inicializada;
	public Par(String tipo, boolean inicializada) {
		this.tipo = tipo;
		this.inicializada = inicializada;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isInicializada() {
		return inicializada;
	}
	public void setInicializada(boolean inicializada) {
		this.inicializada = inicializada;
	}
}
