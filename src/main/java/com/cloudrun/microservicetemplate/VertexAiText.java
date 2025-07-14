package com.cloudrun.microservicetemplate;


import java.util.List;

import com.cloudrun.ProjectConst;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.PartMaker;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
public class VertexAiText {
  // This class is a placeholder for future Vertex AI text generation functionality.
  // Currently, it does not contain any methods or properties.
  // You can implement methods to interact with Vertex AI's text generation capabilities here.
  
  // Example method signature:
  // public String generateText(String prompt) {
  //   // Implementation goes here
  // }

    //use Gemini API
    // Client client = Client.builder().apiKey(ProjectConst.API_KEY).build();
    //use Vertext AI API
    // Client client = Client.builder()
    //         .project(ProjectConst.GCP_PROJECT_ID)
    //         .location(ProjectConst.LOCATION)
    //         .vertexAI(true)
    //         .build();
   
    public String request(){
      
      // 설정값
      String projectId = ProjectConst.GCP_PROJECT_ID;
      String location = ProjectConst.LOCATION; // 예: "us-central1"
      
      String modelName = "gemini-1.5-flash-001";
      String gcsUriA = "gs://document_compare/form_1040_2013.pdf";
      String gcsUriB = "gs://document_compare/form_1040_2023.pdf";
      // ------------

      try (VertexAI vertexAI = new VertexAI(projectId, location)) {
        GenerativeModel model = new GenerativeModel(modelName, vertexAI);
        String promptText = "The first document is from 2013, and the second document is from 2023. How did the standard deduction evolve?";
        
        // 1. 첫 번째 PDF 파일에 대한 Content 객체 생성
        Content fileContentA = Content.newBuilder()
            .addParts(PartMaker.fromMimeTypeAndData("application/pdf", gcsUriA))
            .build();
          
        // 2. 두 번째 PDF 파일에 대한 Content 객체 생성
        Content fileContentB = Content.newBuilder()
            .addParts(PartMaker.fromMimeTypeAndData("application/pdf", gcsUriB))
            .build();
          
        // 3. 텍스트 프롬프트에 대한 Content 객체 생성
        Content textContent = Content.newBuilder()
            .addParts(PartMaker.fromMimeTypeAndData("text/plain", promptText))
            .build();
          
        // 4. 모든 Content 객체를 리스트에 담아 요청 전송
        List<Content> contents = List.of(fileContentA, fileContentB, textContent);
        GenerateContentResponse response = model.generateContent(contents);
        
        String output = ResponseHandler.getText(response);
        System.out.println("--- Analysis Result ---");
        System.out.println(output);
        return output;
      } catch (Exception e) {
          System.err.println("API 호출 중 오류 발생: " + e.getMessage());
          e.printStackTrace();
          return "Error: " + e.getMessage();
      }
   }
}
