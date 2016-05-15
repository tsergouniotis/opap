package ts.opap.joker.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "number_frequencies")
@NamedQueries({
		@NamedQuery(name = "NumberFrequency.orderByFrequencies", query = "select f from NumberFrequency f order by f.frequency asc") })
public class NumberFrequency extends AbstractNumberFrequency {

	protected NumberFrequency() {
		super();
	}

	public NumberFrequency(Integer number) {
		super(number);
	}

}
