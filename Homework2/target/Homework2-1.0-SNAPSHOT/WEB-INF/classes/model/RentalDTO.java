/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deim.urv.cat.homework2.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author joelt
 */
public class RentalDTO {
    private Long id;
    private List<String> videogames;  // Assuming this is a list of video game names
    private Date rentalDate;
    private Date returnDate;
    private double totalPrice;

    // Constructores, getters y setters
    public RentalDTO() {
    
    }
    
    public RentalDTO(Long id, List<String> videogames,  Date rentalDate, Date returnDate, double totalPrice) {
        this.id = id;
        this.videogames = videogames;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;

    }

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

    public List<String> getVideogames() {
        return videogames;
    }

    public void setVideogames(List<String> videogames) {
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
}
