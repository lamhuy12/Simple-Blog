/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HUYVU
 */
public class Cart implements Serializable{
    private Map<Integer, CartObj> items;

    public Map<Integer, CartObj> getItems() {
        return items;
    }
    int position = 1;

    public void addItemToCard(CartObj cart) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        this.items.put(position, cart);
        position ++;
    }

    public void removeItemFromCart(int id) {
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(id)) {
            this.items.remove(id);
            if(this.items.isEmpty()){
                this.items = null;
            }
        }
    }
    public void updateItemCart(int id, CartObj cart){
        if(this.items == null){
            return;
        }
        if(this.items.containsKey(id)) {
            this.items.replace(id, cart);
        }
    }
}
