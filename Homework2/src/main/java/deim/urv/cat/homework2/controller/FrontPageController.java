package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.VideoGameDTO;
import deim.urv.cat.homework2.service.VideoGameService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.mvc.View;
import jakarta.mvc.binding.BindingResult;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;
import java.util.logging.Logger;

@Controller
@Path("FrontPage")
public class FrontPageController {
    
    @Inject 
    private BindingResult bindingResult;
    @Inject
    private Models models;
    
   @Inject Logger log;
    
    @Inject
    private VideoGameService videoGameService;

    @GET
    public String showFrontPage() {
        List<VideoGameDTO> allVideoGames = videoGameService.getFilteredVideoGames("", "");
        models.put("videoGames", allVideoGames);
        return "front-page.jsp";
    }

    @POST
    @UriRef("front-page")
    public String filterVideoGames(@BeanParam VideoGameFilterDTO filterDTO) {
        models.put("filtered", filterDTO);
        List<VideoGameDTO> filteredVideoGames = videoGameService.getFilteredVideoGames(filterDTO.getTypeFilter(), filterDTO.getConsoleFilter());
        models.put("videoGames", filteredVideoGames);
        log.info(filteredVideoGames.toString());
        return "front-page.jsp";
    }
}
