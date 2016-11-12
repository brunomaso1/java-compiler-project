package ast;

public class Par {
	private String tipo;
	private boolean inicializada;
	private boolean esLista;

	public Par(String tipo, boolean inicializada, boolean esLista) {
		this.tipo = tipo;
		this.inicializada = inicializada;
		this.esLista = esLista;
	}
	public String getTipo() {
		if (esLista) {
			return "lista" + tipo.toLowerCase(); 
		}
		return tipo.toLowerCase();
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
	
	public String toString(){
		
		String aux = "tipo:"+tipo;
		if(inicializada){
			aux += " inicializada:true";
		}else{
			aux += " inicializada:false";
		}
		return "("+aux+")";
	}
	public boolean esLista() {
		return esLista;
	}
	public void setEsLista(boolean esLista) {
		this.esLista = esLista;
	}
	
}
