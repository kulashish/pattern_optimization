package in.iitb.cse.pattern.optim;

import it.uniroma2.sag.kelp.data.example.Example;

public abstract class MarginComputer {

	protected TreeKernelClassifier classifier;

	public MarginComputer(TreeKernelClassifier c) {
		classifier = c;
	}

	public abstract float computeMargin(Example x);

}
