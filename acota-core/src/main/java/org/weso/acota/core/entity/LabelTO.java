package org.weso.acota.core.entity;

/**
 * 
 * @author Jose María Álvarez
 *
 */
public class LabelTO {
	protected String name;
	protected double weight;

	/**
	 * Zero-argument default constructor.
	 */
	public LabelTO() {

	}

	public LabelTO(String label, int weight) {
		super();
		this.name = label;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) weight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabelTO other = (LabelTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LabelTO [name=" + name + ", weight=" + weight + "]";
	}

}
