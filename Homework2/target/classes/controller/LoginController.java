package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.CredentialsDTO;
import deim.urv.cat.homework2.service.CarritoService;
import deim.urv.cat.homework2.service.CarritoServiceImpl;
import deim.urv.cat.homework2.service.LoginService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.UriRef;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.logging.Logger;
import static jdk.internal.net.http.common.Log.headers;

@Controller
@Path("Login")
public class LoginController {
    @Inject
    Logger log;

    @Inject
    private CarritoService carritoService;  // Inyectar CarritoService
    
    @Context
    private HttpServletRequest request;

    @Inject
    private LoginService loginService;

    @GET
    @UriRef("log-in-get")
    public String showLoginForm(@Context HttpHeaders headers) {
        // Obtener la sesión del request
        HttpSession session = request.getSession();

        // Obtener la cookie de sesión del encabezado de la solicitud
        Cookie sessionCookie = headers.getCookies().get("JSESSIONID");

        // Obtener la cookie de sesión persistida en tu aplicación (la que pusiste en la sesión)
        Cookie persistedSessionCookie = (Cookie) session.getAttribute("sessionCookie");

        // Obtener el nombre de usuario de la sesión
        String usernameCredentials = (String) session.getAttribute("usernameCredentials");

        log.info("EL VALOR DE LA SESSION COOKIE METODO GET: " + persistedSessionCookie);
        log.info("EL USUARIO AUTENTICADO DEL MODELO METODO GET: " + usernameCredentials);

        // Verificar si las cookies son iguales
        if (persistedSessionCookie != null && persistedSessionCookie.getValue().equals(sessionCookie.getValue())) {
            // Las cookies son iguales, el usuario está autenticado
            return "redirect:/FrontPage";
        } else {
            // Usuario no autenticado, mostrar formulario de inicio de sesión
            return "log-in.jsp";
        }
    }
   @POST
   @UriRef("authenticate")
   public Response authenticateUser(
           @FormParam("username") String username,
           @FormParam("password") String password,
           @Context HttpHeaders headers) {

       try {
           CredentialsDTO credentialsDTO = new CredentialsDTO(username, password);
           Response authenticationResponse = loginService.authenticateUser(credentialsDTO);

           // Verificar si la autenticación fue exitosa
           if (authenticationResponse.getStatus() == Response.Status.OK.getStatusCode()) {
               // Obtener la sesión del request
               HttpSession session = request.getSession(true);

               // Obtener la cookie de sesión del servicio de inicio de sesión
               Cookie sessionCookie = headers.getCookies().get("JSESSIONID");

               // Obtener el nombre de usuario del objeto CredentialsDTO
               String authenticatedUsername = credentialsDTO.getUsername();

               log.info("EL NOMBRE DEL USUARIO AUTENTICADO METODO POST: " + authenticatedUsername);
               log.info("EL VALOR DE LA SESSION COOOKIE METODO POST: " + sessionCookie.getValue());

               // Almacenar el nombre de usuario y la cookie de sesión en la sesión
               session.setAttribute("usernameCredentials", authenticatedUsername);
               session.setAttribute("sessionCookie", sessionCookie);
               session.setAttribute("credentialsDTO", credentialsDTO);           
               CarritoService carritoEnSesion = new CarritoServiceImpl();
               session.setAttribute("carritoService", carritoEnSesion);

               return Response.seeOther(URI.create("FrontPage"))
                       .cookie(new NewCookie(sessionCookie.getName(), sessionCookie.getValue()))
                       .build();
           } else {
               // La autenticación falló, mostrar un mensaje de error
               // Puedes almacenar el mensaje de error en la sesión si es necesario
               request.getSession().setAttribute("errorMessage", "Invalid username or password");
               return Response.status(Response.Status.UNAUTHORIZED).entity("log-in.jsp").build();
           }
       } catch (Exception e) {
           // Captura cualquier excepción que pueda ocurrir durante la autenticación
           request.getSession().setAttribute("errorMessage", "Error al iniciar sesión. Verifica tus credenciales.");
           return Response.status(Response.Status.UNAUTHORIZED).entity("log-in.jsp").build();
       }
   }

}


