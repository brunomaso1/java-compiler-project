package ast;

import java.util.HashMap;
import java.util.Map;

public class State {
	private HashMap <String, Object> state = new HashMap<String, Object>();
	
	public Object devolverValor(String variableName) {
		return state.get(variableName);
	}
	public void remover(String nombre) {
		state.remove(nombre);
	}
	public void agregar(String nombre, Object valor) {
		state.put(nombre, valor);
	}
	
	public void print() {
		
		for(Map.Entry<String, Object> entry : this.state.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue().toString();
		 
		    System.out.println("key :"+key+" value:"+value);
		    // do what you have to do here
		    // In your case, an other loop.
		}
	}
	
	public Boolean equal(State compState) {
		if (compState.state.size() != this.state.size()){
			return false;
		}
		for(Map.Entry<String, Object> entry : this.state.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue().toString();
		   	String compValue = compState.devolverValor(key).toString();
		    if (!value.equals(compValue)){
		    	return false;
		    }
		}
		
		return true;
	}	
	
}