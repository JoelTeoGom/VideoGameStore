package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.model.CredentialsDTO;
import deim.urv.cat.homework2.model.RentalDTO;
import deim.urv.cat.homework2.model.VideoGameDTO;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.List;

public interface CarritoService {

    List<VideoGameDTO> getVideoGamesInCart();

    void addVideoGameToCart(VideoGameDTO videoGame);

    void removeVideoGameFromCart(VideoGameDTO videoGame);

    RentalDTO processCheckout(List<String> games,CredentialsDTO headers);

    boolean isGameInCart(VideoGameDTO videoGame);
}
