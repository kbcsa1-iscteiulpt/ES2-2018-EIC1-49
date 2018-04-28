package support;

import java.awt.Graphics2D;
import java.awt.print.PageFormat;

import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFGraphics;
import com.qoppa.pdfWriter.PDFPage;

import problem.Problem;

/**
 * Editor of PDF
 * Reads and writes a PDF file, using the Problem class and evaluation results
 * 
 * @author Kevin Corrales nº 73529
 *
 */
public class PDF_Editor {

	public PDF_Editor() {
	}
	
	public void write(Problem problem) {
		try
    	{
			 PDFDocument pdfDoc = new PDFDocument();
	         PDFPage newPage = pdfDoc.createPage(new PageFormat());
	         
	         Graphics2D g2d = newPage.createGraphics();
	         g2d.setFont (PDFGraphics.HELVETICA.deriveFont(24f));
	         g2d.drawString(problem.getName(), 150, 100);
	         
	         String[] pdfProblem = problem.toString().split(";");       
	         g2d.setFont (PDFGraphics.HELVETICA.deriveFont(10f));
	         int line = 130;
	         
	         int ignore=0;
	         for(String text : pdfProblem) {
	        	 if(ignore>1) {
	        		 g2d.drawString(text, 100, line);
	        	 	line+=10;
	        	 }
	        	 ignore++;
	         }
	         
	         g2d.drawString("Description:", 100, line);
	         line+=10;
	         g2d.drawString(problem.getDescription(), 100, line);
	         
	         pdfDoc.addPage(newPage);
	         pdfDoc.saveDocument("./src/files/"+problem.getName()+".pdf");
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
	}
	
	
		
}
