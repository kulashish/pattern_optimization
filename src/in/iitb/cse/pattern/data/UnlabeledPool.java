package in.iitb.cse.pattern.data;

import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;
import it.uniroma2.sag.kelp.data.example.Example;

public class UnlabeledPool extends DataPool {

	public UnlabeledPool(SimpleDataset dataset) {
		super(dataset);
	}

	public void removeExample(Example e) {
		data.getExamples().remove(e);
	}

}
