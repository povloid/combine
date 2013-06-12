package pk.home.libs.combine.service;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pk.home.libs.combine.basic.TreeFunctional;
import pk.home.libs.combine.dao.ABaseTreeDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * The Class ABaseTreeService.
 *
 * @param <T> the generic type
 * @author Kopychenko Pavel
 * @date Jun 12, 2012
 */
public abstract class ABaseTreeService<T extends Object> extends
		ABaseService<T> implements TreeFunctional<T> {

	/**
	 * Gets the abstract basic tree dao.
	 *
	 * @return the abstract basic tree dao
	 */
	public abstract ABaseTreeDAO<T> getAbstractBasicTreeDAO();

	/**
	 * Метод группового присвоения родителя
	 * 
	 * Данный метод вынесен на уровень сервиса потому что работа с деревьями
	 * может быть разная, например с деревьями в которых возможно замыкание
	 * ветвей самих на себя.
	 *
	 * @param objects the objects
	 * @param parent the parent
	 * @throws Exception the exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setParent(List<T> objects, T parent) throws Exception {
		for (T object : objects) {
			setParent(object, parent);
		}
	}
	
	

	/**
	 * Метод группового присвоения родителя
	 * 
	 * Данный метод вынесен на уровень сервиса потому что работа с деревьями
	 * может быть разная, например с деревьями в которых возможно замыкание
	 * ветвей самих на себя.
	 *
	 * @param objects the objects
	 * @param parent the parent
	 * @throws Exception the exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setParent(T[] objects, T parent) throws Exception {
		for (T object : objects) {
			setParent(object, parent);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	// @Override
	// @Transactional
	// public List<T> getChildrens(T parent) throws Exception {
	// return getChildrens(parent, true, -1, -1, null, SortOrderType.ASC);
	// }
	//
	// @Override
	// @Transactional
	// public List<T> getChildrens(T parent,
	// SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
	// throws Exception {
	// return getChildrens(parent, true, -1, -1, orderByAttribute, sortOrder);
	// }
	//
	// @Override
	// @Transactional
	// public List<T> getChildrens(T parent, int firstResult, int maxResults)
	// throws Exception {
	// return getChildrens(parent, false, firstResult, maxResults, null,
	// SortOrderType.ASC);
	// }
	//
	// @Override
	// @Transactional
	// public List<T> getChildrens(T parent, boolean all, int firstResult,
	// int maxResults, SingularAttribute<T, ?> orderByAttribute,
	// SortOrderType sortOrder) throws Exception {
	// return getAbstractBasicTreeDAO().getChildrens(parent, all, firstResult,
	// maxResults, orderByAttribute, sortOrder);
	// }

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.basic.TreeFunctional#getChildrens(java.lang.Object, javax.persistence.metamodel.SingularAttribute)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_)
			throws Exception {
		return getChildrens(parent, parent_, true, -1, -1, null,
				SortOrderType.ASC);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.basic.TreeFunctional#getChildrens(java.lang.Object, javax.persistence.metamodel.SingularAttribute, javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getChildrens(parent, parent_, true, -1, -1, orderByAttribute,
				sortOrder);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.basic.TreeFunctional#getChildrens(java.lang.Object, javax.persistence.metamodel.SingularAttribute, int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			int firstResult, int maxResults) throws Exception {
		return getChildrens(parent, parent_, false, firstResult, maxResults,
				null, SortOrderType.ASC);
	}
	
	/* (non-Javadoc)
	 * @see pk.home.libs.combine.basic.TreeFunctional#getChildrens(java.lang.Object, javax.persistence.metamodel.SingularAttribute, int, int, javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception{
		return getChildrens(parent, parent_, false, firstResult, maxResults, orderByAttribute, sortOrder);
	}
	

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.basic.TreeFunctional#getChildrens(java.lang.Object, javax.persistence.metamodel.SingularAttribute, boolean, int, int, javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			boolean all, int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getAbstractBasicTreeDAO().getChildrens(parent, parent_, all,
				firstResult, maxResults, orderByAttribute, sortOrder);
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	
	/* (non-Javadoc)
	 * @see pk.home.libs.combine.basic.TreeFunctional#getChildrensCount(java.lang.Object, javax.persistence.metamodel.SingularAttribute)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public long  getChildrensCount(T parent, SingularAttribute<T, ?> parent_)
			throws Exception {
		return getAbstractBasicTreeDAO().getChildrensCount(parent, parent_);
	}
	
	

}
