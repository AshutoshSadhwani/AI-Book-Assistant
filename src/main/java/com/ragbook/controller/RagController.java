package com.ragbook.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ragbook.model.QuestionRequest;
import com.ragbook.service.RagService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/rag")
@Tag(name = "RAG Controller", description = "Upload PDFs and ask questions")
public class RagController {
	@Autowired
	private RagService ragService;
	
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Upload a PDF file", description = "Upload a PDF to be processed by the RAG system")
    public String uploadPdf(@RequestParam("file") MultipartFile file) {
        return ragService.savePdf(file);
    }
	  
	    @PostMapping("/ask")
	    public String askQuestion(@RequestBody QuestionRequest request ) {
	        return ragService.askBook(request.getQuestion());
	    }
}
