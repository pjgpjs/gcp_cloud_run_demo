package com.cloudrun.microservicetemplate;


import com.cloudrun.ProjectConst;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
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
    Client client = Client.builder()
            .project(ProjectConst.GCP_PROJECT_ID)
            .location(ProjectConst.LOCATION)
            .vertexAI(true)
            .build();
   
    public String request(){
      
      // 설정값
      String projectId = ProjectConst.GCP_PROJECT_ID;
      String location = ProjectConst.LOCATION; // 예: "us-central1"
      
      String modelName = "gemini-2.0-flash-001";
      String gcsUriA = "gs://document_compare/form_1040_2013.pdf";
      String gcsUriB = "gs://document_compare/form_1040_2023.pdf";
      // ------------ㄴㄴ

      String promptString = "너는 누구니?";
      try{
          GenerateContentResponse response =
                  client.models.generateContent("gemini-2.0-flash-001", promptString, null);
          String result = response.text();
          return result;
      }catch(Exception e){
        e.printStackTrace();
        return "Error: " + e.getMessage();
      }
   }
}
