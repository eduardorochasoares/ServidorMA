/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.AMFConfigPackage.AMFConfigPackage;
import Model.MeasurementRequest.LinearTVQualifier;
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
public class AmfConfigDAO {

    private static final Connection conn = ConexaoMySQL.getConexaoMySQL();

    public boolean insertAmfConfigDAO(AMFConfigPackage amf, String user) {

        try {
            Statement s = conn.createStatement();
            String sql = "Insert into directive(code, effectiveDateTime) VALUES (?,?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, 3);
            st.setTimestamp(2, amf.getEffectivityDateAndTime());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                if (new ConfigurationPckgRequestDAO().insertConfigPkg(rs.getInt(1), amf)) {
                    return new MeasurementRequestDAO().salvarMeasurementRequest(rs.getInt(1), amf, user);
                } else {
                    return false;
                }

            } else {
                return false;
            }

        } catch (SQLException ex) {

            Logger.getLogger(AmfConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public ResultSet getMeasurementRequest() {
        String sql;
        Connection conn = ConexaoMySQL.getConexaoMySQL();

        try {
            Statement st = conn.createStatement();
            sql = "Select * from directive order by iddirective DESC";
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ConfigurationPckgRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
