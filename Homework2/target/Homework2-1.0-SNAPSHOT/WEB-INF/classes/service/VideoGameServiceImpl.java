package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.model.VideoGameDTO;
import jakarta.inject.Inject;

import jakarta.inject.Singleton;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;


import java.util.logging.Logger;

public class VideoGameServiceImpl implements VideoGameService {

    @Inject
    private Logger log;

    private static final String API_BASE_URL = "http://localhost:8080/Homework1/webresources/game";



    public List<VideoGameDTO> getFilteredVideoGames(String type, String console) {
       Client client = ClientBuilder.newClient();
       WebTarget target = client.target(API_BASE_URL).queryParam("type", type).queryParam("console", console);
       String apiUrlWithParams = target.getUri().toString();
       log.info("API URL with parameters: " + apiUrlWithParams);

       Response response = target.request(MediaType.APPLICATION_JSON).get();
       if (response.getStatus() == Response.Status.OK.getStatusCode()) {
           return response.readEntity(new GenericType<List<VideoGameDTO>>() {});
       } else {
           log.warning("Failed to fetch filtered video games. HTTP Status: " + response.getStatus());
           return null;
       }
}


    public VideoGameDTO getGameDetailsByName(String name) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(API_BASE_URL).path("/byname").queryParam("name", name);

        String apiUrlWithParams = target.getUri().toString();
        log.info("API URL with parameters: " + apiUrlWithParams);

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(VideoGameDTO.class);
        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            log.warning("Game not found. HTTP Status: " + response.getStatus());
            return null;
        } else {
            log.warning("Failed to fetch game details by name. HTTP Status: " + response.getStatus());
            return null;
        }
    }

}
