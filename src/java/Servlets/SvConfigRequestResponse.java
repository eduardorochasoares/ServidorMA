/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Model.AMFConfigPackage.AMFConfigPackage;
import Model.AMFConfigPackage.MeasurementRequestSet;
import Model.ConfigPackageRequestResponse.Code;
import Model.ConfigPackageRequestResponse.ConfigPackageRequestResponse;
import Model.ConfigPackageRequestResponse.Directive;
import Model.MeasurementRequest.ChannelList;
import Model.MeasurementRequest.ChannelQualifier;
import Model.MeasurementRequest.ControlDevice;
import Model.MeasurementRequest.DayOfTheWeek;
import Model.MeasurementRequest.ImmediatePush;
import Model.MeasurementRequest.LinearTVQualifier;
import Model.MeasurementRequest.MeasurementDeliverySchedule;
import Model.MeasurementRequest.MeasurementPeriod;
import Model.MeasurementRequest.MeasurementRequest;
import Model.MeasurementRequest.MeasurementSchedule;
import Model.MeasurementRequest.NavMethod;
import Model.MeasurementRequest.Obscuration;
import Model.MeasurementRequest.TimeTrigger;
import Model.MeasurementRequest.ViewMode;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
public class SvConfigRequestResponse extends HttpServlet {

