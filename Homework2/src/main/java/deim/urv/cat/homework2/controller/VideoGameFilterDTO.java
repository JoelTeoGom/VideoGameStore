package deim.urv.cat.homework2.controller;

import jakarta.mvc.binding.MvcBinding;
import jakarta.ws.rs.FormParam;

public class VideoGameFilterDTO {

    @FormParam("typeFilter")
    @MvcBinding
    private String typeFilter;

    @FormParam("consoleFilter")
    @MvcBinding
    private String consoleFilter;

    public String getTypeFilter() {
        return typeFilter;
    }

    public String getConsoleFilter() {
        return consoleFilter;
    }
    
 
}
