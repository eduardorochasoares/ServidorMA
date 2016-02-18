/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.MeasurementRequest.ChannelList;
import Model.MeasurementRequest.DayOfTheWeek;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Principal
 */
public class DiasDAO {
    private static final Connection conn = ConexaoMySQL.getConexaoMySQL();
    public static List<String> getDias() {
        
        String query = "SELECT * FROM dayoftheweek";
        List<String> days = new ArrayList<>();
        // create the java statement
        Statement st;
        try {
            st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                
                String s = rs.getString("dia");
                int i = rs.getInt("codigo");
                days.add(i + " " +"-"+ " "+ s);

                // print the results
            }
            return days;
        } catch (SQLException ex) {
            Logger.getLogger(CanaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }
}
