# 📚 AI-Powered Book Assistant (RAG)

An AI-powered Retrieval-Augmented Generation (RAG) application built with **Java 21**, **Spring Boot**, **Spring AI**, **Google Gemini**, **MariaDB Vector Store**, and **HuggingFace Embeddings**.

The application enables users to upload PDF books and ask natural language questions about their content. Instead of relying only on the LLM's knowledge, the system retrieves relevant sections from the uploaded document and uses them as context to generate accurate, grounded responses.

---

## 🚀 Features

- 📄 Upload PDF books
- 🔍 Extract text using Apache PDFBox
- ✂️ Semantic text chunking using Spring AI TokenTextSplitter
- 🧠 Generate local embeddings using HuggingFace **all-MiniLM-L6-v2** (ONNX Runtime)
- 💾 Store 384-dimensional embeddings in MariaDB Vector Store
- 🎯 Perform semantic similarity search
- 🤖 Generate context-aware answers using Google Gemini (gemini-2.5-flash)
- 🌐 REST APIs with Swagger/OpenAPI documentation

---

## 🏗️ Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java 21 |
| Backend | Spring Boot 3 |
| AI Framework | Spring AI |
| LLM | Google Gemini 2.5 Flash |
| Embedding Model | HuggingFace all-MiniLM-L6-v2 |
| Embedding Runtime | ONNX Runtime |
| PDF Processing | Apache PDFBox |
| Database | MariaDB |
| API Documentation | Swagger / OpenAPI |
| Build Tool | Maven |

---

## 🏛️ System Architecture

```text
              PDF Upload
                   │
                   
          Apache PDFBox
                   │
                   
    TokenTextSplitter (Chunking)
                   │
                   
 HuggingFace Embedding Model
   (all-MiniLM-L6-v2 ONNX)
                   │
                   
      MariaDB Vector Store
                   │
     Semantic Similarity Search
                   │
                   
     Retrieved Context Chunks
                   │
                   
     Google Gemini 2.5 Flash
                   │
                   
        AI Generated Answer
```
