package com.lxl.maintenance;

import com.lxl.maintenance.service.MaintenanceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//http://192.168.0.103:8080/ReportWeb/report_inquire
public class MaintenanceInquireServlet extends HttpServlet {
    MaintenanceService service = new MaintenanceService();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type");
        String area = request.getParameter("area");
        String station = request.getParameter("station");
        String date = request.getParameter("date");
        String checker = request.getParameter("checker");
        System.out.println("MaintenanceInquireServlet");
        System.out.println("type:" + type + ",area:" + area + ",station:" + station + ",date:" + date + ",checker:" + checker);

        String s = service.actionselectDB(type, area, station, checker, date);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        response.setStatus(200);
        writer.write(s);
        writer.flush();
    }
}
