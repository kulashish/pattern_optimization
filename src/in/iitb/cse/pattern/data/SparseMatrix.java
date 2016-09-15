package in.iitb.cse.pattern.data;

import org.apache.commons.math3.linear.OpenMapRealVector;

import in.iitb.cse.pattern.exception.InvalidValueException;

public class SparseMatrix {

	private static final float DEFAULT_VALUE = 0.0f;
	private OpenMapRealVector vector;
	private int dimension;

	public SparseMatrix(int dim) {
		dimension = dim;
		vector = new OpenMapRealVector(dim / 2 * (dim - 1));
		System.out.println("Created sparse vector with dimension - " + vector.getDimension());
	}

	public void set(int r, int c, float val) {
		vector.setEntry(vectorIndex(r, c), val);
	}

	public float get(int r, int c) throws InvalidValueException {
		float val = (float) vector.getEntry(vectorIndex(r, c));
		if (val == DEFAULT_VALUE)
			throw new InvalidValueException("Value does not exist!");
		return val;
	}

	private int vectorIndex(int r, int c) {
		if (c < r) {
			int rCopy = r;
			r = c;
			c = rCopy;
		}
		return r * (dimension - 1) - (r - 1) * r / 2 + c - r - 1;
	}

	public static void main(String[] args) {
		SparseMatrix mat = new SparseMatrix(5);
		mat.set(1, 2, 2.3f);
		try {
			System.out.println(mat.get(1, 2));
			System.out.println(mat.get(1, 3));
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
	}
}
