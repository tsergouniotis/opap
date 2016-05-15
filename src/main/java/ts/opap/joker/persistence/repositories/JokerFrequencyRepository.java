package ts.opap.joker.persistence.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ts.opap.joker.domain.JokerFrequency;

@Stateless
public class JokerFrequencyRepository extends GenericRepositoryImpl<JokerFrequency, Integer> {

	public JokerFrequencyRepository() {
		super(JokerFrequency.class);
	}

	public List<JokerFrequency> findNumberFrequenciesOrdered() {
		TypedQuery<JokerFrequency> q = em.createNamedQuery("JokerFrequency.orderByFrequencies", JokerFrequency.class);
		return q.getResultList();
	}

}
