/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.AMFConfigPackage.AMFConfigPackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Principal
 */
public class ConfigurationPckgRequestDAO {

    private static final Connection conn = ConexaoMySQL.getConexaoMySQL();

    public boolean insertConfigPkg(int amfID, AMFConfigPackage amf) {
        String sql;
        try {
            Statement s = conn.createStatement();
            if (amf.getEffectivityDateAndTime() != null) {
                sql = "Insert into configpkgrequestresponse(configCheckDelay, id_future) VALUES (?,?)";
            } else {
                sql = "Insert into configpkgrequestresponse(configCheckDelay, id_immediate) VALUES (?,?)";
            }

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, 1);
            st.setInt(2, amfID);
            st.executeUpdate();
           return true;

        } catch (SQLException ex) {

            Logger.getLogger(AmfConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    public ResultSet getConfigRequestPkg(){
        String sql;
        try {
            Statement st = conn.createStatement();
            sql = "Select * from configpkgrequestresponse order by idconfigPkgRequestresponse DESC";
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ConfigurationPckgRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
