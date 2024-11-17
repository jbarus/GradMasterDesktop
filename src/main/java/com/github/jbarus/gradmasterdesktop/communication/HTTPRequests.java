package com.github.jbarus.gradmasterdesktop.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jbarus.gradmasterdesktop.models.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterdesktop.models.SolutionDTO;
import com.github.jbarus.gradmasterdesktop.models.Student;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
}
