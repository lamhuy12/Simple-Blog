/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.feedback;

import java.io.Serializable;
import huyvl.hotel.HotelDTO;

/**
 *
 * @author HUYVU
 */
public class FeedbackDTO implements Serializable{
    private HotelDTO hotelID;
    private String fbRank;
    private String amountOfFb;
    private String rating;

    public FeedbackDTO() {
    }

    public FeedbackDTO(HotelDTO hotelID, String fbRank, String amountOfFb, String rating) {
        this.hotelID = hotelID;
        this.fbRank = fbRank;
        this.amountOfFb = amountOfFb;
        this.rating = rating;
    }

    

    public String getAmountOfFb() {
        return amountOfFb;
    }

    public String getFbRank() {
        return fbRank;
    }


    public HotelDTO getHotelID() {
        return hotelID;
    }

    public String getRating() {
        return rating;
    }
    
}
