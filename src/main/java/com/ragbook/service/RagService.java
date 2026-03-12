package com.ragbook.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ragbook.util.PdfUtil;

@Service
public class RagService {
	 	@Autowired
	    private ChatClient chatClient;

	    private String bookContent;

	    public String savePdf(MultipartFile file) {
	    		try {
	        bookContent = PdfUtil.extractText(file);

	        return "PDF uploaded successfully!";
	    		} 
	    		
	    		catch (Exception e) {
	                e.printStackTrace();
	                return "Error processing PDF";
	            }
	    		
	    		}

	    public String askBook(String question) {

	        String prompt = """
	                Answer the question based on the following book content:

	                %s

	                Question: %s
	                """.formatted(bookContent, question);

	        return chatClient.prompt(prompt).call().content();
	    }
}
