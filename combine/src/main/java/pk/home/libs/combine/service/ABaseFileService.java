package pk.home.libs.combine.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;



/**
 * The Class ABaseFileService.
 *
 * @author povloid
 */
public abstract class ABaseFileService {

	
	/**
	 * Gets the base dir path.
	 *
	 * @return the base dir path
	 */
	public abstract String getBaseDirPath();
	
	/**
	 * Gets the buffer size.
	 *
	 * @return the buffer size
	 */
	public abstract int getBufferSize();
	
	/**
	 * Gets the dir path.
	 *
	 * @return the dir path
	 * @throws Exception the exception
	 */
	public abstract String makeDirsStructurePath() throws Exception;
	
	/**
	 * Create dirs.
	 *
	 * @param path the path
	 * @throws Exception the exception
	 */
	public static void mkDirs(String path) throws Exception{
		(new File(path)).mkdirs();
	}
	
	
	/**
	 * Save to file.
	 *
	 * @param inputStream the input stream
	 * @param fileName the file name
	 * @return the string
	 * @throws Exception the exception
	 */
	public String saveToFile(InputStream inputStream, String fileName )
			throws Exception {
		String absoluteFilePath = null;
		FileOutputStream fileOutputStream = null;

		try {
			
			String absDirPath = getBaseDirPath() + "//" + makeDirsStructurePath(); 
			
			mkDirs(absDirPath);

			absoluteFilePath = absDirPath + "//" + fileName;
			
			File result = new File(absDirPath + fileName);

			fileOutputStream = new FileOutputStream(result);
			byte[] buffer = new byte[getBufferSize()];
			int bulk;
			while (true) {
				bulk = inputStream.read(buffer);
				if (bulk < 0) {
					break;
				}
				fileOutputStream.write(buffer, 0, bulk);
				fileOutputStream.flush();
			}

		} catch (Exception ex) {
			if (fileOutputStream != null)
				fileOutputStream.close();

			if (inputStream != null)
				inputStream.close();
			
			throw new Exception(ex);

		} finally {
			fileOutputStream.close();
			inputStream.close();
		}

		return absoluteFilePath;
	}
}
