/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.MeasurementRequest.ChannelList;
import Model.MeasurementRequest.LinearTVQualifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Principal
 */
public class CanaisDAO {

    private static final Connection conn = ConexaoMySQL.getConexaoMySQL();

    public static ChannelList getChannels() {
        ChannelList cl = new ChannelList();
        String query = "SELECT * FROM channels";

        // create the java statement
        Statement st;
        try {
            st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                int id = rs.getInt("id");
                String s = rs.getString("canais");
                cl.getServiceIdentifier().add(s);

                // print the results
            }

            return cl;
        } catch (SQLException ex) {
            Logger.getLogger(CanaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    public boolean insertChannels(LinearTVQualifier ltq, int id) {

        try {

            Statement s = conn.createStatement();

            for (String c : ltq.getChannelQualifier().getChannelList().getServiceIdentifier()) {
                
                String sql = "Select id from channels where canais ='"+c+"'";
                ResultSet rs = s.executeQuery(sql);
                if (rs.next()) {
                    sql = "Insert into channellist(id_channel, id_tvLinear) VALUES (?,?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    st.setInt(1,rs.getInt(1));
                    st.setInt(2, id);
                    st.execute();
                   
                }

            }
            return true;
        
        } catch (SQLException ex) {
            Logger.getLogger(CanaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
}
