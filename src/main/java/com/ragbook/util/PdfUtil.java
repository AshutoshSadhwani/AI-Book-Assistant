package com.ragbook.util;

import java.io.InputStream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

public class PdfUtil {
	
	public static String extractText(MultipartFile file) throws Exception {

		 // Option 1: Using getBytes()
        PDDocument document = Loader.loadPDF(file.getBytes());

        // Option 2: Using InputStream
        // PDDocument document = Loader.loadPDF(file.getInputStream());

	    PDFTextStripper stripper = new PDFTextStripper();
	    String text = stripper.getText(document);

	    document.close();

	    return text;
	}
}
