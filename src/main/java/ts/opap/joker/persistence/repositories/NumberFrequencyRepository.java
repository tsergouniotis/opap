package ts.opap.joker.persistence.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ts.opap.joker.domain.NumberFrequency;

@Stateless
public class NumberFrequencyRepository extends GenericRepositoryImpl<NumberFrequency, Integer> {

	public NumberFrequencyRepository() {
		super(NumberFrequency.class);
	}

	public List<NumberFrequency> findNumberFrequenciesOrdered() {
		TypedQuery<NumberFrequency> q = em.createNamedQuery("NumberFrequency.orderByFrequencies",
				NumberFrequency.class);
		return q.getResultList();
	}

}
