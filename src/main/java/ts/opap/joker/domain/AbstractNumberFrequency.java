package ts.opap.joker.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractNumberFrequency {

	@Id
	@Column(name = "number")
	private Integer number;

	@Column(name = "frequency")
	private Integer frequency;

	/**
	 * JPA constructor
	 */
	protected AbstractNumberFrequency() {
		super();
	}

	protected AbstractNumberFrequency(Integer number) {
		super();
		this.number = number;
		this.frequency = 0;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public void increaseFreq() {
		this.frequency++;
	}

}
