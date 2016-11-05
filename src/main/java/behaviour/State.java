package behaviour;

import java.util.*;

public class State {
	public final Map<String, Double> variables = new HashMap<String, Double>();
	//public final Map<String, Double> parametros = new HashMap<String, Double>();
	
	public Double get(String id) {
		return variables.get(id);		
	}
	
	/*public Double getParam(String id) {
		return parametros.get(id);		
	}*/
	
	public Double set(String id, double value) {
		return variables.put(id, value);
	}
	
	/*public Double setParam(String id, double value) {
		return parametros.put(id, value);
	}*/
	
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
	public State clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (State)super.clone();
	}
	
	public static State intersect(final State first, final State second) {
		State result = new State();
		for (String id : first.variables.keySet()) {
			Double firstEntry = first.variables.get(id);
			Double secondEntry = second.variables.get(id);
			
			boolean sameType = firstEntry.equals(secondEntry);
			
			if (sameType) {
				result.variables.put(id, firstEntry);
			}
		}
		return result;
	}	
}
