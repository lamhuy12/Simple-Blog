/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.bookingdetail;

import java.io.Serializable;
import huyvl.room.RoomDTO;

/**
 *
 * @author HUYVU
 */
public class BookingDetailDTO implements Serializable{
    private String bookingDetailID;
    private String bookingID;
    private RoomDTO room;
    private String checkinDate;
    private String checkoutDate;

    public BookingDetailDTO() {
    }

    public BookingDetailDTO(String bookingDetailID, String bookingID, RoomDTO room, String checkinDate, String checkoutDate) {
        this.bookingDetailID = bookingDetailID;
        this.bookingID = bookingID;
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public String getBookingDetailID() {
        return bookingDetailID;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    

    public String getBookingID() {
        return bookingID;
    }

    public RoomDTO getRoom() {
        return room;
    }

    
}
