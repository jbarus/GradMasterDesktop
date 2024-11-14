package com.github.jbarus.gradmasterdesktop.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jbarus.gradmasterdesktop.models.ContextDisplayInfoDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
}
