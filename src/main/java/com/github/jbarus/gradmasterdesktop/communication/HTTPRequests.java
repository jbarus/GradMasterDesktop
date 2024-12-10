package com.github.jbarus.gradmasterdesktop.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jbarus.gradmasterdesktop.models.*;
import com.github.jbarus.gradmasterdesktop.models.communication.*;
import com.github.jbarus.gradmasterdesktop.models.dto.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HTTPRequests {
    private static final String BASE_URL = "http://localhost:8080/api/";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static{
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    //University Employee
    public static FullResponse<UploadStatus, UniversityEmployeeDTO> uploadUniversityEmployeesFileByContextId(UUID contextId, File file) {
        String boundary = "Boundary-" + System.currentTimeMillis();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"universityEmployees\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());

            outputStream.write(java.nio.file.Files.readAllBytes(file.toPath()));
            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
        } catch (IOException e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "university-employees/" + contextId))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(outputStream.toByteArray()))
                .build();

        HttpResponse<String> httpResponse;
        try {
            httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        ServerResponse<UploadStatus, UniversityEmployeeDTO> serverResponse;

        try {
            serverResponse = OBJECT_MAPPER.readValue(httpResponse.body(),
                    OBJECT_MAPPER.getTypeFactory().constructParametricType(ServerResponse.class, UploadStatus.class, UniversityEmployeeDTO.class));
        } catch (Exception e) {
            return null;
        }

        if (httpResponse.statusCode() >= 300) {
            return new FullResponse<>(httpResponse.statusCode(), serverResponse.getStatus(), null);
        }

        return new FullResponse<>(httpResponse.statusCode(), serverResponse.getStatus(), serverResponse.getResponseBody());
    }

    public static Response<UniversityEmployeeDTO> getUniversityEmployeeByContextId(UUID contextId){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "university-employees/" + contextId))
                .GET()
                .build();

        HttpResponse<String> response;
        try{
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            return null;
        }

        UniversityEmployeeDTO universityEmployees;

        if(response.statusCode() < 300){
            try{
                universityEmployees = OBJECT_MAPPER.readValue(response.body(), UniversityEmployeeDTO.class);
                return new Response<>(response.statusCode(),universityEmployees);
            }catch (Exception e){
                return null;
            }
        }
        return new Response<>(response.statusCode(),null);
    }

    public static Response<List<UniversityEmployee>> updateUniversityEmployeeByContextId(UUID contextId, List<UniversityEmployee> universityEmployees){

        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(universityEmployees);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "university-employees/" + contextId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try{
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            return null;
        }

        if(response.statusCode() < 300){
            try{
                universityEmployees = OBJECT_MAPPER.readValue(response.body(), new TypeReference<List<UniversityEmployee>>(){});
                return new Response<>(response.statusCode(),universityEmployees);
            }catch (Exception e){
                return null;
            }
        }
        return new Response<>(response.statusCode(),null);
    }

    //Student
    public static FullResponse<UploadStatus, StudentDTO> uploadStudentFileByContextId(UUID contextId, File file) {
        String boundary = "Boundary-" + System.currentTimeMillis();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"students\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());

            outputStream.write(java.nio.file.Files.readAllBytes(file.toPath()));
            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
        } catch (IOException e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "students/" + contextId))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(outputStream.toByteArray()))
                .build();

        HttpResponse<String> httpResponse;
        try {
            httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        ServerResponse<UploadStatus, StudentDTO> serverResponse;

        try {
            serverResponse = OBJECT_MAPPER.readValue(httpResponse.body(),
                    OBJECT_MAPPER.getTypeFactory().constructParametricType(ServerResponse.class, UploadStatus.class, StudentDTO.class));
        } catch (Exception e) {
            return null;
        }

        if (httpResponse.statusCode() >= 300) {
            return new FullResponse<>(httpResponse.statusCode(), serverResponse.getStatus(), null);
        }

        return new FullResponse<>(httpResponse.statusCode(), serverResponse.getStatus(), serverResponse.getResponseBody());
    }

    public static Response<StudentDTO> getStudentsByContextId(UUID contextId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "students/" + contextId))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                StudentDTO students = OBJECT_MAPPER.readValue(response.body(), StudentDTO.class);
                return new Response<>(response.statusCode(), students);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<StudentDTO> updateStudentsByContextId(UUID contextId, List<Student> students) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(students);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "students/" + contextId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                StudentDTO updatedStudents = OBJECT_MAPPER.readValue(response.body(), StudentDTO.class);
                return new Response<>(response.statusCode(), updatedStudents);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    //DisplayInfo

    public static Response<ContextDisplayInfoDTO> createContextDisplayInfo(ContextDisplayInfoDTO contextDto) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(contextDto);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "context-display-infos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                ContextDisplayInfoDTO createdContext = OBJECT_MAPPER.readValue(response.body(), ContextDisplayInfoDTO.class);
                return new Response<>(response.statusCode(), createdContext);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<ContextDisplayInfoDTO> getContextDisplayInfoByContextId(UUID contextId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "context-display-infos/" + contextId))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                ContextDisplayInfoDTO context = OBJECT_MAPPER.readValue(response.body(), ContextDisplayInfoDTO.class);
                return new Response<>(response.statusCode(), context);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<List<ContextDisplayInfoDTO>> getAllContextDisplayInfos() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "context-display-infos"))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                List<ContextDisplayInfoDTO> contexts = OBJECT_MAPPER.readValue(response.body(), new TypeReference<List<ContextDisplayInfoDTO>>() {});
                return new Response<>(response.statusCode(), contexts);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<ContextDisplayInfoDTO> updateContextDisplayInfoByContextId(UUID contextId, ContextDisplayInfoDTO contextDto) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(contextDto);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "context-display-infos/" + contextId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                ContextDisplayInfoDTO updatedContext = OBJECT_MAPPER.readValue(response.body(), ContextDisplayInfoDTO.class);
                return new Response<>(response.statusCode(), updatedContext);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<Void> deleteContextDisplayInfoByContextId(UUID contextId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "context-display-infos/" + contextId))
                .DELETE()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            return new Response<>(response.statusCode(), null);
        }
        return new Response<>(response.statusCode(), null);
    }

    // Problem Parameters
    public static Response<ProblemParametersDTO> setProblemParametersByContextId(UUID contextId, ProblemParameters problemParameters) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(problemParameters);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "problem-parameters/" + contextId))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                ProblemParametersDTO result = OBJECT_MAPPER.readValue(response.body(), ProblemParametersDTO.class);
                return new Response<>(response.statusCode(), result);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<ProblemParametersDTO> getProblemParametersByContextId(UUID contextId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "problem-parameters/" + contextId))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                ProblemParametersDTO result = OBJECT_MAPPER.readValue(response.body(), ProblemParametersDTO.class);
                return new Response<>(response.statusCode(), result);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<ProblemParametersDTO> updateProblemParametersByContextId(UUID contextId, ProblemParameters problemParameters) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(problemParameters);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "problem-parameters/" + contextId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                ProblemParametersDTO result = OBJECT_MAPPER.readValue(response.body(), ProblemParametersDTO.class);
                return new Response<>(response.statusCode(), result);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<RelationDTO> addPositiveRelationByContextId(UUID contextId, List<UUID> relations) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(relations);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "relations/positive/" + contextId))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                RelationDTO relationDTO = OBJECT_MAPPER.readValue(response.body(), RelationDTO.class);
                return new Response<>(response.statusCode(), relationDTO);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<RelationDTO> addNegativeRelationByContextId(UUID contextId, List<UUID> relations) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(relations);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "relations/negative/" + contextId))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                RelationDTO relationDTO = OBJECT_MAPPER.readValue(response.body(), RelationDTO.class);
                return new Response<>(response.statusCode(), relationDTO);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<RelationDTO> updatePositiveRelationByContextId(UUID contextId, List<UUID> relations) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(relations);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "relations/positive/" + contextId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                RelationDTO relationDTO = OBJECT_MAPPER.readValue(response.body(), RelationDTO.class);
                return new Response<>(response.statusCode(), relationDTO);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<RelationDTO> updateNegativeRelationByContextId(UUID contextId, List<UUID> relations) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(relations);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "relations/negative/" + contextId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                RelationDTO relationDTO = OBJECT_MAPPER.readValue(response.body(), RelationDTO.class);
                return new Response<>(response.statusCode(), relationDTO);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<RelationDTO> getPositiveRelationsByContextId(UUID contextId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "relations/positive/" + contextId))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                RelationDTO relationDTO = OBJECT_MAPPER.readValue(response.body(), RelationDTO.class);
                return new Response<>(response.statusCode(), relationDTO);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<RelationDTO> getNegativeRelationsByContextId(UUID contextId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "relations/negative/" + contextId))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                RelationDTO relationDTO = OBJECT_MAPPER.readValue(response.body(), RelationDTO.class);
                return new Response<>(response.statusCode(), relationDTO);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static FullResponse<CalculationStartStatus, ProblemDTO> startCalculationByContextId(UUID contextId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "problems/" + contextId))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> httpResponse;
        try {
            httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        ServerResponse<CalculationStartStatus, ProblemDTO> serverResponse;

        try {
            serverResponse = OBJECT_MAPPER.readValue(httpResponse.body(),
                    OBJECT_MAPPER.getTypeFactory().constructParametricType(ServerResponse.class, CalculationStartStatus.class, ProblemDTO.class));
        } catch (Exception e) {
            return null;
        }

        if (httpResponse.statusCode() >= 300) {
            return new FullResponse<>(httpResponse.statusCode(), serverResponse.getStatus(), null);
        }

        return new FullResponse<>(httpResponse.statusCode(), serverResponse.getStatus(), serverResponse.getResponseBody());
    }

    public static Response<SolutionDTO> getSolutionByContextId(UUID contextId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "solutions/" + contextId))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                SolutionDTO solution = OBJECT_MAPPER.readValue(response.body(), SolutionDTO.class);
                return new Response<>(response.statusCode(), solution);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<SolutionDTO> updateSolutionByContextId(UUID contextId, SolutionDTO solutionDTO) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(solutionDTO);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "solutions/" + contextId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                SolutionDTO updatedSolution = OBJECT_MAPPER.readValue(response.body(), SolutionDTO.class);
                return new Response<>(response.statusCode(), updatedSolution);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }

    public static Response<SolutionDTO> setSolutionByContextId(UUID contextId, SolutionDTO solutionDTO) {
        String jsonRequestBody;
        try {
            jsonRequestBody = OBJECT_MAPPER.writeValueAsString(solutionDTO);
        } catch (Exception e) {
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "solutions/" + contextId))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }

        if (response.statusCode() < 300) {
            try {
                SolutionDTO newSolution = OBJECT_MAPPER.readValue(response.body(), SolutionDTO.class);
                return new Response<>(response.statusCode(), newSolution);
            } catch (Exception e) {
                return null;
            }
        }
        return new Response<>(response.statusCode(), null);
    }
}
