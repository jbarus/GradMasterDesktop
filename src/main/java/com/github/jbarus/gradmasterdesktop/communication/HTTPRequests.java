package com.github.jbarus.gradmasterdesktop.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jbarus.gradmasterdesktop.models.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterdesktop.models.SolutionDTO;
import com.github.jbarus.gradmasterdesktop.models.Student;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HTTPRequests {
    private static final String BASE_URL = "http://localhost:8080/api/";
    public static List<ContextDisplayInfoDTO> getAllContextDisplayInfo(){
        try {
            URL obj = new URL(BASE_URL+"context-display-infos");
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                List<ContextDisplayInfoDTO> contextDisplayInfoDTOS = mapper.readValue(in, new TypeReference<List<ContextDisplayInfoDTO>>(){});
                in.close();
                return contextDisplayInfoDTOS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean deleteContext(UUID id) {

        try{
            URL obj = new URL(BASE_URL+"context-display-infos/"+id);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<UniversityEmployee> getUniversityEmployeesById(UUID id) {

        try{
            URL obj = new URL(BASE_URL+"university-employees/"+id);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("UniversityEmployee: "+responseCode);
            System.out.println("UUID: "+id);
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                List<UniversityEmployee> employeeList = mapper.readValue(in, new TypeReference<List<UniversityEmployee>>(){});
                in.close();
                return employeeList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<Student> getStudentsById(UUID id) {
        try{
            URL obj = new URL(BASE_URL+"students/"+id);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                List<Student> studentList = mapper.readValue(in, new TypeReference<List<Student>>(){});
                in.close();
                return studentList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static SolutionDTO getSolutionById(UUID id) {
        try{
            URL obj = new URL(BASE_URL+"solutions/"+id);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                SolutionDTO solutionDTO = mapper.readValue(in,SolutionDTO.class);
                in.close();
                return solutionDTO;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new SolutionDTO();
    }

    public static boolean uploadUniversityEmployeeFile(File file, UUID id){
        String boundary = "Boundary-" + System.currentTimeMillis();
        String LINE_FEED = "\r\n";

        try{
            URL url = new URL(BASE_URL+"university-employees/"+id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream outputStream = connection.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true)) {

                writer.append("--").append(boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"universityEmployees\"; filename=\"")
                        .append(file.getName()).append("\"").append(LINE_FEED);
                writer.append("Content-Type: ").append(Files.probeContentType(file.toPath())).append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();

                Files.copy(file.toPath(), outputStream);
                outputStream.flush();

                writer.append(LINE_FEED).flush();
                writer.append("--").append(boundary).append("--").append(LINE_FEED);
                writer.flush();
            }


            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static UUID createProblemContext(ContextDisplayInfoDTO displayInfoDTO) {
        try {
            URL obj = new URL(BASE_URL + "context-display-infos");
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            String jsonPayload = objectMapper.writeValueAsString(displayInfoDTO);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                ContextDisplayInfoDTO responseDTO = objectMapper.readValue(response.toString(), ContextDisplayInfoDTO.class);

                return responseDTO.getId();
            } else {
                throw new RuntimeException("Błąd serwera: kod odpowiedzi " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean uploadStudent(File file, UUID id) {
        String boundary = "Boundary-" + System.currentTimeMillis();
        String LINE_FEED = "\r\n";

        try{
            URL url = new URL(BASE_URL+"students/"+ id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream outputStream = connection.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true)) {

                writer.append("--").append(boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"students\"; filename=\"")
                        .append(file.getName()).append("\"").append(LINE_FEED);
                writer.append("Content-Type: ").append(Files.probeContentType(file.toPath())).append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();

                Files.copy(file.toPath(), outputStream);
                outputStream.flush();

                writer.append(LINE_FEED).flush();
                writer.append("--").append(boundary).append("--").append(LINE_FEED);
                writer.flush();
            }


            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean uploadPositiveRelation(UUID contextId, List<UUID> positiveRelation) {
        try {
            URL url = new URL(BASE_URL + "relations/positive/" + contextId);
            System.out.println("Positive: " + positiveRelation);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(positiveRelation);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean uploadNegativeRelation(UUID contextId, List<UUID> negativeRelation) {
        try {
            URL url = new URL(BASE_URL + "relations/negative/" + contextId);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(negativeRelation);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<UniversityEmployee> getPositiveRelationsById(UUID id) {

        try{
            URL obj = new URL(BASE_URL+"relations/positive/"+id);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                List<UniversityEmployee> employeeList = mapper.readValue(in, new TypeReference<List<UniversityEmployee>>(){});
                in.close();
                return employeeList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<UniversityEmployee> getNegativeRelationsById(UUID id) {

        try{
            URL obj = new URL(BASE_URL+"relations/negative/"+id);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                List<UniversityEmployee> employeeList = mapper.readValue(in, new TypeReference<List<UniversityEmployee>>(){});
                in.close();
                return employeeList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean updateUniversityEmployeeById(UUID id, List<UniversityEmployee> universityEmployees) {
        try {
            URL url = new URL(BASE_URL + "university-employees/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String jsonPayload = mapper.writeValueAsString(universityEmployees);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
