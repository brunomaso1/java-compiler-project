package behaviour;

import java.util.*;

public class State {
	public final Map<String, Double> variables = new HashMap<String, Double>();
	
	public Double get(String id) {
		return variables.get(id);		
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
}
