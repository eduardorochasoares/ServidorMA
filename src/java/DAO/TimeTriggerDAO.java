/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.AMFConfigPackage.AMFConfigPackage;
import Model.MeasurementRequest.TimeTrigger;
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
public class TimeTriggerDAO {

    private static final Connection conn = ConexaoMySQL.getConexaoMySQL();

    public boolean insertTT(TimeTrigger tt, int idSched) {

        try {
            Statement s = conn.createStatement();
            String sql = "Insert into timetrigger(periodicity, id_measurement_sched) VALUES (?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, tt.getPeriodicity());
            st.setInt(2, idSched);
            st.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(AmfConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
}
