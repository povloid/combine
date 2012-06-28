package pk.home.libs.combine.web.jsf.flow;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Абстрактный класс - форма отчета
 * 
 * @author povloid
 * 
 */
public abstract class AWFRPTWFControl extends AWFBasicControl implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3745338047513928374L;

	protected final DateFormat dateFormatFileNameTime = new SimpleDateFormat(
			"dd-MM-yyyy_HH_mm_ss", new Locale("ru"));

	protected String purl, filename = "report";

	/**
	 * Результирующий класс
	 * 
	 * @author povloid
	 * 
	 */
	final public class MakeResult {

		private String purl;
		private String filename;

		public MakeResult(String purl, String filename) {
			super();
			this.purl = purl;
			this.filename = filename;
		}

		public String getPurl() {
			return purl;
		}

		public String getFilename() {
			return filename;
		}

	}

	/**
	 * Сформировать отчет
	 * 
	 * @return
	 * @throws Exception
	 */
	public String makeReport() throws Exception {
		try {

			MakeResult mr = _makeReport();

			this.purl = mr.getPurl();
			this.filename = mr.getFilename();

			return "makeReport";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));

			purl = null;

			this.filename = "report";

			throw new Exception(e);
		}
	}

	/**
	 * Выполнить отчет
	 * 
	 * @throws Exception
	 */
	protected abstract MakeResult _makeReport() throws Exception;

	// get's and set's
	// -----------------------------------------------------------------------------------------------------------------

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
