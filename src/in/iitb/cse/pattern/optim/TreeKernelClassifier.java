package in.iitb.cse.pattern.optim;

import java.util.List;

import in.iitb.cse.pattern.data.LabeledPool;
import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.label.StringLabel;
import it.uniroma2.sag.kelp.data.representation.tree.TreeRepresentation;
import it.uniroma2.sag.kelp.kernel.DirectKernel;
import it.uniroma2.sag.kelp.kernel.Kernel;
import it.uniroma2.sag.kelp.kernel.tree.SubSetTreeKernel;
import it.uniroma2.sag.kelp.kernel.tree.SubTreeKernel;
import it.uniroma2.sag.kelp.learningalgorithm.classification.libsvm.BinaryCSvmClassification;
import it.uniroma2.sag.kelp.predictionfunction.classifier.BinaryKernelMachineClassifier;
import it.uniroma2.sag.kelp.predictionfunction.model.SupportVector;

public class TreeKernelClassifier {

	private TreeKernelType treeKernelType;
	private DirectKernel<TreeRepresentation> treeKernel;
	private BinaryCSvmClassification svmSolver;
	private BinaryKernelMachineClassifier classifier;

	public TreeKernelClassifier() {
		this(TreeKernelType.SUBSET);
	}

	public TreeKernelClassifier(TreeKernelType type) {
		treeKernelType = type;
		switch (type) {
		case SUB:
			treeKernel = new SubTreeKernel(PatternConstants.SUBSET_TREEKERNEL_LAMBDA,
					PatternConstants.TREEKERNEL_REPRESENTATION);
			break;
		case SUBSET:
			treeKernel = new SubSetTreeKernel(PatternConstants.SUBSET_TREEKERNEL_LAMBDA,
					PatternConstants.TREEKERNEL_REPRESENTATION);
		}
		svmSolver = new BinaryCSvmClassification();
		svmSolver.setCp(PatternConstants.PARAM_REG_POS);
		svmSolver.setCn(PatternConstants.PARAM_REG_NEG);
		svmSolver.setKernel(treeKernel);
		svmSolver.setLabel(new StringLabel(PatternConstants.DATA_LABEL_POS));
	}

	public void train(LabeledPool pool) {
		svmSolver.learn(pool.getData());
		classifier = svmSolver.getPredictionFunction();
	}

	public BinaryKernelMachineClassifier getClassifier() {
		return classifier;
	}

	public List<SupportVector> getSupportVectors() {
		return classifier.getModel().getSupportVectors();
	}

	public DirectKernel<TreeRepresentation> getTreeKernel() {
		return treeKernel;
	}

	public float computeKernelValue(Example a, Example b) {
		return treeKernel.kernelComputation(
				(TreeRepresentation) a.getRepresentation(PatternConstants.TREEKERNEL_REPRESENTATION),
				(TreeRepresentation) b.getRepresentation(PatternConstants.TREEKERNEL_REPRESENTATION));
	}

}
