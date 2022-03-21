/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.discount;

import java.io.Serializable;

/**
 *
 * @author HUYVU
 */
public class DiscountDTO implements Serializable{
    private String discountID;
    private String discountName;
    private String discountCost;
    private String expirationDate;

    public DiscountDTO() {
    }

    public DiscountDTO(String discountID, String discountName, String discountCost, String expirationDate) {
        this.discountID = discountID;
        this.discountName = discountName;
        this.discountCost = discountCost;
        this.expirationDate = expirationDate;
    }

    public String getDiscountID() {
        return discountID;
    }

    

    public String getDiscountCost() {
        return discountCost;
    }

    public String getDiscountName() {
        return discountName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
    
}
