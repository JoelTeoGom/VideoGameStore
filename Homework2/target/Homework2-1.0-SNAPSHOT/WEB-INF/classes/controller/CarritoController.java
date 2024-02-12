package deim.urv.cat.homework2.controller;
import deim.urv.cat.homework2.model.CredentialsDTO;
import deim.urv.cat.homework2.model.AlertMessage;
import deim.urv.cat.homework2.model.VideoGameDTO;
import deim.urv.cat.homework2.model.RentalDTO;
import deim.urv.cat.homework2.service.CarritoService;
import deim.urv.cat.homework2.service.VideoGameService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.HttpHeaders;
import static java.lang.Math.log;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@Path("Carrito")
public class CarritoController {
    @Context
    private HttpServletRequest request;
    @Inject
    private HttpSession session;

    @Inject
    Logger log;
    
    @Inject
    private AlertMessage flashMessage;

    @Inject
    private Models models;

    @Inject
    private CarritoService shoppingCartService;

    @Inject
    private VideoGameService videoGameService;

    @GET
    @UriRef("carrito-details")
    public String showShoppingCart(@Context HttpHeaders headers) {
        
        HttpSession session = request.getSession();

        Cookie sessionCookie = headers.getCookies().get("JSESSIONID");

        Cookie persistedSessionCookie = (Cookie) session.getAttribute("sessionCookie");

        String usernameCredentials = (String) session.getAttribute("usernameCredentials");

        // Verificar si las cookies son iguales
        if (persistedSessionCookie != null && persistedSessionCookie.getValue().equals(sessionCookie.getValue())) {
            
            List<VideoGameDTO> videoGamesInCart = shoppingCartService.getVideoGamesInCart();
            models.put("videoGamesInCart", videoGamesInCart);
            return "game-cart.jsp";
            
        } else {
            return "redirect:/FrontPage";
        }
       
    }

    @POST
    @UriRef("carrito/add")
    public String addToCart(
            @FormParam("videoGameName") String videoGameName,
            @Context HttpHeaders headers) {

        HttpSession session = request.getSession();
        Cookie sessionCookie = headers.getCookies().get("JSESSIONID");
        Cookie persistedSessionCookie = (Cookie) session.getAttribute("sessionCookie");
        String usernameCredentials = (String) session.getAttribute("usernameCredentials");

        log.info("add cart POST: " + persistedSessionCookie);
        log.info("add cart post: " + usernameCredentials);

        if (persistedSessionCookie != null && persistedSessionCookie.getValue().equals(sessionCookie.getValue())) {
            VideoGameDTO videogame = videoGameService.getGameDetailsByName(videoGameName);

            if (shoppingCartService.isGameInCart(videogame) || videogame.getAvailability().equals("no")) {
                models.put("message", "¡Error! El juego ya está en el carrito.");
                return "redirect:/FrontPage";
            }else{
              
                shoppingCartService.addVideoGameToCart(videogame);
                List<VideoGameDTO> cartItems = shoppingCartService.getVideoGamesInCart();
                session.setAttribute("cartItems", cartItems);
                log.info("ADD DE CARRITO DETAILS: "+session.getAttribute("cartItems").toString());
                models.put("message", "¡Éxito! Juego añadido al carrito correctamente.");
               // session.setAttribute("message", "¡Éxito! Juego añadido al carrito correctamente.");
                //return "redirect:/GameDetails?name=" + URLEncoder.encode(videoGameName, StandardCharsets.UTF_8);
                return "redirect:/FrontPage";
            }
        } else {
           
            return "log-in.jsp";
        }
    }

    @POST
    @Path("remove")
    public String removeFromCart(@FormParam("videoGameName") String videoGameName) {
       
        VideoGameDTO videoGame = videoGameService.getGameDetailsByName(videoGameName);
         log.info("BORRAR: "+videoGame.getName());
        shoppingCartService.removeVideoGameFromCart(videoGame);
        List<VideoGameDTO> cartItems = shoppingCartService.getVideoGamesInCart();
        session.setAttribute("cartItems", cartItems);
        return "game-cart.jsp";
    }
    @POST
    @Path("carrito/checkout")
    public String checkout(@Context HttpHeaders headers) {
        List<VideoGameDTO> cartItems = shoppingCartService.getVideoGamesInCart();

       
        if (cartItems.isEmpty()) {
            flashMessage.warning("No hay juegos en el carrito. Añade juegos antes de realizar el checkout.");
            return "game-cart.jsp";
        }

        HttpSession session = request.getSession();
        CredentialsDTO usernameCredentials = (CredentialsDTO) session.getAttribute("credentialsDTO");

        // Obtener solo los nombres de los videojuegos
        List<String> gameNames = cartItems.stream()
                                         .map(VideoGameDTO::getName)
                                         .collect(Collectors.toList());

        RentalDTO rental = shoppingCartService.processCheckout(gameNames, usernameCredentials);
        models.put("rental", rental);
        flashMessage.success("¡Compra exitosa! Gracias por tu pedido.");

        return "game-cart.jsp";
    }


    
}
