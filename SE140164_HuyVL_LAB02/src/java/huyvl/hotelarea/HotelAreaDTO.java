/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.hotelarea;

import java.io.Serializable;

/**
 *
 * @author HUYVU
 */
public class HotelAreaDTO implements Serializable{
    private String areaName;

    public HotelAreaDTO() {
    }

    public HotelAreaDTO(String areaName) {
        this.areaName = areaName;
    }


    public String getAreaName() {
        return areaName;
    }
    
    
}
