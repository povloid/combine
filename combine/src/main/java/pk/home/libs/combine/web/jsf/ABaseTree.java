package pk.home.libs.combine.web.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private Map<T, Object> multiselected = new HashMap<T, Object>();
    private List<T> buffer = new ArrayList<T>();

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
	
	// buffer ------------------------
	
	/**
	 * Copy selected rows to buffer
	 */
	public void copyMultiselectedToBuffer(){
		buffer.clear();
		
		for(T t: multiselected.keySet()){
			////System.out.println(">>>>>" + t + " is selected - " + multiselected.get(t).getClass());
			
			Object val = multiselected.get(t);
			Boolean bvalue = false;
			
			if(val instanceof String){
				////System.out.println(">>>>>value is String");
				bvalue = ((String) val).trim().toLowerCase().equals("true");
			} else if (val instanceof Boolean){
				////System.out.println(">>>>>value is Boolean");
				bvalue = (Boolean) val;
			}
			
			if(bvalue)
				buffer.add(t);
		}
		
		multiselected.clear();
	}
	
	
	/**
	 * Set new parent action
	 */
	public void setNewParent(){
		try {
			
			if(buffer != null){
				asetNewParent(buffer);
				buffer.clear();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}
	
	/**
	 * Childern impliments set new parent action
	 * @throws Exception
	 */
	protected abstract void asetNewParent(List<T> buffer) throws Exception;
	
	
	
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

	public Map<T, Object> getMultiselected() {
		return multiselected;
	}

	public void setMultiselected(Map<T, Object> multiselected) {
		this.multiselected = multiselected;
	}

	public List<T> getBuffer() {
		return buffer;
	}

	public void setBuffer(List<T> buffer) {
		this.buffer = buffer;
	}

	
	
	
	
	
	
}
