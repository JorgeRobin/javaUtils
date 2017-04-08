package javaUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFile {
	String path = "";
	String fileName = "";
	StringBuilder sb = new StringBuilder();
	
	public ReadFromFile(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}
	
	public String read() {
		String result = "";
		BufferedReader reader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(path + fileName);
			reader = new BufferedReader(fileReader);
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		try {
			reader.close();
			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return result;
	} 
}
