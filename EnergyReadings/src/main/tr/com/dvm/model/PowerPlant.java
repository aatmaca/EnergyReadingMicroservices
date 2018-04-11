package tr.com.dvm.model;

public class PowerPlant {
	public String name;
	public String meter;
	public String gen1min;

	public PowerPlant(String pp, String meter, String gen) {
		this.name = pp;
		this.meter = meter;
		this.gen1min = gen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMeter() {
		return meter;
	}

	public void setMeter(String meter) {
		this.meter = meter;
	}

	public String getGen1min() {
		return gen1min;
	}

	public void setGen1min(String gen1min) {
		this.gen1min = gen1min;
	}

	@Override
	public boolean equals(Object obj) {
		PowerPlant other = (PowerPlant) obj;
		return getName().equals(other.getName()) && getMeter().equals(other.getMeter());
	}

	@Override
	public String toString() {
		return name + ", " + meter;
	}
}