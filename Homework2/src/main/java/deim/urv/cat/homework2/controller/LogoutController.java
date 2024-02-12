package deim.urv.cat.homework2.controller;

import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.UriRef;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.logging.Logger;

@Controller
@Path("LogOut")
public class LogoutController {
    @Inject
    Logger log;
  

    @Context
    private HttpServletRequest request;

    @GET
    @UriRef("log-me-out")
    public String logout() {
        // Invalidar la sesión para eliminar toda la información relacionada con el usuario
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Redirigir a la página de inicio de sesión después del cierre de sesión
        return "redirect:/FrontPage";
    }
}
