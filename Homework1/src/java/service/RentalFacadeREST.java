package service;

import authn.Secured;
import java.util.Date;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Calendar;
import model.entities.Game;
import model.entities.Rental;

@Stateless
@Path("rental")
public class RentalFacadeREST extends AbstractFacade<Rental> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public RentalFacadeREST() {
        super(Rental.class);
    }

@POST
@Secured
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response createRental(List<String> gameNames) {
    try {
        Rental rental = new Rental();
        Date rentalDate = new Date();
        rental.setRentalDate(rentalDate);
        
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date returnDate = calendar.getTime();
        rental.setReturnDate(returnDate);

        // Obtenemos los juegos que existan y los guardamos en la lista de juegos de alquiler
        List<Game> games = new ArrayList<>();
        for (String gameName : gameNames) {
            Game existingGame = findGameByName(gameName);
            if (existingGame != null && existingGame.getAvailability().equals("yes")) {
                games.add(existingGame);
            }
        }
        double precio = calcularPrecioTotal( games, rentalDate,  returnDate);
        rental.setTotalPrice(precio);
        rental.setVideogames(games);
        super.create(rental);

        // Actualizamos la disponibilidad de los juegos a "no"
        updateGamesAvailability(games, "no");

        return Response.status(Response.Status.CREATED)
                        .entity(rental)  // Retorna el objeto Rental completo
                        .build();

    } catch (Exception e) {
        // Manejo de errores
        e.printStackTrace();
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error en la creación del alquiler").build();
    }
}

// Método para actualizar la disponibilidad de los juegos en la base de datos
private void updateGamesAvailability(List<Game> games, String availability) {
    for (Game game : games) {
        game.setAvailability(availability);
        em.merge(game);
    }
}

    @GET
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRentalDetails(@PathParam("id") Long id) {
        try {
            // Buscar el alquiler por ID
            Rental rental = super.find(id);

            if (rental == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontró el alquiler con ID: " + id).build();
            }
            // Devolver los detalles del alquiler
            return Response.status(Response.Status.OK).entity(rental).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener los detalles del alquiler").build();
        }
    }

 
    // Método para calcular el precio total (calculamos x semanas)
    private double calcularPrecioTotal(List<Game> games, Date rentalDate, Date returnDate) {
        double precioTotal = 0;
        if (rentalDate != null && returnDate != null) {
            long diffInMillis = returnDate.getTime() - rentalDate.getTime();
            int semanasAlquiler = (int) (diffInMillis / (7 * 24 * 60 * 60 * 1000));
            for (Game game : games) {
                precioTotal += game.getWeeklyRentalPrice() * semanasAlquiler;
            }
        }
        return precioTotal;
    }

    // Método para buscar un juego por nombre
    private Game findGameByName(String name) {
        try {
            TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g WHERE g.name = :name", Game.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        }
    }
    


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
}
