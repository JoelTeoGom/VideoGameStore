package service;

import authn.Secured;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.Query;
import model.entities.Game;

@Stateless
@Path("game")
public class GameFacadeREST extends AbstractFacade<Game> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public GameFacadeREST() {
        super(Game.class);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFilteredGames(
            @QueryParam("type") String type,
            @QueryParam("console") String console
    ) {
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT g FROM Game g WHERE 1 = 1");

            if (type != null && !type.isEmpty()) {
                queryBuilder.append(" AND g.type = :type");
            }

            if (console != null && !console.isEmpty()) {
                queryBuilder.append(" AND g.console = :console");
            }

            Query query = em.createQuery(queryBuilder.toString(), Game.class);

            if (type != null && !type.isEmpty()) {
                query.setParameter("type", type);
            }

            if (console != null && !console.isEmpty()) {
                query.setParameter("console", console);
            }

            List<Game> games = query.getResultList();

            return Response.ok(games).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching games").build();
        }
    }

    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createGame(Game game) {
        try {
            // Verificar si el juego ya existe
            Query query = em.createQuery("SELECT COUNT(g) FROM Game g WHERE g.name = :name");
            query.setParameter("name", game.getName());
            long count = (long) query.getSingleResult();

            if (count > 0) {
                return Response.status(Response.Status.CONFLICT).entity("El juego ya existe").build();
            }
            super.create(game);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la creación del juego").build();
        }
    }
    
    

    @GET
    @Path("/byname")
   // @Secured
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getGameByName(@QueryParam("name") String name) {
        try {
            if (name != null) {
                Query query = em.createQuery("SELECT g FROM Game g WHERE g.name = :name");
                query.setParameter("name", name);

                List<Game> result = query.getResultList();

                if (result.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Juego no encontrado").build();
                } else {
                    return Response.ok(result.get(0)).build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("El parámetro 'name' es requerido").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la búsqueda del juego por nombre").build();
        }
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
