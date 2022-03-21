/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.roomtype;

import java.io.Serializable;

/**
 *
 * @author HUYVU
 */
public class RoomTypeDTO implements Serializable{
    private String typeName;

    public RoomTypeDTO() {
    }

    public RoomTypeDTO(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
    
}
