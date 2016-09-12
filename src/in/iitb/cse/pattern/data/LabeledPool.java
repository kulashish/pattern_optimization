package in.iitb.cse.pattern.data;

import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;
import it.uniroma2.sag.kelp.data.example.Example;

public class LabeledPool extends DataPool {

	private int tokenCoverage = 0;

	public LabeledPool(SimpleDataset dataset) {
		super(dataset);
	}

	public int computeTokenCoverage() {
		for (Example e : data.getExamples())
			updateTokenCoverage(e);
		return tokenCoverage;
	}

	public void addExample(Example e) {
		data.addExample(e);
		updateTokenCoverage(e);
	}

	private void updateTokenCoverage(Example e) {
		int patId = (int) e.getId();
		Pattern pattern = patternCoverageData.getPatterns().getPattern(patId);
		tokenCoverage += pattern.getCorpusCoverage();
		patternCoverageData.updatePatternCover(patId);
	}

}
