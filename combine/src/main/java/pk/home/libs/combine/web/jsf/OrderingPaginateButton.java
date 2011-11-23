package pk.home.libs.combine.web.jsf;

public class OrderingPaginateButton {

	private String caption;
	private String ppage;

	public OrderingPaginateButton(String caption, String ppage) {
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
