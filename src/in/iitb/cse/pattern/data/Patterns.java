package in.iitb.cse.pattern.data;

import java.util.HashMap;
import java.util.Map;

public class Patterns {

	private Map<Integer, Pattern> patterns;

	public Patterns() {
		patterns = new HashMap<Integer, Pattern>();
	}

	public void addPattern(Pattern pat) {
		patterns.put(pat.getId(), pat);
	}

	public Pattern getPattern(int id) {
		return patterns.get(id);
	}

}
