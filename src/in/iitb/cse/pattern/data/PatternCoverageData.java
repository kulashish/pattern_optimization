package in.iitb.cse.pattern.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternCoverageData {

	private String dataPath;
	private Patterns patterns;
	private CorpusTokens corpusTokens;
	private Map<String, List<Integer>> tokenPatternsMap;

	public PatternCoverageData() {
		patterns = new Patterns();
		corpusTokens = new CorpusTokens();
		tokenPatternsMap = new HashMap<String, List<Integer>>();
	}

	public Patterns getPatterns() {
		return patterns;
	}

	public void loadFromFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		while (null != (line = reader.readLine())) {
			Pattern pat = new Pattern();
			pat.setId(parsePatternLine(line) - 1);
			String[] tokensSet = parseTokensLine(reader.readLine());
			pat.setUniqueTokens(tokensSet);
			pat.setCorpusCoverage(tokensSet.length);
			patterns.addPattern(pat);
			corpusTokens.addTokens(tokensSet);
			addPatternCover(pat.getId(), tokensSet);
		}
		reader.close();
	}

	public void updatePatternCover(int patId) {
		Pattern pattern = patterns.getPattern(patId);
		for (String token : pattern.getUniqueTokens()) {
			for (int id : tokenPatternsMap.get(token))
				patterns.getPattern(id).decreaseCoverage(1);
		}
	}

	private void addPatternCover(int patId, String[] tokensSet) {
		for (String token : tokensSet) {
			List<Integer> patList = tokenPatternsMap.containsKey(token) ? tokenPatternsMap.get(token)
					: new ArrayList<Integer>();
			patList.add(patId);
			tokenPatternsMap.put(token, patList);
		}
	}

	private String[] parseTokensLine(String tokensLine) {
		// remove enclosing square braces
		tokensLine = tokensLine.substring(1, tokensLine.length() - 1);
		// remove single quotes
		tokensLine = tokensLine.replaceAll("'", "");
		return tokensLine.split(",");
	}

	private int parsePatternLine(String line) {
		return Integer.parseInt(line.substring(0, line.indexOf('-')));
	}
}
