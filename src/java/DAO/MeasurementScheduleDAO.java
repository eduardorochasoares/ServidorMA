/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.MeasurementRequest.MeasurementSchedule;
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
public class MeasurementScheduleDAO {

    public boolean salvarSchedule(MeasurementSchedule ms, int id_request) {
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        String sql = "INSERT into measurementschedule (id_measurementrequest) VALUES (?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, id_request);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                if (new TimeTriggerDAO().insertTT(ms.getTimeTrigger(), rs.getInt(1))) {
                    return new MeasurementPeriodDAO().insertMeasurementPeriod(ms.getMeasurementPeriod().get(0), rs.getInt(1));

                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MeasurementScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }
}
