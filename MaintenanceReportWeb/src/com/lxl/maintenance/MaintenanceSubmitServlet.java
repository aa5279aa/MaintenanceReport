package com.lxl.maintenance;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//http://localhost:8080/ReportWeb/
public class MaintenanceSubmitServlet extends HttpServlet {
    static int i = 0;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("MaintenanceSubmitServlet");
        String data = request.getParameter("data");
        String area = request.getParameter("area");
        String station = request.getParameter("station");
        String checker = request.getParameter("checker");
        String date = request.getParameter("date");
        String location = request.getParameter("location");
        String lat_long = request.getParameter("lat_long");

        System.out.println("data:" + data);
        System.out.println("area:" + area);
        System.out.println("station:" + station);
        System.out.println("checker:" + checker);
        System.out.println("date:" + date);
        System.out.println("location:" + location);
        System.out.println("lat_long:" + lat_long);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.write(i++ % 3 == 0 ? "success" : "fail");
        writer.flush();
    }
}
