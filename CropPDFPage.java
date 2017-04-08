package javaUtils;

import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class CropPDFPage {
	public String filePath;
	public String fileToCrop;
	public String fileCropped;
	public int width;
	public int height;
	
	public CropPDFPage(String filePath, String fileToCrop, String fileCropped, int width, int height) {
		this.filePath = filePath;
		this.fileToCrop = fileToCrop;
		this.fileCropped = fileCropped;
		this.width = width;
		this.height = height;
	}

	public String crop() {
		String fileToReturn = "";
		try {
			// load document
		    PDDocument document = PDDocument.load(filePath + fileToCrop);
		    PDDocumentCatalog catalog = document.getDocumentCatalog();
		    @SuppressWarnings("unchecked")
		    List<PDPage> allPages = catalog.getAllPages();
            // crop all pages
		    for (PDPage page : allPages) {
		    	PDRectangle newMediaBox = new PDRectangle();
		        newMediaBox.setLowerLeftX(30);
		        newMediaBox.setLowerLeftY(height + 20);
		        newMediaBox.setUpperRightX(page.getMediaBox().getWidth() - width);
		        newMediaBox.setUpperRightY(page.getMediaBox().getHeight() - 30);
		        page.setMediaBox(newMediaBox);
		    }
		    document.save(filePath + fileCropped);
		    fileToReturn = filePath + fileCropped;
		    document.close();
			System.out.println("Common - utils - CropPDFPage - saved PDF successfully to: " + fileToReturn);
		} catch (Exception e){
			System.out.println("Common - utils - CropPDFPage - save PDF" + e.getStackTrace());
		}
		return fileCropped;
		
    }
		
}
