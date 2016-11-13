package ast;

public class ParFunc {
	private Par[] pares;
	
	public Par[] getPares() {
		return pares;
	}
	
	public Par getPar(int index) {
		return pares[index];
	}

	public void setPares(Par[] pares) {
		this.pares = pares;
	}

	public Par getResultado() {
		return resultado;
	}

	public void setResultado(Par resultado) {
		this.resultado = resultado;
	}

	private Par resultado;

	public ParFunc( Par[] pares, Par resultado) {
		this.pares = pares;
		this.resultado = resultado;
	}
	/*public String getTipo() {
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
	}*/
	
}
