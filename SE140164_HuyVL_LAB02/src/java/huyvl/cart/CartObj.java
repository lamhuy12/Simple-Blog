/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.cart;

import java.io.Serializable;
import huyvl.room.RoomDTO;

/**
 *
 * @author HUYVU
 */
public class CartObj implements Serializable{
    RoomDTO room;
    String checkinDate;
    String checkoutDate;
    String amountDate;
    public CartObj() {
    }

    public CartObj(RoomDTO room, String checkinDate, String checkoutDate, String amountDate) {
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.amountDate = amountDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    
    public String getAmountDate() {
        return amountDate;
    }

    
    
    

    public String getCheckinDate() {
        return checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public RoomDTO getRoom() {
        return room;
    }

    
}
