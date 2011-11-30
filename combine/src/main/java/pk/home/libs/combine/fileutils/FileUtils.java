package pk.home.libs.combine.fileutils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;





/**
 * @author kopychenko
 * 
 * Файловые утилиты
 *
 */
public final class FileUtils {
	
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("/yyyy/MM/dd/");
	
	/**
	 * Получить структуру каталогов изходя из текущей даты
	 * в формате /yyyy/MM/dd/
	 * @return
	 */
	public static String getCurentTimeDirsPath(){
		return sdf1.format(new Date());
	}
	
	
	
	/**
	 * Создать структуру каталогов
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void mkDirs(String path) throws Exception{
		(new File(path)).mkdirs();
//		boolean success = (new File(path)).mkdirs();
//		
//		if(!success){
//			throw new Exception("Error! when was creting dirs structute: " + path);
//		}
	}
	
	
	
	
	
	

}
