package com.lxl.maintenance;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//http://192.168.0.103:8080/ReportWeb/report_inquire
public class MaintenanceInquireServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type");
        String area = request.getParameter("area");
        String station = request.getParameter("station");
        System.out.println("MaintenanceInquireServlet");
        System.out.println("type:" + type);
        System.out.println("area:" + area);
        System.out.println("station:" + station);


        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        response.setStatus(200);
        writer.write("[{\"name\":\"1、PLC机柜供电：声光报警器供电正常，可正常鸣响。测量 220V电压应在正常范围内。测试各冗 余24V电源输出电压应为24±2V。测试各冗余24V电源输出端子对地电压应为悬空。\",\"value\":\"粗粗\"},{\"name\":\"2、CPU机架：主备机架冗余电源模块均正常，CPU模块正常、冗余正常、通讯正常。备份PLC程序。\",\"value\":\"比成绩\"},{\"name\":\"3、远程IO机架：冗余电源模块运行正常，远程通讯模块CRP运行正常无报警。各IO模块运行正常无异常报警。\",\"value\":\"刺激刺激\"},{\"name\":\"4、交换机与路由器：使用Ping命令测试，站内局域网内各设备通讯正常，与北京油气调控中心、廊坊备控中 心互联地址通讯正常无丢包；备份路由器配置。\",\"value\":\"v句今年\"},{\"name\":\"5、通讯服务器RCI：登录检查，备份配置文件。电池电压在25V以上。各指示灯及仪表指示正常。 发电机试运行正常，输出频率稳定。外观清洁，无油污、无灰尘、无锈蚀。\",\"value\":\" 经常交叉口\"},{\"name\":\"6、站控机：各网络连接均显示正常，无中断，如有则排查。系统时间与北京时间相同，否则进行校时操作。 各指示灯及仪表指示正常。画面各参数显示正常，更新时间标签正常；无异常报警；命令可正常发出。 配置图内各第三方设备通讯无故障中断。趋势、报警显示正常，可追溯。报表正常生成，可追溯。\",\"value\":\"超级差你从哪\"},{\"name\":\"7、流量计算机检测：检查流量计算机中的相关参数，标准状况参比条件、压力温度的量程设置、气体组分、 管道内径、键盘值压力、温度等是否正常，并记录。用网线将笔记本电脑与流量计算机连接，对报警信息、 事件记录进行下载存档。\",\"value\":\"vuvi看\"},{\"name\":\"8、流量计检测：检查流量计电缆连接、探头安装有无异常情况，注油孔油位是否正常。 每年9月至10月份，对超声波流量计进行声速核查，确定流量计处于完好状态(一年一次，做好备份)。\",\"value\":\"才能警察局\"},{\"name\":\"9、色谱分析仪：检查载气（110psi）、标气（20psi）、样气（20psi）压力值是否正常。 电脑连接色谱分析仪，基线走势是否异常。管线连接处是否完好，有无泄漏点。\",\"value\":\"viv看v看看\"},{\"name\":\"10、备注：\",\"value\":\"粗v就你从哪\"}]");
        writer.flush();
    }
}