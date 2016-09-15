package in.iitb.cse.pattern.optim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import in.iitb.cse.pattern.data.DataGen;
import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;

public class TreeKernelComputer {

	private BufferedWriter writer;
	private SimpleDataset dataset;
	private TreeKernelClassifier classifier;

	public TreeKernelComputer(String dataFile, String kernelFile) throws Exception {
		DataGen data = new DataGen(dataFile);
		dataset = data.getDataset();
		writer = new BufferedWriter(new FileWriter(kernelFile));
		classifier = new TreeKernelClassifier();
	}

	public void computeKernel() throws IOException {
		int max = dataset.getNumberOfExamples();
		StringBuilder rowBuilder = null;
		for (int i = 0; i < max; i++) {
			rowBuilder = new StringBuilder();
			for (int j = i + 1; j < max; j++)
				rowBuilder.append(classifier.computeKernelValue(dataset.getExample(i), dataset.getExample(j)))
						.append(" ");
			writeRow(rowBuilder.substring(0, rowBuilder.length() - 1));
		}
		writer.flush();
		writer.close();
	}

	private void writeRow(String kernelRow) throws IOException {
		writer.write(kernelRow);
		writer.newLine();
	}

	public static void main(String[] args) {
		try {
			TreeKernelComputer kernelComputer = new TreeKernelComputer(
					"/Users/ashish/Documents/workspace/eclipse/submodular_activelearning/data/KernelDataForPattern",
					"/Users/ashish/Documents/workspace/eclipse/pattern_optimization/data/kernel_data");
			long startTime = System.currentTimeMillis();
			kernelComputer.computeKernel();
			long endTime = System.currentTimeMillis();
			System.out.println("Elapsed time: " + (endTime - startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
