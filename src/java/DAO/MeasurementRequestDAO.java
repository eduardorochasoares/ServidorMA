/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.AMFConfigPackage.AMFConfigPackage;
import Model.MeasurementRequest.MeasurementRequest;
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
public class MeasurementRequestDAO {

    public boolean salvarMeasurementRequest(int idAmf, AMFConfigPackage amf, String user) {
        Connection conn = ConexaoMySQL.getConexaoMySQL();

        MeasurementRequest mr = amf.getMeasurementRequestSets().get(0).getMeasurementRequest().get(0);

        try {
            Statement s = conn.createStatement();
            String sql = "SELECT id from user where login ='" + user + "'";
            ResultSet rs = s.executeQuery(sql);
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("id");
            }
            sql = "Insert into measurementrequest (obscuration, viewmode, controldevice, navmethod, id_user, id_directive)"
                    + "Values (?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, mr.getLinearTVQualifier().getObscuration().getCode());
            st.setInt(2, mr.getLinearTVQualifier().getViewMode().getCode());
            st.setInt(3, mr.getLinearTVQualifier().getControlDevice().getValorCodigo());
            st.setInt(4, mr.getLinearTVQualifier().getNavMethod().getCode());
            st.setInt(5, id);
            st.setInt(6, idAmf);

            st.execute();
            rs = st.getGeneratedKeys();
            int mrID = 0;
            if (rs.next()) {
                mrID = rs.getInt(1);
            }
            conn.close();
            return new CanaisDAO().insertChannels(mr.getLinearTVQualifier(), mrID) && new MeasurementScheduleDAO().salvarSchedule(mr.getMeasurementSchedule().get(0), mrID);

        } catch (SQLException ex) {
            Logger.getLogger(MeasurementRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public ResultSet getMeasurementRequest(String user) {
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        String query;
        query = "select user.id  from user where login ='" + user + "'";

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("id");
            }
            query = "Select measurementrequest.id, measurementrequest.id_user, measurementrequest.id_directive, measurementrequest.obscuration, measurementrequest.viewmode, "
                    + "measurementrequest.controldevice, measurementrequest.navmethod, measurementschedule.id from measurementrequest inner join measurementschedule on measurementschedule.id_measurementrequest = measurementrequest.id where measurementrequest.id_user ='" + id + "' order by measurementrequest.id DESC";
            s = conn.createStatement();
            rs = s.executeQuery(query);

            return rs;

        } catch (SQLException ex) {
            Logger.getLogger(MeasurementRequestDAO.class
                    .getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

    public ResultSet getPeriod(int idRequest) {
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        String query = "Select dayoftheweek.dia,dayoftheweek.id , measurementschedule.id , measurementschedule.id_measurementrequest,measurementperiod.id_schedule, measurementperiod.id_schedule, measurementperiod.id_dia from dayoftheweek inner join measurementschedule on measurementschedule.id_measurementrequest ='" + idRequest + "' inner join measurementperiod on measurementperiod.id_schedule = measurementschedule.id where dayoftheweek.id = measurementperiod.id_dia";

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);

            return rs;

        } catch (SQLException ex) {
            Logger.getLogger(MeasurementRequestDAO.class
                    .getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

    public ResultSet getHours(int measurementSched) {
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        String query = "select startTime, endTime from horario where id_period ='" + measurementSched + "'";
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            return rs;

        } catch (SQLException ex) {
            Logger.getLogger(MeasurementRequestDAO.class
                    .getName()).log(Level.SEVERE, null, ex);

            return null;
        }

    }

   
}
