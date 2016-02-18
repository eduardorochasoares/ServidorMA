/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.AmfConfigDAO;
import DAO.ConfigurationPckgRequestDAO;
import DAO.MeasurementRequestDAO;
import Model.AMFConfigPackage.AMFConfigPackage;
import Model.AMFConfigPackage.MeasurementRequestSet;
import Model.ConfigPackageRequestResponse.Code;
import Model.ConfigPackageRequestResponse.ConfigPackageRequestResponse;
import Model.ConfigPackageRequestResponse.Directive;
import Model.MeasurementRequest.ChannelList;
import Model.MeasurementRequest.ChannelQualifier;
import Model.MeasurementRequest.ControlDevice;
import Model.MeasurementRequest.DayOfTheWeek;
import Model.MeasurementRequest.LinearTVQualifier;
import Model.MeasurementRequest.MeasurementPeriod;
import Model.MeasurementRequest.MeasurementRequest;
import Model.MeasurementRequest.MeasurementSchedule;
import Model.MeasurementRequest.NavMethod;
import Model.MeasurementRequest.Obscuration;
import Model.MeasurementRequest.TimeTrigger;
import Model.MeasurementRequest.ViewMode;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Principal
 */
public class CriarRequestController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        LinearTVQualifier ltq = new LinearTVQualifier();
        String controlDevice = request.getParameter("controlDevice");

        String aux[] = controlDevice.split(" " + "-");
        String a = request.getParameter("nav");
        HttpSession s = request.getSession();
        String user = (String) s.getAttribute("user");

        int codigo = Integer.parseInt(aux[0]);
        switch (codigo) {
            case 0:
                ltq.setControlDevice(ControlDevice.STBControl);
                break;
            case 1:
                ltq.setControlDevice(ControlDevice.STB);
                break;
            case 2:
                ltq.setControlDevice(ControlDevice.STBKeyboard);
                break;
            case 10:
                ltq.setControlDevice(ControlDevice.PC);
                break;
            case 20:
                ltq.setControlDevice(ControlDevice.TABLET);
                break;

            case 30:
                ltq.setControlDevice(ControlDevice.MOBILEFONE);
                break;
            case 40:
                ltq.setControlDevice(ControlDevice.OTHER);
                break;
        }
        String viewMode = request.getParameter("viewMode");
        if (viewMode == null) {
            ltq.setViewMode(ViewMode.DoNotReport);
        } else {
            ltq.setViewMode(ViewMode.Report);
        }
        String obscuration = request.getParameter("obscuration");
        if (obscuration == null) {
            ltq.setObscuration(Obscuration.DoNotReport);

        } else {
            ltq.setObscuration(Obscuration.Report);
        }

        if (a == null) {
            ltq.setNavMethod(NavMethod.doNotReport);

        } else {
            ltq.setNavMethod(NavMethod.report);
        }

        String c[] = request.getParameterValues("canais");
        if (c != null) {
            ChannelList channelList = new ChannelList();
            ChannelQualifier cq = new ChannelQualifier();
            ltq.setChannelQualifier(cq);
            ltq.getChannelQualifier().setChannelList(channelList);
            channelList.getServiceIdentifier().addAll(Arrays.asList(c));
        }
        MeasurementRequest mr = new MeasurementRequest(ltq);
        String days[] = request.getParameterValues("days");

        MeasurementSchedule ms = new MeasurementSchedule();

        mr.getMeasurementSchedule().add(ms);
        MeasurementPeriod mp = new MeasurementPeriod();
        ms.getMeasurementPeriod().add(mp);

        String startTime = request.getParameter("startTime");
        String timeString[] = startTime.split("/");
        for (String time : timeString) {
            mp.getStartTime().add(stringTime(time));
        }
        String endTimeString[] = request.getParameter("endTime").split("/");
        for (String time : endTimeString) {
            mp.getEndTime().add(stringTime(time));
        }
        for (String dias : days) {
            String split[] = dias.split(" " + "-");
            int cod = Integer.parseInt(split[0]);
            switch (cod) {
                case 0:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Everyday);
                    break;
                case 1:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Monday);

                    break;
                case 2:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Tuesday);

                    break;
                case 3:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Wednesday);

                    break;
                case 4:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Thursday);

                    break;
                case 5:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Friday);

                    break;
                case 6:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Saturday);

                    break;
                case 7:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Sunday);

                    break;
                case 8:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.WeekDay);

                    break;
                case 9:
                    mp.getDayOfTheWeek().add(DayOfTheWeek.Weekend);

                    break;
            }
        }

        TimeTrigger tt = new TimeTrigger();
        tt.setPeriodicity(Integer.parseInt(request.getParameter("periodicity")));
        ms.setTimeTrigger(tt);
        String efetividade = request.getParameter("effectiveTime");
        AMFConfigPackage amConfig = new AMFConfigPackage();
        if (efetividade != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                Date parsedDate = dateFormat.parse(efetividade);

                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                amConfig.setEffectivityDateAndTime(timestamp);

            } catch (Exception e) {//this generic but you can control another types of exception
                System.err.println(e.getMessage());
            }
        }

        MeasurementRequestSet mrs = new MeasurementRequestSet();
        mrs.getMeasurementRequest().add(mr);
        amConfig.getMeasurementRequestSets().add(mrs);
        ConfigPackageRequestResponse cp = null;

        if (new AmfConfigDAO().insertAmfConfigDAO(amConfig, user)) {

            ResultSet rs = new ConfigurationPckgRequestDAO().getConfigRequestPkg();
            ResultSet requestRs = new MeasurementRequestDAO().getMeasurementRequest(user);
            try {
                if(requestRs.next()){
                    amConfig.setPackageID(requestRs.getInt("id_directive"));
                    amConfig.setPackageVersion(1);
                    mr.setMeasurementRequestID(requestRs.getInt("id"));
                    
                    //amConfig.getMeasurementRequestSets().get(0).getMeasurementRequest().get(0).setMeasurementRequestID(requestRs.getInt("id"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(CriarRequestController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (rs.next()) {
                    if (amConfig.getEffectivityDateAndTime() != null) {
                        Directive d = new Directive();
                        d.setaMFConfigPackage(amConfig);
                        d.setCode(Code.ConfPackagePresent);

                        cp = new ConfigPackageRequestResponse(null, d, rs.getInt("configCheckDelay"));

                    } else {
                        Directive d = new Directive();
                        d.setaMFConfigPackage(amConfig);
                        d.setCode(Code.ConfPackagePresent);
                        cp = new ConfigPackageRequestResponse(d, null, rs.getInt("configCheckDelay"));
                    }
                }
                if (cp != null) {
                    cp.createConfigPkgRequestResponseXML();
                    response.sendRedirect("index.jsp");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CriarRequestController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private Time stringTime(String time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        try {
            java.sql.Time timeValue = new java.sql.Time(formatter.parse(time).getTime());
            return timeValue;
        } catch (ParseException ex) {
            Logger.getLogger(SvConfigRequestResponse.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
