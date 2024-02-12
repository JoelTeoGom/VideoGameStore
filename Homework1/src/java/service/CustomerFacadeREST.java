package service;

import authn.Credentials;
import authn.Secured;
import model.entities.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import model.entities.Rental;

@Stateless
@Path("customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        try {
            super.create(customer);
            // Crear las credenciales
            Credentials credentials = new Credentials(customer.getUsername(), customer.getPassword());
            em.persist(credentials);

            
            return Response.status(Response.Status.CREATED)
                    .entity("Nuevo cliente creado ")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error en la creación del cliente").build();
        }
    }
    

@GET
@Produces(MediaType.APPLICATION_JSON)
public Response getAllCustomers() {
    try {
        List<Customer> customers = findAll();

        // Detach entities to prevent persistence of changes
        for (Customer customer : customers) {
            em.detach(customer);
            customer.setPassword(null);
        }

        return Response.ok(customers).build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al obtener la lista de clientes").build();
    }
}

@GET
@Path("/{id}")
@Produces(MediaType.APPLICATION_JSON)
public Response getCustomerById(@PathParam("id") Long id) {
    try {
        Customer customer = find(id);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se encontró el cliente con ID: " + id).build();
        }

        // Detach entity to prevent persistence of changes
        em.detach(customer);
        customer.setPassword(null);

        return Response.ok(customer).build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al obtener los detalles del cliente").build();
    }
}

    @PUT
    @Path("/{id}")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") Long id, Customer updatedCustomer) {
        try {
            Customer existingCustomer = find(id);

            if (existingCustomer == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontró el cliente con ID: " + id).build();
            }

            // Actualizar los datos del cliente
            existingCustomer.setUsername(updatedCustomer.getUsername());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());

            // Puedes agregar más campos según sea necesario

            edit(existingCustomer);

            return Response.ok(existingCustomer).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar los detalles del cliente").build();
        }
    }
    
    
    @GET
    @Path("/{id}/rentals")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRentalsByCustomerId(@PathParam("id") Long id) {
        try {
            // Obtener el cliente por ID
            Customer customer = find(id);

            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontró el cliente con ID: " + id).build();
            }

            // Obtener la lista de rentals asociados al cliente
            List<Rental> customerRentals = customer.getRentals();

            // Detach entities para evitar la persistencia de cambios
            for (Rental rental : customerRentals) {
                em.detach(rental);
            }

            return Response.ok(customerRentals).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener los rentals del cliente").build();
        }
    }


}