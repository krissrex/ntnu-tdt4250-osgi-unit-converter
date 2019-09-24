package no.ntnu.tdt4250.oving2.conversion;

import java.util.Objects;

/**
 * @author krire
 *
 */
public class Conversion {
	private String from;
	private String to;

	public String from() {
		return from;
	}

	public String to() {
		return to;
	}

	public static Conversion from(String from, String to) {
		if (from == null || to == null) {
			throw new IllegalArgumentException("To and from can not be null");
		}
		return new Conversion(from, to);
	}
	
	private Conversion(String from, String to) {
		this.to = to;
		this.from = from;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conversion other = (Conversion) obj;
		return Objects.equals(from, other.from) && Objects.equals(to, other.to);
	}

	@Override
	public String toString() {
		return "Conversion [from=" + from + ", to=" + to + "]";
	}

	
	
}