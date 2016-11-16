/**
 * Universidad Catolica - Compiladores - Obligatorio.
 */
package ast;

import java.util.*;

import behaviour.*;

import java.awt.List;
import java.lang.reflect.Array; // Este arreglo se utiliza para guardar en el indice 0 el tipo y en el 1 si esta iniciada la variable o no.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChequearEstado {
	private HashMap <String, ParFunc> funciones = new HashMap<String, ParFunc>();
	private HashMap <String, Par> state = new HashMap<String, Par>();
	
	public Par devolverValor(String variableName) {
		return state.get(variableName);
	}
	public ParFunc devolverValorFunc(String funcName) {
		return funciones.get(funcName);
	}
	/*public HashMap<String, Par> getState() {
		return state;
	}*/
	public void borrar(){
		state.clear();
	}
	public void remover(String nombre) {
		state.remove(nombre);
	}
	public void agregar(String nombre, Par tipo) {
		state.put(nombre, tipo);
	}
	public void agregarFunc(String nombre, ParFunc par) {
		funciones.put(nombre, par);
	}
	
	public HashMap<String, ParFunc> getFunciones() {
		return funciones;
	}
	public void setFunciones(HashMap<String, ParFunc> funciones) {
		this.funciones = funciones;
	}
	public boolean tieneVariables() {
		if (state.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public ArrayList<String> devolverClaves() {
	   ArrayList<String> l = new ArrayList<String>(state.keySet());
	   return l;
	}
	
	public void print() {
		
		for(Map.Entry<String, Par> entry : this.state.entrySet()) {
		    String key = entry.getKey();
		    Par value = entry.getValue();
		 
		    System.out.println("key :"+key+" "+value.toString());
		}
	}
	
	public Boolean equal(ChequearEstado compState) {
		if (compState.state.size() != this.state.size()){
			return false;
		}
		for(Map.Entry<String, Par> entry : this.state.entrySet()) {
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