package in.iitb.cse.pattern.optim;

import in.iitb.cse.pattern.data.DataGen;
import in.iitb.cse.pattern.data.PatternCoverageData;
import in.iitb.cse.pattern.data.SparseMatrix;
import it.uniroma2.sag.kelp.data.example.Example;

public class OptimMain {

	public static void main(String[] args) {
		try {
			// Load tree kernel input data
			System.out.println("Loading tree kernel input data...");
			DataGen dataGen = new DataGen(
					"/Users/ashish/Documents/workspace/eclipse/submodular_activelearning/data/KernelDataForPattern");
			int numInstances = dataGen.getDataset().getNumberOfExamples();
			PatternCoverageData patCoverage = new PatternCoverageData();
			// Load patterns-tokens data and compute coverage
			System.out.println("Loading patterns coverage data...");
			patCoverage.loadFromFile(
					"/Users/ashish/Documents/workspace/eclipse/pattern_optimization/data/pat_instances.txt");

			dataGen.generatePools(0.001f); // Split into labeled / unlabeled
											// pools
			System.out.println("Data split into labeled / unlabeled pools");
			System.out.println("Labeled pool has " + dataGen.getLabeledPool().getNumInstances() + " instances");
			dataGen.getLabeledPool().setPatternCoverageData(patCoverage);
			int coverage = dataGen.getLabeledPool().computeTokenCoverage();

			TreeKernelClassifier classifier = new TreeKernelClassifier();
			System.out.println("Creating kernel matrix with dimension " + numInstances);
			SparseMatrix kernelMatrix = new SparseMatrix(numInstances);
			classifier.setKernelMatrix(kernelMatrix);
			MarginComputer marginComputer = new SimpleMarginComputer(classifier);
			ALSampler sampler = new ALSampler(dataGen, marginComputer, patCoverage, 0.5f);

			for (int i = 0; i < 5; i++) {
				System.out.println("Training classifier...");
				long start = System.currentTimeMillis();
				classifier.train(dataGen.getLabeledPool());
				System.out.println("Time for training : " + ((System.currentTimeMillis() - start) / 1000) + " seconds");
				System.out.println("Sampling next...");
				start = System.currentTimeMillis();
				Example optimE = sampler.sampleNext();
				System.out.println("Time for sampling: " + ((System.currentTimeMillis() - start) / 1000) + " seconds");
				System.out.println("Sampled pattern with id: " + optimE.getId());
				dataGen.getLabeledPool().addExample(optimE);
				dataGen.getUnlabeledPool().removeExample(optimE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
