package com.lxl.maintenance.model;

import org.apache.commons.fileupload.FileItem;

/**
 * Created by xiangleiliu on 2017/5/25.
 */
public class ImageModel {

    public int mImgId;
    public String mImgName;
    public String mImgPath;
    public String mImgUrl;
    public int mRelationId;//
    public String mRelationName;//
    public int mType;//类型

    public FileItem fileItem;

}
