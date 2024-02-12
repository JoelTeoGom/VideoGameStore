package deim.urv.cat.homework2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import deim.urv.cat.homework2.model.RentalDTO;
import deim.urv.cat.homework2.model.CredentialsDTO;

import deim.urv.cat.homework2.model.VideoGameDTO;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@SessionScoped
@Named("litaCarrito")
public class CarritoServiceImpl implements CarritoService, Serializable {

    private static final String API_BASE_URL = "http://localhost:8080/Homework1/webresources/rental";
    private static final long serialVersionUID = 1L;

    private List<VideoGameDTO> videoGamesInCart;

    public CarritoServiceImpl() {
        initializeVideoGamesInCart();
    }

    private void initializeVideoGamesInCart() {
        if (videoGamesInCart == null) {
            videoGamesInCart = new ArrayList<>();
        }
    }

    @Override
    public List<VideoGameDTO> getVideoGamesInCart() {
        return videoGamesInCart;
    }

    @Override
    public void addVideoGameToCart(VideoGameDTO videoGame) {
        if (!videoGamesInCart.contains(videoGame)) {
            videoGamesInCart.add(videoGame);
        }
    }

    @Override
    public void removeVideoGameFromCart(VideoGameDTO videoGame) {

        for (Iterator<VideoGameDTO> iterator = videoGamesInCart.iterator(); iterator.hasNext(); ) {
            VideoGameDTO gameInCart = iterator.next();
            if (gameInCart.getName().equals(videoGame.getName())) {
                iterator.remove();
                break;
            }
        }
    }


    @Override
    public RentalDTO processCheckout(List<String> gameNames, CredentialsDTO credentials) {

        if (credentials != null) {
            videoGamesInCart.clear();

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(API_BASE_URL);

            // Construir la lista de nombres de videojuegos en formato JSON
            String jsonBody = convertListToJson(gameNames);

            // Codificar las credenciales a Base64
            String usernamePassword = credentials.getUsername() + ":" + credentials.getPassword();
            String authorizationHeader = "Basic " + Base64.getEncoder().encodeToString(usernamePassword.getBytes());

            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, authorizationHeader) // Agregar encabezado de autorización
                    .post(Entity.json(jsonBody));

            // Manejar la respuesta de la API
            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                // Leer y devolver el objeto RentalDTO desde la respuesta
                return response.readEntity(RentalDTO.class);
            } else {
                
                throw new RuntimeException("Error al realizar el checkout. Código de respuesta: " + response.getStatus());
            }
        } else {
            
            throw new RuntimeException("Error al realizar el checkout. Usuario no autenticado.");
        }
    }


    private String convertListToJson(List<String> gameNames) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(gameNames);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir la lista de nombres de videojuegos a JSON.", e);
        }
    }



    @Override
    public boolean isGameInCart(VideoGameDTO videoGame) {
        return videoGamesInCart.stream().anyMatch(cartItem -> cartItem.getName().equals(videoGame.getName()));
    }

}
