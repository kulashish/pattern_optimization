package in.iitb.cse.pattern.data;

import java.util.ArrayList;
import java.util.List;

import in.iitb.cse.pattern.optim.PatternConstants;
import it.uniroma2.sag.kelp.data.dataset.Dataset;
import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;
import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.data.label.StringLabel;

public class DataPool {

	protected SimpleDataset data;
	protected StringLabel positiveLabel;
	protected PatternCoverageData patternCoverageData;

	protected DataPool(SimpleDataset data) {
		this(data, PatternConstants.DATA_LABEL_POS);
	}

	protected DataPool(SimpleDataset data, String pLabel) {
		this.data = data;
		positiveLabel = new StringLabel(pLabel);
	}

	public Dataset getPosData() throws InstantiationException, IllegalAccessException {
		List<Label> posLabel = new ArrayList<Label>();
		posLabel.add(positiveLabel);
		return SimpleDataset.extractExamplesOfClasses(data, posLabel);
	}

	public SimpleDataset getData() {
		return data;
	}

	public int getNumPosInstances() {
		return data.getNumberOfPositiveExamples(positiveLabel);
	}

	public int getNumNegInstances() {
		return data.getNumberOfNegativeExamples(positiveLabel);
	}

	public int getNumInstances() {
		return data.getNumberOfExamples();
	}

	public void setPatternCoverageData(PatternCoverageData patternCoverageData) {
		this.patternCoverageData = patternCoverageData;
	}

}
