package com.lxl.maintenance;

import com.alibaba.fastjson.JSONObject;
import com.lxl.maintenance.config.MaintenanceConfig;
import com.lxl.maintenance.model.ImageModel;
import com.lxl.maintenance.util.IOHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "MaintenanceUploadImageServlet")
public class MaintenanceUploadImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        JSONObject requestJson = new JSONObject();
        try {
            //解析请求参数
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fileItems = upload.parseRequest(request);
            //转换为ImageModel
            ImageModel imageModel = readImageModel(fileItems);
            //主线程保存图片信息
            //线程    保存图片
            saveImage(imageModel);
            requestJson.put("result", 200);
            requestJson.put("resultMessage", "success");
            requestJson.put("imgpath", imageModel.mImgUrl);
        } catch (Exception e) {
            requestJson.put("result", 500);
            requestJson.put("resultMessage", e.getMessage());
            e.printStackTrace();
        }
        writer.write(requestJson.toJSONString());
        writer.flush();
    }

    //请求转化为ImgeModel
    public ImageModel readImageModel(List<FileItem> list) throws FileUploadException, IOException {
        ImageModel imageModel = new ImageModel();
        String fileName = "temp.png";
        for (FileItem fileItem : list) {
            String fieldName = fileItem.getFieldName();
            if ("input_img".equals(fieldName) && fileItem instanceof DiskFileItem) {
                fileName = fileItem.getName();
                imageModel.fileItem = fileItem;
            }
        }
        //重新整理imgName
        imageModel.mImgName = "Maintenance_" + fileName;
        imageModel.mImgPath = MaintenanceConfig.Save_Path;
        imageModel.mImgUrl = imageModel.mImgPath + File.separator + imageModel.mImgName;
        return imageModel;
    }

    //开个线程去存储
    public String saveImage(ImageModel imageModel) throws Exception {
        //转存图片
        File saveImgFile = new File(imageModel.mImgUrl);
        if (!saveImgFile.getParentFile().exists()) {
            saveImgFile.getParentFile().mkdirs();
        }
        if (imageModel.fileItem != null) {
            imageModel.fileItem.write(saveImgFile);
        } else {
            File error = new File(saveImgFile.getAbsolutePath() + ".fail");
            saveImgFile.renameTo(error);
        }
        return saveImgFile.getAbsolutePath();
    }

}
