package pk.home.libs.combine.web.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * The tree abstract base class
 * 
 * 
 * 
 * @author povloid
 * 
 * @param <T>
 */
public abstract class ABaseTree<T extends Object> {

	// ----------------------------------------------------------------------------------------------------------------
	// INIT

	/**
	 * View initialisation
	 */
	public void init() {
		try {

			if (this.sKey == null || this.sKey.length() == 0)
				this.selected = null;
			else
				this.selected = findSelectedObject(this.sKey);

			prepereParams();
			aInit();

			// initialize childrens
			if (getABaseLazyLoadTableView() != null)
				getABaseLazyLoadTableView().init();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	/**
	 * View initialisation impl
	 * 
	 * @throws Exception
	 */
	protected abstract void aInit() throws Exception;

	// -- bean params
	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Get the ABaseLazyLoadTableView<T> childrens element
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract ABaseLazyLoadTableView<T> getABaseLazyLoadTableView()
			throws Exception;

	// ----------------------------------------------------------------------------------------------------------------

	// request params
	private Integer prows = 10;
	private Integer ppage = 1;
	private String pcsortOrder;
	private String pcsortField;

	// selected bean parametr
	protected String sKey;
	protected String lastSKey;

	// selected bean parametr
	protected T selected;

	/**
	 * prepere params
	 * 
	 * @throws Exception
	 */
	private void prepereParams() throws Exception {

		if (getABaseLazyLoadTableView() != null) {
			if (this.sKey == null && this.lastSKey == null || this.sKey != null
					&& this.lastSKey != null && this.lastSKey.equals(this.sKey)) {
				System.out.println(">>>>1");

			} else {
				System.out.println(">>>>2");

				prows = 10;
				ppage = 1;
				pcsortOrder = null;
				pcsortField = null;
			}

			getABaseLazyLoadTableView().setPage(ppage);
			getABaseLazyLoadTableView().setRows(prows);
			getABaseLazyLoadTableView().setCsortOrder(pcsortOrder);
			getABaseLazyLoadTableView().setCsortField(pcsortField);

		}

		System.out.println(">>>sKey=" + sKey + "; lastKey=" + lastSKey);

		this.lastSKey = this.sKey;
	}

	/**
	 * will implimented find selected object
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract T findSelectedObject(String sKey) throws Exception;

	// Path
	// ------------------------------------------------------------------------------

	public T[] getPathToSelected() {
		T[] tm = null;

		try {
			tm = agetPathToSelected();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return tm;
	}

	protected abstract T[] agetPathToSelected() throws Exception;

	// ---------------------------------------------------------------------------------------------
	// getters and setters

	public T getSelected() {
		return selected;
	}

	public void setSelected(T selected) {
		this.selected = selected;
	}

	public Integer getProws() {
		return prows;
	}

	public void setProws(Integer prows) {
		this.prows = prows;
	}

	public Integer getPpage() {
		return ppage;
	}

	public void setPpage(Integer ppage) {
		this.ppage = ppage;
	}

	public String getPcsortOrder() {
		return pcsortOrder;
	}

	public void setPcsortOrder(String pcsortOrder) {
		this.pcsortOrder = pcsortOrder;
	}

	public String getPcsortField() {
		return pcsortField;
	}

	public void setPcsortField(String pcsortField) {
		this.pcsortField = pcsortField;
	}

	public String getsKey() {
		return sKey;
	}

	public void setsKey(String sKey) {
		this.sKey = sKey;
	}

}
