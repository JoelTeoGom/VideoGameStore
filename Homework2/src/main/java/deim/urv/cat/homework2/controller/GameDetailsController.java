package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.VideoGameDTO;
import deim.urv.cat.homework2.service.VideoGameService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.mvc.binding.BindingResult;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.logging.Logger;

/**
 *
 * @author joelt
 */
@Controller
@Path("GameDetails")
public class GameDetailsController {

    @Inject 
    private Models models;

    @Inject Logger log;

    @Inject
    private VideoGameService videoGameService;

    @GET
    @UriRef("game-details")
    public String showGameDetails(@QueryParam("name") String name) {
        log.info("GAME DETAILS: "+name);
        VideoGameDTO gameDetails = videoGameService.getGameDetailsByName(name);
        
        if (gameDetails == null) {
            // Manejar el caso en el que el juego no se encuentra
            return "error-page.jsp";
        }

        models.put("gameDetails", gameDetails);
        
        return "game-details.jsp";
    }
    
    
    

}



