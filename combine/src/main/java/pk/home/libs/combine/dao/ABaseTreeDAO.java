package pk.home.libs.combine.dao;

import java.util.List;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.transaction.annotation.Transactional;
import pk.home.libs.combine.basic.TreeFunctional;


public abstract class ABaseTreeDAO<T extends Object> extends ABaseDAO<T>
		implements TreeFunctional<T>{
	
	
	@Override
	@Transactional
	public List<T> getChildrens(T parent) throws Exception {
		return getChildrens(parent ,true, -1, -1, null, SortOrderType.ASC);
	}
	
	
	@Override
	@Transactional
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> orderByAttribute,
			SortOrderType sortOrder) throws Exception {
		return getChildrens(parent, true, -1, -1, orderByAttribute, sortOrder);
	}
	

	@Override
	@Transactional
	public List<T> getChildrens(T parent, int firstResult,
			int maxResults) throws Exception {
		return getChildrens(parent ,false, firstResult, maxResults, null, SortOrderType.ASC);
	}

	
	
	
}
