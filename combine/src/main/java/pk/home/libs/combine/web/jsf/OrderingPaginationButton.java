package pk.home.libs.combine.web.jsf;

import java.io.Serializable;

public class OrderingPaginationButton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4157145121958884481L;
	
	
	private String caption;
	private String ppage;

	public OrderingPaginationButton(String caption, String ppage) {
		super();
		this.caption = caption;
		this.ppage = ppage;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getPpage() {
		return ppage;
	}

	public void setPpage(String ppage) {
		this.ppage = ppage;
	}

}
