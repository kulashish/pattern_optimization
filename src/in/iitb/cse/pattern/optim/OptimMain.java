package in.iitb.cse.pattern.optim;

import in.iitb.cse.pattern.data.DataGen;
import in.iitb.cse.pattern.data.Pattern;
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
			System.out.println("Total tokens in the corpus: " + patCoverage.getCorpusSize());
			for (int i = 0; i < 5; i++) {
				Pattern p = patCoverage.getPatterns().getPattern(i);
				System.out.println("Pattern id: " + p.getId() + " , coverage: " + p.getCorpusCoverage());
			}

			dataGen.generatePools(0.001f); // Split into labeled / unlabeled
											// pools
			System.out.println("Data split into labeled / unlabeled pools");
			System.out.println("Labeled pool summary: " + dataGen.getLabeledPool().summary());
			dataGen.getLabeledPool().setPatternCoverageData(patCoverage);
			int coverage = dataGen.getLabeledPool().computeTokenCoverage();
			System.out.println("Current coverage: " + coverage);
			//
			TreeKernelClassifier classifier = new TreeKernelClassifier();
			System.out.println("Creating kernel matrix with dimension " + numInstances);
			SparseMatrix kernelMatrix = new SparseMatrix(numInstances);
			classifier.setKernelMatrix(kernelMatrix);
			MarginComputer marginComputer = new SimpleMarginComputer(classifier);
			ALSampler sampler = new ALSampler(dataGen, marginComputer, patCoverage, 1.0f);

			for (int i = 0; i < 50; i++) {
				System.out.println("Iteration count: " + i);
				System.out.println("Current coverage: " + dataGen.getLabeledPool().getTokenCoverage());
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
