package model.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RENTALS")
public class Rental implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @JsonbTransient
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "RENTAL_VIDEOGAME",
            joinColumns = @JoinColumn(name = "RENTAL_ID"),
            inverseJoinColumns = @JoinColumn(name = "VIDEOGAME_ID"))
    private List<Game> videogames;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
    
    private Date rentalDate;
    private Date returnDate;
    private double totalPrice;

    // Constructores y demás métodos

    public Rental() {
        // Constructor por defecto
    }

    public Rental(List<Game> videogames, Customer customer, Date rentalDate, Date returnDate, double totalprice) {
        this.videogames = videogames;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;

    }

    // Getters y setters

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Game> getVideogames() {
        return videogames;
    }

    public void setVideogames(List<Game> videogames) {
        this.videogames = videogames;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }




    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rentalDate, returnDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rental rental = (Rental) obj;
        return id != null && id.equals(rental.id);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", customer=" + customer +
                '}';
    }
}
