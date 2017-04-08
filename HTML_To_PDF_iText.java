package javaUtils;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HTML_To_PDF_iText {
	public String header = "";
	public String body = "";
	public String footer = "";
	public String pdfFilename;
	public String labelPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "artifacts/assets/";
	
	public HTML_To_PDF_iText(String header, String body, String footer, String pdfFilename) {
	  this.body= body;
	  this.header = header;
	  this.footer = footer;
	  this.pdfFilename = pdfFilename;
	}

	public String htmlToPdfFile() throws Exception{
		
	    String response = "";
	    try {
	    	String html = header + body + footer;
	    	Document document = new Document();
	    	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilename));
	    	document.open();
	    	InputStream is = new ByteArrayInputStream(html.getBytes());
	    	XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
	    	document.close(); 
	        writer.close();		    
	        response = pdfFilename;
	    } catch (Exception e) {
	    	response = "I/O error - " + e.getMessage();
	    	System.out.println( "I/O error - " + e.getMessage());
	    }
	    
	    return response;
	}
}
