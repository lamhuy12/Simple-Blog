/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.feedback;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import huyvl.hotel.HotelDAO;
import huyvl.ultites.DBHelper;

/**
 *
 * @author HUYVU
 */
public class FeedbackDAO implements Serializable{
    private List<FeedbackDTO> fbList;
    public List<FeedbackDTO> getFBList(){
        return this.fbList;
    }
    public void getAllFeedback()
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT DISTINCT hotelID, COUNT(rating) as amount, AVG(rating) as rating "
                        + "FROM tblFeedback "
                        + "GROUP BY hotelID";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    HotelDAO dao = new HotelDAO();
                    String hotelID = rs.getString("hotelID");
                    String amount = rs.getString("amount");
                    float rating = rs.getFloat("rating");
                    String rank = "";
                    if(rating>=0 && rating<=1){
                        rank = "";
                    }
                    if(rating>=1 && rating<=2){
                        rank = "";
                    }
                    if(rating>=2 && rating<=3){
                        rank = "";
                    }
                    if(rating>=4 && rating<=4){
                        rank = "";
                    }
                    if(rating>=5 && rating<=5){
                        rank = "";
                    }
                    FeedbackDTO dto = new FeedbackDTO(dao.getHotelByID(hotelID), rank, amount,Float.toString(rating*2));
                    if(this.fbList == null){
                        this.fbList = new ArrayList<>();
                    }
                    this.fbList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
}
