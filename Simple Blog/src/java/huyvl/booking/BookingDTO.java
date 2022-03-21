/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.booking;

import java.io.Serializable;
import huyvl.registration.AccountDTO;
import huyvl.discount.DiscountDTO;

/**
 *
 * @author HUYVU
 */
public class BookingDTO implements Serializable{
    private String bookingID;
    private AccountDTO account;
    private String bookingDate;
    private DiscountDTO discount;
    private String totalCostBeforeDiscount;
    private String totalCostAfterDiscount;
    private boolean status;
    private int rating;

    public BookingDTO() {
    }

    public BookingDTO(String bookingID, AccountDTO account, String bookingDate, DiscountDTO discount, String totalCostBeforeDiscount, String totalCostAfterDiscount, boolean status, int rating) {
        this.bookingID = bookingID;
        this.account = account;
        this.bookingDate = bookingDate;
        this.discount = discount;
        this.totalCostBeforeDiscount = totalCostBeforeDiscount;
        this.totalCostAfterDiscount = totalCostAfterDiscount;
        this.status = status;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    

    public String getTotalCostAfterDiscount() {
        return totalCostAfterDiscount;
    }

    public String getTotalCostBeforeDiscount() {
        return totalCostBeforeDiscount;
    }

    

    public String getBookingID() {
        return bookingID;
    }

    

    public AccountDTO getAccount() {
        return account;
    }

    

    public String getBookingDate() {
        return bookingDate;
    }

    public DiscountDTO getDiscount() {
        return discount;
    }


    

    public boolean isStatus() {
        return status;
    }
    
    
}
