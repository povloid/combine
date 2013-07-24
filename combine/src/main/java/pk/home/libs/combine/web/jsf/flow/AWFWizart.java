package pk.home.libs.combine.web.jsf.flow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public class AWFWizart implements Serializable {

	
	/**
	 * initialize lazy function
	 */
	public void init() {
		try {
			init0();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}
	
	
	/**
	 * initialize
	 * @throws Exception
	 */
	protected void init0() throws Exception{
		
	}
	
	
	
	/**
	 * Find bean for name
	 * 
	 * @param name
	 * @return
	 */
	public Object findBean(String name) {
		ApplicationContext ctx = FacesContextUtils
				.getWebApplicationContext(FacesContext.getCurrentInstance());
		return ctx.getBean(name);
	}
	
	/**
	 * Find bean for name.
	 *
	 * @param <Z> the generic type
	 * @param name the name
	 * @param requiredType the required type
	 * @return the z
	 */
	public <Z> Z findBean(String name, Class<Z> requiredType) {
		ApplicationContext ctx = FacesContextUtils
				.getWebApplicationContext(FacesContext.getCurrentInstance());
		return (Z) ctx.getBean(name, requiredType);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8445761204034590599L;

	/**
	 * Close action
	 * 
	 * @return
	 */
	public String close() {
		try {
			closeImpl();
		} catch (javax.persistence.OptimisticLockException e) {
			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: ",
									"Версия устарела, кто-то уже обновил данную запись. Закройте диалог."));
			return "error";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			return "error";
		}

		return "close";
	}

	/**
	 * Close action implimentation
	 * 
	 * @throws Exception
	 */
	protected void closeImpl() throws Exception {

	}

	/**
	 * Back action
	 * 
	 * @return
	 */
	public String back() {
		try {
			backImpl();
		} catch (javax.persistence.OptimisticLockException e) {
			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: ",
									"Версия устарела, кто-то уже обновил данную запись. Закройте диалог."));
			return "error";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			return "error";
		}

		return "back";
	}

	/**
	 * Back action implimentation
	 * 
	 * @throws Exception
	 */
	protected void backImpl() throws Exception {

	}

	/**
	 * Next action
	 * 
	 * @return
	 */
	public String next() {
		try {
			nextImpl();
		} catch (javax.persistence.OptimisticLockException e) {
			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: ",
									"Версия устарела, кто-то уже обновил данную запись. Закройте диалог."));
			return "error";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			
			return "error";
		}

		return "next";
	}

	/**
	 * Next action implimentation
	 * 
	 * @throws Exception
	 */
	protected void nextImpl() throws Exception {

	}

	/**
	 * Current wizart step
	 */
	private short step = 0;

	/**
	 * last wizart step
	 */
	private short lstep = 0;

	public short getStep() {
		return step;
	}

	public void setStep(short step) {
		this.step = step;
	}

	public short getLstep() {
		return lstep;
	}

	public void setLstep(short lstep) {
		this.lstep = lstep;
	}

}
