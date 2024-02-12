/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deim.urv.cat.homework2.service;

/**
 *
 * @author joelt
 */
// VideoGameService.java
import java.util.List;
import deim.urv.cat.homework2.model.VideoGameDTO;

public interface VideoGameService {

    List<VideoGameDTO> getFilteredVideoGames(String typeFilter, String consoleFilter);
    VideoGameDTO getGameDetailsByName(String name);

    
}
