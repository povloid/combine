package pk.home.libs.combine.service;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The Class DefaultPathDateFileService.
 */
public class DefaultPathDateFileService extends ABaseFileService {

	
	/** The Constant DEFAULT_PATH_FROM_DATE_FORMAT. */
	public static final String DEFAULT_PATH_FROM_DATE_FORMAT = "/yyyy/MM/dd/";
	
	/** The Buffer size. */
	int BufferSize = 512;
	
	/** The base dir path. */
	private String baseDirPath;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_PATH_FROM_DATE_FORMAT);

	/**
	 * Instantiates a new default path date file service.
	 */
	public DefaultPathDateFileService() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseFileService#makeDirsStructurePath()
	 */
	@Override
	public String makeDirsStructurePath() throws Exception {
		return simpleDateFormat.format(new Date());
	}

	@Override
	public String getBaseDirPath() {
		return this.baseDirPath;
	}

	@Override
	public int getBufferSize() {
		return this.BufferSize;
	}

	public void setBufferSize(int bufferSize) {
		BufferSize = bufferSize;
	}

	public void setBaseDirPath(String baseDirPath) {
		this.baseDirPath = baseDirPath;
	}

}
