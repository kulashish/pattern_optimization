package in.iitb.cse.pattern.data;

import java.util.List;

public class Patterns {

	private List<Pattern> patterns;

	public void addPattern(Pattern pat) {
		patterns.add(pat.getId(), pat);
	}

	public Pattern getPattern(int id) {
		return patterns.get(id);
	}

}
