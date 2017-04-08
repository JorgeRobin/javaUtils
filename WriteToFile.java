package javaUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class WriteToFile {
	String path = "";
	String fileName = "";
	StringBuilder sb = new StringBuilder();
	
	public WriteToFile(String path, String fileName, String fileContent) {
		this.path = path;
		this.fileName = fileName;
		sb.append(fileContent);
	}
	
	public int write() {
		int result = 0;
		File file = new File(path + fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//
		try {
			//System.out.println("filePath: " + path + fileName);
			OutputStream os = new FileOutputStream(file);
			Writer w = new OutputStreamWriter(os);
			//System.out.println(sb.toString());
			w.write(sb.toString());
			w.flush();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = 2;
		}
		
		return result;
	}

}
