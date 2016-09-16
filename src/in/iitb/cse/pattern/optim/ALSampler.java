package in.iitb.cse.pattern.optim;

import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

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
		List<Example> unlabledExamples = dataPools.getUnlabeledPool().getData().getExamples();
		RealVector marginVector = new ArrayRealVector(unlabledExamples.size());
		RealVector coverageVector = new ArrayRealVector(unlabledExamples.size());
		RealVector scoreVector = new ArrayRealVector(unlabledExamples.size());

		for (int eIndex = 0; eIndex < unlabledExamples.size(); eIndex++) {
			Example e = unlabledExamples.get(eIndex);
			marginVector.setEntry(eIndex, marginComputer.computeMargin(e));
			coverageVector.setEntry(eIndex, coverageData.getPatterns().getPattern((int) e.getId()).getCorpusCoverage());
		}
		marginVector = scale(marginVector);
		coverageVector = scale(coverageVector);
		scoreVector = marginVector.add(coverageVector.mapMultiply(tradeoff));
		return unlabledExamples.get(scoreVector.getMaxIndex());
	}

	private RealVector scale(RealVector v) {
		double min = v.getMinValue();
		double scaleFactor = v.getMaxValue() - v.getMinValue();
		return v.mapSubtract(min).mapDivide(scaleFactor);
	}
}
