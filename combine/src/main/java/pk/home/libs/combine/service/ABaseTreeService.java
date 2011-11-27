package pk.home.libs.combine.service;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pk.home.libs.combine.basic.TreeFunctional;
import pk.home.libs.combine.dao.ABaseTreeDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

public abstract class ABaseTreeService<T extends Object> extends
		ABaseService<T> implements TreeFunctional<T> {

	public abstract ABaseTreeDAO<T> getAbstractBasicTreeDAO();

	/**
	 * Метод группового присвоения родителя
	 * 
	 * Данный метод вынесен на уровень сервиса потому что работа с деревьями
	 * может быть разная, например с деревьями в которых возможно замыкание
	 * ветвей самих на себя
	 * 
	 * @param objects
	 * @param parent
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setParent(List<T> objects, T parent) throws Exception {
		for (T object : objects) {
			getAbstractBasicTreeDAO().setParent(object, parent);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	@Override
	@Transactional
	public List<T> getChildrens(T parent) throws Exception {
		return getChildrens(parent, true, -1, -1, null, SortOrderType.ASC);
	}

	@Override
	@Transactional
	public List<T> getChildrens(T parent,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getChildrens(parent, true, -1, -1, orderByAttribute, sortOrder);
	}

	@Override
	@Transactional
	public List<T> getChildrens(T parent, int firstResult, int maxResults)
			throws Exception {
		return getChildrens(parent, false, firstResult, maxResults, null,
				SortOrderType.ASC);
	}

	@Override
	@Transactional
	public List<T> getChildrens(T parent, boolean all, int firstResult,
			int maxResults, SingularAttribute<T, ?> orderByAttribute,
			SortOrderType sortOrder) throws Exception {
		return getAbstractBasicTreeDAO().getChildrens(parent, all, firstResult,
				maxResults, orderByAttribute, sortOrder);
	}

}
