/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.room;

import java.io.Serializable;
import huyvl.hotel.HotelDTO;
import huyvl.roomtype.RoomTypeDTO;

/**
 *
 * @author HUYVU
 */
public class RoomDTO implements Serializable{
    private String roomID;
    private String roomImage;
    private HotelDTO roomHotel;
    private RoomTypeDTO roomType;
    private String roomPrice;

    public RoomDTO() {
    }

    public RoomDTO(String roomID, String roomImage, HotelDTO roomHotel, RoomTypeDTO roomType, String roomPrice) {
        this.roomID = roomID;
        this.roomImage = roomImage;
        this.roomHotel = roomHotel;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }
    
    

    public String getRoomID() {
        return roomID;
    }

    

    public String getRoomImage() {
        return roomImage;
    }

    

    public HotelDTO getRoomHotel() {
        return roomHotel;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public RoomTypeDTO getRoomType() {
        return roomType;
    }
    
    
}
