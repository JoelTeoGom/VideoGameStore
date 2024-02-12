package model.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.List;

@Entity
@Table(name = "GAME")
public class Game implements Serializable {

    @Id
    @SequenceGenerator(name="Game_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Game_Gen")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;
    private String console;
    private String adress;
    private String imageURL;
    private String type;  
    
    @JsonbTransient
    @ManyToMany(mappedBy = "videogames", fetch = FetchType.LAZY )
    private List<Rental> rentals;

    private String description;
    private String availability;
    private float weeklyRentalPrice;
   
  
     // Constructor y demás métodos

    public Game() {
        // Constructor por defecto
    }
    public Game(String name, String console, String type, String description, String availability,
                float weeklyRentalPrice, String adresses) {
        this.name = name;
        this.console = console;
        this.type = type;
        this.description = description;
        this.availability = availability;
        this.weeklyRentalPrice = weeklyRentalPrice;
        this.adress = adress;
      
    }

    // Getters y setters 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public float getWeeklyRentalPrice() {
        return weeklyRentalPrice;
    }

    public void setWeeklyRentalPrice(float weeklyRentalPrice) {
        this.weeklyRentalPrice = weeklyRentalPrice;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }



    // Hashcode y equals 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }
}
