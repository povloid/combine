package pk.home.libs.combine.fileutils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Specified file utilites
 * 
 * @param <T> the generic type
 * @author Kopychenko Pavel
 * @date Jun 12, 2012
 */
public final class FileUtils {
	
	private static final SimpleDateFormat DATA_DIR_FORMAT = new SimpleDateFormat("/yyyy/MM/dd/");
	
	/**
	 * Creating file structure
	 * for next format /yyyy/MM/dd/
	 * @return
	 */
	public static String getCurentTimeDirsPath(){
		return DATA_DIR_FORMAT.format(new Date());
	}
	
	/**
	 * Create dirs
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void mkDirs(String path) throws Exception{
		(new File(path)).mkdirs();
	}
}
