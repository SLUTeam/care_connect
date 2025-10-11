package com.app.util.file;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.app.util.TextUtil;
import com.app.util.exception.ExceptionProcess;

public class FileUtility {

	public static final String TRACE_ID	= FileUtility.class.getName();

	public static String FILE_EXTENSION_JPG	= "jpg";

	public static boolean isExist(String file) throws Exception {

		try {

			// For use in web app with jar in properties
			/*if (FileUtility.class.getResourceAsStream(file) == null) {
				return false;
			}*/

			// for use when have to access properties file in jar
			if (FileUtility.class.getClassLoader().getResourceAsStream(file) == null) {
				return false;
			}

			return true;
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		}
	}

	/*
	 * Name : only file name
	 * imagerStr : Base64 encoded image as String
	 * filetype : type of image file like jpg, png, gif etc
	 * location : location where to save file example C:/Data/Photos
	 */
	public static boolean writeFile(String name, String imageStr, String fileType, String locationPath) throws Exception {
		InputStream		in					= null;
		BufferedImage	bufferedImage		= null;
		File			outputFile			= null;
		byte[]			imageByte			= null;
		String			encodImgStr			= null;
		String			fullPath			= null;
		boolean			success				= false;

		try {

			encodImgStr		= imageStr.substring(imageStr.indexOf(",") + 1);
			
			//imageByte		= ByteConvertor.base64toByteArray(encodImgStr);
			
			in				= new ByteArrayInputStream(imageByte);
			bufferedImage	= ImageIO.read(in);
			
			fullPath		= locationPath + name + "." + fileType;
			
			outputFile		= new File(fullPath);

			if (!outputFile.exists()) {
				outputFile.mkdirs();
			}

			success		= ImageIO.write(bufferedImage, fileType, new File(fullPath));

			return success;
		} catch (IOException e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			if (in != null) {
				in.close();
			}
			in					= null;
			bufferedImage		= null;
			outputFile			= null;
			imageByte			= null;
			encodImgStr			= null;
			fullPath			= null;
			success				= false;
		}
	}
	
	public static String readFile(String fileName) throws Exception {
		String			content = null;
		InputStream		in 		= null;
		
		 try {
			 if(isExist(fileName)) { 
				 in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
					
					if(in == null) {
						in = PropertiesUtils.class.getResourceAsStream(fileName);
					}
					
					if(in != null) {
						content = read(in);
						content = content.trim();
					}
			 }
			 
			 return content;
		 } catch (Exception e) {
			 throw ExceptionProcess.execute(e, TRACE_ID);
		 } finally {
			 content	= null;
		 }
	}
	
	public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining(""));
        }
    }
	
	public static String readFile(String name, String fileType, String locationPath) throws Exception {
		BufferedImage			bufferedImage		= null;
		File					inputFile			= null;
		byte[]					imageByte			= null;
		String					fullPath			= null;
		StringBuffer			imageStr			= null;
		ByteArrayOutputStream	outputStream		= null;

		try {

			fullPath		= locationPath + name + "." + fileType;

			inputFile		= new File(fullPath);

			if (!inputFile.exists()) {
				return null;
			}

			bufferedImage	= ImageIO.read(inputFile);

			outputStream	= new ByteArrayOutputStream();

			ImageIO.write(bufferedImage, fileType, outputStream);

			outputStream.flush();

			imageByte	= outputStream.toByteArray();

			imageStr	= new StringBuffer();
			imageStr.append("data:image/");
			imageStr.append(fileType);
			imageStr.append(";base64,");

			//imageStr.append(ByteConvertor.byteArraytoBase64(imageByte));

			return imageStr.toString();
		} catch (IOException e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			bufferedImage		= null;
			inputFile			= null;
			imageByte			= null;
			fullPath			= null;
			outputStream		= null;
		}
	}
	public static boolean writeFileForMobile(String name, String imageStr, String fileType, String locationPath) throws Exception {
		InputStream		in					= null;
		BufferedImage	bufferedImage		= null;
		File			outputFile			= null;
		byte[]			imageByte			= null;
		String			fullPath			= null;
		boolean			success				= false;

		try {

			//imageByte		= ByteConvertor.base64toByteArray(imageStr);
			
			in				= new ByteArrayInputStream(imageByte);
			bufferedImage	= ImageIO.read(in);
			
			fullPath		= locationPath + name + "." + fileType;
			
			outputFile		= new File(fullPath);

			if (!outputFile.exists()) {
				outputFile.mkdirs();
			}

			success		= ImageIO.write(bufferedImage, fileType, new File(fullPath));

			return success;
		} catch (IOException e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} catch (Exception e) {
			throw ExceptionProcess.execute(e, TRACE_ID);
		} finally {
			if (in != null) {
				in.close();
			}
			in					= null;
			bufferedImage		= null;
			outputFile			= null;
			imageByte			= null;
			fullPath			= null;
			success				= false;
		}
	}
	
	/*
    private String getFileType(String base64File) {​​​​​​​
        String fileType = "";
        if (!TextUtil.isEmptyOrNull(base64File)) {​​​​​​​
            fileType = base64File.substring(base64File.indexOf('/') + 1, base64File.indexOf(';'));
        }​​​​​​​
        return fileType;
    } */
}
