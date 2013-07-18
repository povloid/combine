package pk.home.libs.combine.beanutils;

import java.io.Serializable;

/**
 * Простая сущьность для передачи древовидных списков
 * 
 * @author Kopychenko Pavel - 06.11.2012 20:38:23
 * 
 * @param <T>
 */
public abstract class SimpleTreeRowObject<T extends Object> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6244497217347766565L;

	private Long id;
	private Long parentId;
	private String label;
	private String description;

	public SimpleTreeRowObject() {
		super();
	}

	public SimpleTreeRowObject(T entity) throws Exception {
		super();

		this.id = parseId(entity);
		this.parentId = parseParentId(entity);
		this.label = parseLabel(entity);
		this.description = parseDescription(entity);
	}

	protected abstract Long parseId(T entity) throws Exception;

	protected abstract Long parseParentId(T entity) throws Exception;

	protected abstract String parseLabel(T entity) throws Exception;

	protected abstract String parseDescription(T entity) throws Exception;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
