/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.hotel;

import java.io.Serializable;
import huyvl.hotelarea.HotelAreaDTO;

/**
 *
 * @author HUYVU
 */
public class HotelDTO implements Serializable{
    private String hotelID;
    private String hotelName;
    private String hotelImage;
    private String hotelAddress;
    private HotelAreaDTO hotelArea;

    public HotelDTO() {
    }

    public HotelDTO(String hotelID, String hotelName, String hotelImage, String hotelAddress, HotelAreaDTO hotelArea) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.hotelImage = hotelImage;
        this.hotelAddress = hotelAddress;
        this.hotelArea = hotelArea;
    }

    public String getHotelID() {
        return hotelID;
    }


    public String getHotelAddress() {
        return hotelAddress;
    }

    public HotelAreaDTO getHotelArea() {
        return hotelArea;
    }

    public String getHotelImage() {
        return hotelImage;
    }

    public String getHotelName() {
        return hotelName;
    }

    @Override
    public String toString() {
        return "HotelDTO{" + "hotelID=" + hotelID + ", hotelName=" + hotelName + ", hotelImage=" + hotelImage + ", hotelAddress=" + hotelAddress + ", hotelArea=" + hotelArea + '}';
    }


            
            
}
