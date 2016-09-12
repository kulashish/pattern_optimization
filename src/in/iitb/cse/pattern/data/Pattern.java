package in.iitb.cse.pattern.data;

public class Pattern {
	private int id;
	private String patString;
	private String[] uniqueTokens;
	private int corpusCoverage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPatString() {
		return patString;
	}

	public void setPatString(String patString) {
		this.patString = patString;
	}

	public String[] getUniqueTokens() {
		return uniqueTokens;
	}

	public void setUniqueTokens(String[] uniqueTokens) {
		this.uniqueTokens = uniqueTokens;
	}

	public int getCorpusCoverage() {
		return corpusCoverage;
	}

	public void setCorpusCoverage(int corpusCoverage) {
		this.corpusCoverage = corpusCoverage;
	}

	public void decreaseCoverage(int i) {
		corpusCoverage -= i;
	}

}
