package ast;

import java.util.*;

public class Estado {
	public final Map<String, Object> estado = new HashMap<String, Object>();
	public final Map<String, ParLista> estadoLista = new HashMap<String, ParLista>();
	
	public Object get(String variableName) {
		if (estado.get(variableName)==null) {
			Errores.exceptionList.add(new Errores("Variable \"" + variableName + "\" no inizializada"));
		}
		return estado.get(variableName);
	}
	public void remove(String nombre) {
		estado.remove(nombre);
	}
	public void set(String nombre, Object valor) {
		estado.put(nombre, valor);
	}
	
	public void setLista(String nombre, ParLista valor) {
		estadoLista.put(nombre, valor);
	}
	
	public void print() {
		
		for(Map.Entry<String, Object> entry : this.estado.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue().toString();
		 
		    System.out.println("key :"+key+" value:"+value);
		    // do what you have to do here
		    // In your case, an other loop.
		}
	}
	
	public Boolean equal(Estado compState) {
		if (compState.estado.size() != this.estado.size()){
			return false;
		}
		for(Map.Entry<String, Object> entry : this.estado.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue().toString();
		   	String compValue = compState.get(key).toString();
		    if (!value.equals(compValue)){
		    	return false;
		    }
		}
		
		return true;
	}
	
	@Override public Estado clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Estado)super.clone();
	}
	
	public static Estado intersect(final Estado first, final Estado second) {
		Estado result = new Estado();
		for (String id : first.estado.keySet()) {
			Object firstEntry = first.estado.get(id);
			Object secondEntry = second.estado.get(id);
			
			boolean sameType = firstEntry.equals(secondEntry);
			
			if (sameType) {
				result.estado.put(id, firstEntry);
			}
		}
		return result;
	}
	/*public Double get(String id) {
		return variables.get(id);		
	}
	
	public Object devolverValor(String variableName) {
		if (estado.get(variableName)==null) {
			Errores.exceptionList.add(new Errores("Variable \"" + variableName + "\" no inizializada"));
		}
		return estado.get(variableName);
	}
	
	public Double set(String id, double value) {
		return variables.put(id, value);
	}
	
	public String toString() {
		List<String> ids = new ArrayList<String>(variables.keySet());
		ids.sort(new Comparator<String>() {
			 public int compare(String arg0, String arg1) {
				return arg0.compareToIgnoreCase(arg1);
			}
		});
		StringBuilder buffer = new StringBuilder();
		int i = 0;
		for (String id : ids) {
			buffer.append(i++ > 0 ? ", " : "[").append(id).append("=").append(variables.get(id));
		}
		return buffer.append("]").toString();
	}
	
	@Override
	public Estado clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Estado)super.clone();
	}
	
	public static Estado intersect(final Estado first, final Estado second) {
		Estado result = new Estado();
		for (String id : first.variables.keySet()) {
			Double firstEntry = first.variables.get(id);
			Double secondEntry = second.variables.get(id);
			
			boolean sameType = firstEntry.equals(secondEntry);
			
			if (sameType) {
				result.variables.put(id, firstEntry);
			}
		}
		return result;
	}	*/
}
