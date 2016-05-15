package ts.opap.joker.persistence.repositories;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ts.opap.joker.domain.Draw;

@Stateless
public class DrawRepository extends GenericRepositoryImpl<Draw, Integer> {

	public DrawRepository() {
		super(Draw.class);
	}

	public Integer findLastDrawNumber() {
		TypedQuery<Integer> query = em.createNamedQuery("Draw.lastDrawNumber", Integer.class);
		return query.getSingleResult();
	}

}