    public static Connection con = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        String usuario = ""; // É bom iniciar a variável ANTES de pegar o valor da sessão
        usuario = (String) sessao.getAttribute("user");
        if (usuario != null) {
            PrintWriter out = response.getWriter();

            Directive im = setImmediate(request, out);
            Directive ft = setFuture(request);
            ConfigPackageRequestResponse cg = new ConfigPackageRequestResponse(im, ft, Integer.parseInt(request.getParameter("delay")));
            cg.createConfigPkgRequestResponseXML();

            out.print("<div style = 'margin-left : 40%' >Succeeded in creating XML document!</div>");
        }else{
             response.sendRedirect("login.jsp");
        }
    }

    public Directive setImmediate(HttpServletRequest request, PrintWriter out) {
        String opc = request.getParameter("opc");
        String[] s = opc.split(" " + "-");
        int code = Integer.parseInt(s[0]);
        Directive im = new Directive();

        switch (code) {
            case 1:
                im.setCode(Code.NoAmRequired);
                break;
            case 2:
                im.setCode(Code.NoConfPackageChange);
                break;
            case 3:
                im.setCode(Code.ConfPackagePresent);
                LinearTVQualifier ltq = new LinearTVQualifier();
                AMFConfigPackage amConfig = new AMFConfigPackage();
                im.setaMFConfigPackage(amConfig);
                String a = request.getParameter("nav");
                MeasurementRequest mr = new MeasurementRequest(ltq);
                MeasurementRequestSet mrs = new MeasurementRequestSet();
                mrs.getMeasurementRequest().add(mr);
                mr.setLinearTVQualifier(ltq);
                mr.setMeasurementRequestID(1);
                amConfig.getMeasurementRequestSets().add(mrs);
                amConfig.setPackageID(1);
                amConfig.setPackageVersion(1);
                String efetividade = request.getParameter("dataEfetividade");

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    Date parsedDate = dateFormat.parse(efetividade);

                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    amConfig.setEffectivityDateAndTime(timestamp);

                } catch (Exception e) {//this generic but you can control another types of exception
                    System.err.println(e.getMessage());
                }
                String controlDevice = request.getParameter("controlDevice");

                String aux[] = controlDevice.split(" " + "-");

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

                MeasurementDeliverySchedule msd = new MeasurementDeliverySchedule();

                mr.setMeasurementDeliverySchedule(msd);
                String deliveryAddress[] = request.getParameter("deliveryAddres").split("/");
                msd.getDeliveryAddres().addAll(Arrays.asList(deliveryAddress));
                ImmediatePush ip = new ImmediatePush();
                msd.setImeImmediatePush(ip);
                msd.setRetransmitNumber(Integer.parseInt(request.getParameter("retransmitNumber")));
                ip.setMeasurementReportNumberByPush(Integer.parseInt(request.getParameter("reportNumberByPush")));
                ip.setMaxTimeBetweenDelivery(Integer.parseInt(request.getParameter("maxtime")));

                break;

        }

        return im;
    }

    public Directive setFuture(HttpServletRequest request) {
        String opc = request.getParameter("opc2");
        String[] s = opc.split(" " + "-");
        int code = Integer.parseInt(s[0]);
        Directive ft = new Directive();

        switch (code) {
            case 1:
                ft.setCode(Code.NoAmRequired);
                break;
            case 2:
                ft.setCode(Code.NoConfPackageChange);
                break;
            case 3:
                ft.setCode(Code.ConfPackagePresent);
                LinearTVQualifier ltq = new LinearTVQualifier();
                AMFConfigPackage amConfig = new AMFConfigPackage();
                ft.setaMFConfigPackage(amConfig);
                String a = request.getParameter("nav2");
                MeasurementRequest mr = new MeasurementRequest(ltq);
                MeasurementRequestSet mrs = new MeasurementRequestSet();
                mrs.getMeasurementRequest().add(mr);
                mr.setLinearTVQualifier(ltq);
                mr.setMeasurementRequestID(1);
                amConfig.getMeasurementRequestSets().add(mrs);
                amConfig.setPackageID(1);
                amConfig.setPackageVersion(1);

                String efetividade = request.getParameter("dataEfetividade2");

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    Date parsedDate = dateFormat.parse(efetividade);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    amConfig.setEffectivityDateAndTime(timestamp);

                } catch (Exception e) {//this generic but you can control another types of exception
                    System.err.println(e.getMessage());
                }
                String controlDevice = request.getParameter("controlDevice2");

                String aux[] = controlDevice.split(" " + "-");
                switch (aux[0]) {
                    case "0":
                        ltq.setControlDevice(ControlDevice.STBControl);
                        break;
                    case "1":
                        ltq.setControlDevice(ControlDevice.STB);
                        break;
                    case "2":
                        ltq.setControlDevice(ControlDevice.STBKeyboard);
                        break;
                    case "10":
                        ltq.setControlDevice(ControlDevice.PC);
                        break;
                    case "20":
                        ltq.setControlDevice(ControlDevice.TABLET);
                        break;

                    case "30":
                        ltq.setControlDevice(ControlDevice.MOBILEFONE);
                        break;
                    case "40":
                        ltq.setControlDevice(ControlDevice.OTHER);
                        break;
                }
                String viewMode = request.getParameter("viewMode2");
                if (viewMode == null) {
                    ltq.setViewMode(ViewMode.DoNotReport);
                } else {
                    ltq.setViewMode(ViewMode.Report);
                }
                String obscuration = request.getParameter("obscuration2");
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

                String c[] = request.getParameterValues("canais2");
                if (c != null) {
                    ChannelList channelList = new ChannelList();
                    ChannelQualifier cq = new ChannelQualifier();
                    ltq.setChannelQualifier(cq);
                    cq.setChannelList(channelList);
                    ltq.getChannelQualifier().getChannelList().getServiceIdentifier().addAll(Arrays.asList(c));
                }
                String days[] = request.getParameterValues("days2");

                MeasurementSchedule ms = new MeasurementSchedule();

                mr.getMeasurementSchedule().add(ms);
                MeasurementPeriod mp = new MeasurementPeriod();
                ms.getMeasurementPeriod().add(mp);
                String startTime = request.getParameter("startTime2");
                String timeString[] = startTime.split("/");
                for (String time : timeString) {
                    mp.getStartTime().add(stringTime(time));
                }
                String endTimeString[] = request.getParameter("endTime2").split("/");
                for (String time : endTimeString) {
                    mp.getEndTime().add(stringTime(time));
                }
                for (String dias : days) {
                    String split[] = dias.split(" " + "-");
                    switch (split[0]) {
                        case "0":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Everyday);
                            break;
                        case "1":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Monday);

                            break;
                        case "2":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Tuesday);

                            break;
                        case "3":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Wednesday);

                            break;
                        case "4":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Thursday);

                            break;
                        case "5":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Friday);

                            break;
                        case "6":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Saturday);

                            break;
                        case "7":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Sunday);

                            break;
                        case "8":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.WeekDay);

                            break;
                        case "9":
                            mp.getDayOfTheWeek().add(DayOfTheWeek.Weekend);

                            break;
                    }
                }
                TimeTrigger tt = new TimeTrigger();
                tt.setPeriodicity(Integer.parseInt(request.getParameter("periodicity2")));
                ms.setTimeTrigger(tt);

                MeasurementDeliverySchedule msd = new MeasurementDeliverySchedule();

                mr.setMeasurementDeliverySchedule(msd);
                String deliveryAddress[] = request.getParameter("deliveryAddres2").split("/");
                msd.getDeliveryAddres().addAll(Arrays.asList(deliveryAddress));
                ImmediatePush ip = new ImmediatePush();
                msd.setImeImmediatePush(ip);
                msd.setRetransmitNumber(Integer.parseInt(request.getParameter("retransmitNumber2")));
                ip.setMeasurementReportNumberByPush(Integer.parseInt(request.getParameter("reportNumberByPush2")));
                ip.setMaxTimeBetweenDelivery(Integer.parseInt(request.getParameter("maxtime2")));
                break;

        }
        return ft;
    }// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Timestamp stringHora(String time) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SS");
            Date parsedDate = dateFormat.parse(time);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp;

        } catch (Exception e) {//this generic but you can control another types of exception
            System.err.println(e.getMessage());
        }
        return null;
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
}
