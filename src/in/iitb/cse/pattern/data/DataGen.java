package in.iitb.cse.pattern.data;

import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;

public class DataGen {

	private String dataPath;
	private SimpleDataset dataset;
	private LabeledPool labeledPool;
	private UnlabeledPool unlabeledPool;

	public DataGen(String path) throws Exception {
		dataPath = path;
		dataset = new SimpleDataset();
		dataset.populate(dataPath);
	}

	public void generatePools(float splitPercent) {
		SimpleDataset[] dataSplits = dataset.splitClassDistributionInvariant(splitPercent);
		labeledPool = new LabeledPool(dataSplits[0]);
		unlabeledPool = new UnlabeledPool(dataSplits[1]);
	}

	public LabeledPool getLabeledPool() {
		return labeledPool;
	}

	public UnlabeledPool getUnlabeledPool() {
		return unlabeledPool;
	}

	public SimpleDataset getDataset() {
		return dataset;
	}

	public static void main(String[] args) {
		String dataPath = "/Users/ashish/Documents/workspace/eclipse/submodular_activelearning/data/KernelDataForPattern";
		try {
			DataGen dataGen = new DataGen(dataPath);
			dataGen.generatePools(0.01f);
			System.out.println(dataGen.getLabeledPool().getData().getExample(1).getId());
			// TreeKernelClassifier classifier = new TreeKernelClassifier();
			System.out.println(dataGen.getUnlabeledPool().getData().getExample(1).getId());
			// classifier.train(labeled);
			// System.out.println(labeled.getNumInstances());
			// System.out.println(classifier.getClassifier().getModel().getNumberOfSupportVectors());

			// SubSetTreeKernel kernel = new SubSetTreeKernel();
			// System.out
			// .println(kernel.kernelComputation((TreeRepresentation)
			// dataset.getExample(0).getRepresentation("0"),
			// (TreeRepresentation)
			// dataset.getExample(1).getRepresentation("0")));
			// System.out
			// .println(kernel.kernelComputation((TreeRepresentation)
			// dataset.getExample(0).getRepresentation("0"),
			// (TreeRepresentation)
			// dataset.getExample(0).getRepresentation("0")));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
