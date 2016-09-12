package in.iitb.cse.pattern.optim;

import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.predictionfunction.model.SupportVector;

public class SimpleMarginComputer {

	private TreeKernelClassifier classifier;

	public SimpleMarginComputer(TreeKernelClassifier classifier) {
		this.classifier = classifier;
	}

	public float computeMargin(Example x) {
		float margin = 0.0f;
		for (SupportVector sv : classifier.getSupportVectors()) 
			margin += sv.getWeight() * classifier.computeKernelValue(x, sv.getInstance());
		return Math.abs(margin);
	}
}
