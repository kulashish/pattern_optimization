package in.iitb.cse.pattern.optim;

import in.iitb.cse.pattern.data.DataGen;
import in.iitb.cse.pattern.data.PatternCoverageData;
import it.uniroma2.sag.kelp.data.example.Example;

public class ALSampler {

	private DataGen dataPools;
	private MarginComputer marginComputer;
	private PatternCoverageData coverageData;
	private float tradeoff;

	public ALSampler(DataGen pools, MarginComputer marginComputer, PatternCoverageData covData, float alpha) {
		dataPools = pools;
		this.marginComputer = marginComputer;
		coverageData = covData;
		tradeoff = alpha;
	}

	public Example sampleNext() {
		Example optimExample = null;
		float margin = 0.0f;
		int coverage = 0;
		float optimScore = 0.0f;
		float score = 0.0f;
		for (Example e : dataPools.getUnlabeledPool().getData().getExamples()) {
			margin = marginComputer.computeMargin(e);
			coverage = coverageData.getPatterns().getPattern((int) e.getId()).getCorpusCoverage();
			score = margin + tradeoff * coverage;
			if (score > optimScore) {
				optimScore = score;
				optimExample = e;
			}
		}
		return optimExample;
	}
}
