package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.model.CredentialsDTO;
import jakarta.inject.Inject;
import jakarta.mvc.Models;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

public class LoginServiceImpl implements LoginService {

    private static final String BACKEND_BASE_URL = "http://localhost:8080/Homework1/webresources";

    private final Client client;

    @Inject
    private Models models;

    public LoginServiceImpl() {
        this.client = ClientBuilder.newClient();
    }

    @Override
    public Response authenticateUser(CredentialsDTO credentialsDTO) {
        // Llama a la API de autenticación del backend
        WebTarget target = client.target(BACKEND_BASE_URL).path("login");
        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
        Response response = builder.post(Entity.entity(credentialsDTO, MediaType.APPLICATION_JSON));

        // Obtiene las cookies de la respuesta
        MultivaluedMap<String, Object> headers = response.getHeaders();
        Cookie sessionCookie = extractSessionCookie(headers, "JSESSIONID");

        // Almacena la cookie de sesión en el modelo
        models.put("sessionCookie", sessionCookie);

        // Almacena el nombre de usuario en el modelo
        models.put("username", credentialsDTO.getUsername());


        return response;
    }

    private Cookie extractSessionCookie(MultivaluedMap<String, Object> headers, String cookieName) {
        for (Object header : headers.get("Set-Cookie")) {
            String cookieHeaderValue = (String) header;
            NewCookie newCookie = NewCookie.valueOf(cookieHeaderValue);
            if (newCookie.getName().equals(cookieName)) {
                return newCookie;
            }
        }
        return null;
    }
}
