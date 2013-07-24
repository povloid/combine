package pk.home.libs.combine.web.jsf.flow;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView;

public abstract class AWFBaseLazyLoadTableView<T extends Object> extends
		ABaseLazyLoadTableView<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2683808658504586022L;

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

}
