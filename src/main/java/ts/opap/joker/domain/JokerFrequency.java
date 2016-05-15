package ts.opap.joker.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "joker_frequencies")
@NamedQueries({
		@NamedQuery(name = "JokerFrequency.orderByFrequencies", query = "select f from JokerFrequency f order by f.frequency asc") })
public class JokerFrequency extends AbstractNumberFrequency {

	protected JokerFrequency() {
		super();
	}

	public JokerFrequency(Integer number) {
		super(number);
	}

}
