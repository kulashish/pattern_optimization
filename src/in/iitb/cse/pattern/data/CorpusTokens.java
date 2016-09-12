package in.iitb.cse.pattern.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CorpusTokens {

	private Set<String> tokens;

	public CorpusTokens() {
		tokens = new HashSet<String>();
	}

	public void addToken(String token) {
		tokens.add(token);
	}

	public void addTokens(Set<String> tokenSet) {
		tokens.addAll(tokenSet);
	}

	public int size() {
		return tokens.size();
	}

	public void addTokens(String[] tokensSet) {
		tokens.addAll(Arrays.asList(tokensSet));
	}

}
