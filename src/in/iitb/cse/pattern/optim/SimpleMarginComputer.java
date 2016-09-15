package in.iitb.cse.pattern.optim;

import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.predictionfunction.model.SupportVector;

public class SimpleMarginComputer extends MarginComputer {

	public SimpleMarginComputer(TreeKernelClassifier classifier) {
		super(classifier);
	}

	public float computeMargin(Example x) {
		float margin = 0.0f;
		for (SupportVector sv : classifier.getSupportVectors())
			margin += sv.getWeight() * classifier.computeKernelValue(x, sv.getInstance());
//		return 1 / Math.abs(classifier.getClassifier().predict(x).getScore(classifier.getSvmSolver().getLabel()));
		return 1 / Math.abs(margin);
	}
}
