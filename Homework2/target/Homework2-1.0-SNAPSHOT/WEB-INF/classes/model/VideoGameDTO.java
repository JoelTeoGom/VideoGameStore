/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deim.urv.cat.homework2.model;

import java.io.Serializable;
import java.util.List;
import jdk.internal.joptsimple.internal.Strings;

/**
 *
 * @author joelt
 */


public class VideoGameDTO implements Serializable {
    private String name;
    private String console;
    private String availability;
    private double weeklyRentalPrice;
    private String imageURL;
    private String description;
    private String adress;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    private String type;  
    // Constructores, getters y setters

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public double getWeeklyRentalPrice() {
        return weeklyRentalPrice;
    }

    public void setWeeklyRentalPrice(double weeklyRentalPrice) {
        this.weeklyRentalPrice = weeklyRentalPrice;
    }
    
    
    
}
