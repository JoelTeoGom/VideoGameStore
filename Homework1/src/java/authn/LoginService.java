package authn;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jdk.internal.org.jline.utils.Log;

@Path("/login")
public class LoginService {

    @Context
    private HttpServletRequest servletRequest;

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {
        try {
            // Autenticar al usuario
            TypedQuery<Credentials> query = em.createNamedQuery("Credentials.findUser", Credentials.class);
            Credentials user = query.setParameter("username", credentials.getUsername()).getSingleResult();

            if (user.getPassword().equals(credentials.getPassword())) {
                // Crear una sesión y almacenar la información del usuario
                HttpSession session = servletRequest.getSession(true);
                session.setAttribute("username", credentials.getUsername());

                // Obtener el nombre de usuario para incluirlo en el valor de la cookie
                String username = (String) session.getAttribute("username");

                // Crear una cookie con el nombre de usuario como valor
                NewCookie cookie = new NewCookie("JSESSIONID", session.getId(), null, null, null, -1, false);
                
                return Response.ok().entity("{\"message\": \"Login successful\"}").cookie(cookie).build();
            } else {
                // No autorizado
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (NoResultException e) {
            // Usuario no encontrado
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
