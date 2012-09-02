package pk.home.libs.combine.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.basic.TreeFunctional;

public abstract class ABaseTreeDAO<T extends Object> extends ABaseDAO<T>
		implements TreeFunctional<T> {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_)
			throws Exception {
		return getChildrens(parent, parent_, true, -1, -1, null,
				SortOrderType.ASC);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getChildrens(parent, parent_, true, -1, -1, orderByAttribute,
				sortOrder);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			int firstResult, int maxResults) throws Exception {
		return getChildrens(parent, parent_, false, firstResult, maxResults,
				null, SortOrderType.ASC);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getChildrens(parent, parent_, false, firstResult, maxResults,
				orderByAttribute, sortOrder);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			boolean all, int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); // Только
																		// так
																		// заработало
		CriteriaQuery<T> cq = cb.createQuery(getTClass());
		Root<T> t = cq.from(getTClass());

		// parent param ---------------------------------------

		Path<?> parentParam = t.get(parent_);
		// Expression<T> parentParam = cb.parameter(getTClass());
		if (parent == null) {
			cq.where(cb.isNull(parentParam));
		} else {
			cq.where(cb.equal(parentParam, parent));
		}

		return getAllEntities(all, firstResult, maxResults, orderByAttribute,
				sortOrder, cb, cq, t);
	}

	// ----------------------------------------------------------------------------------------------------------------
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public long getChildrensCount(T parent, SingularAttribute<T, ?> parent_)
			throws Exception {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); // Только
		CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		Root<T> rt = cq.from(getTClass());

		Path<?> parentParam = rt.get(parent_);
		if (parent == null) {
			cq.where(cb.isNull(parentParam));
		} else {
			cq.where(cb.equal(parentParam, parent));
		}

		cq.select(getEntityManager().getCriteriaBuilder().count(rt));

		return ((Long) getSinleResult(cq)).longValue();
	}

}
