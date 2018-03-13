package com.lxl.maintenance;

import com.alibaba.fastjson.JSONObject;
import com.lxl.maintenance.dao.MaintenanceDao;
import com.lxl.maintenance.dao.MaintenanceDaoImpl;
import com.lxl.maintenance.service.MaintenanceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//http://localhost:8080/ReportWeb/
public class MaintenanceSubmitServlet extends HttpServlet {
    static int i = 0;
    MaintenanceService service;

    public MaintenanceSubmitServlet() {
        service = new MaintenanceService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        System.out.println("MaintenanceSubmitServlet");
        String type = request.getParameter("type");
        String data = request.getParameter("data");
        String area = request.getParameter("area");
        String station = request.getParameter("station");
        String checker = request.getParameter("checker");
        String date = request.getParameter("date");
        String location = request.getParameter("location");
        String lat_long = request.getParameter("lat_long");
        String imgpath = request.getParameter("imgPath");

        System.out.println("area:" + area + ",station:" + station + ",checker:" + checker + ",date:" + date + ",location:" + location + ",lat_long:" + lat_long + ",imgpath:" + imgpath);
        System.out.println("data:" + data);

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);
        PrintWriter writer = response.getWriter();

        JSONObject resultJson = new JSONObject();
        try {
            boolean isSucess = service.actionInsertDB(type, area, station, checker, date, location, lat_long, imgpath, data);
            resultJson.put("result", isSucess ? 200 : 500);
            resultJson.put("resultMessage", isSucess ? "success" : "fail");
        } catch (Exception e) {
            resultJson.put("result", 500);
            resultJson.put("resultMessage", e.getMessage());
            e.printStackTrace();
        }
        writer.write(resultJson.toJSONString());
        writer.flush();
    }
}
