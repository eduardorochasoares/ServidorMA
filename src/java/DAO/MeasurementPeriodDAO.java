/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.MeasurementRequest.DayOfTheWeek;
import Model.MeasurementRequest.MeasurementPeriod;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Principal
 */
public class MeasurementPeriodDAO {

    public boolean insertMeasurementPeriod(MeasurementPeriod mp, int id) {
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        PreparedStatement st = null;
        String sql;
        try {
            for (DayOfTheWeek d : mp.getDayOfTheWeek()) {
                String query = "Select id from dayoftheweek where codigo ='" + d.getCode() + "'";
                Statement s = conn.createStatement();
               
                ResultSet rs =  s.executeQuery(query);
                sql = "INSERT into measurementperiod (id_dia, id_schedule) VALUES (?,?)";
                while (rs.next()) {
                    st = conn.prepareStatement(sql);
                    st.setInt(1, rs.getInt("id"));
                    st.setInt(2, id);
                    st.executeUpdate();
                }

            }

            for (int i = 0; i < mp.getEndTime().size(); i++) {
                sql = "INSERT into horario (startTime, endTime, id_period) VALUES (?,?,?)";
                st = conn.prepareStatement(sql);
                st.setTime(1, mp.getStartTime().get(i));
                st.setTime(2, mp.getEndTime().get(i));
                st.setInt(3, id);
                st.executeUpdate();

            }
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MeasurementPeriodDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
}
