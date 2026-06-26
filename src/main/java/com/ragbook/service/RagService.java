package com.ragbook.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ragbook.util.PdfUtil;

@Service
public class RagService {
	 	@Autowired
	    private ChatClient chatClient;
	 	
	 	@Autowired
	 	private VectorStore vectorStore;

		@Autowired
		private JdbcTemplate jdbcTemplate;
	    
	    public String savePdf(MultipartFile file) {
	    		try {
					// Clear old data before indexing new book
					jdbcTemplate.execute("TRUNCATE TABLE vector_store");

	    			String text=PdfUtil.extractText(file); // extract full text

	    			Document document=new Document(text);	// springai wraps text into document Object

	    			TokenTextSplitter splitter=new TokenTextSplitter(); // creates chunking utility

	    			List<Document> chunks=splitter.split(document); // splits into smaller pieces

	    			vectorStore.add(chunks);  // store chunks in MariaDb vector store

	    			return "PDF indexed Successfully. Total chunks : "+chunks.size() ;

	    		}catch (Exception e) {
					e.printStackTrace();
					return "Error: " + e.getMessage();
				}
	    }



	    
	    public String askBook(String question) {
			List<Document> docs = vectorStore.similaritySearch(
					SearchRequest.builder()
							.query(question)
							.topK(5)
							.similarityThreshold(0.3)
							.build()
			);

			// DEBUG - remove after fixing
//			System.out.println("Found " + docs.size() + " chunks");
//			docs.forEach(d -> System.out.println("Chunk: " + d.getText().substring(0, Math.min(100, d.getText().length()))));
//
//			if (docs.isEmpty()) {
//				return "No relevant chunks found in vector store";
//			}

	    		String context=docs.stream().map(Document::getText)
	    				.reduce("",(a,b)-> a+"\n"+b);
	    		
	    		// reduce()   --- Combines all chunks into one String.
	    		
	    		String prompt="""
	    					You are a helpful assistant. Use the context below to answer the question.
						    If the context contains relevant information, use it.
	    		
	    					context:
	    					%s
	    					Question:
	    					%s
	    				
	    					If the answer cannot be determined from the context
	    					say I don't know,I couldn't find that information in the uploaded book..
	    				""".formatted(context,question);
	    		
	    		return chatClient.prompt(prompt).call().content();
//
//			return docs.stream()
//					.map(Document::getText)
//					.reduce("", (a, b) -> a + "\n\n" + b);
	    }



//	    public String askBook(String question) {
//
//	    	String prompt = """
//	    			You are a helpful assistant.
//
//	    			Use only the provided book content.
//	    			If the answer is not present in the book, say:
//
//	    			"I couldn't find that information in the uploaded book."
//
//	    			Book Content:
//	    			%s
//
//	    			Question:
//	    			%s
//	    			""".formatted(bookContent, question);
//
//	        return chatClient.prompt(prompt).call().content();
//	    }
}
