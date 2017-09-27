package com.lxl.valvedemo.config;

import android.content.Context;

import com.lxl.valvedemo.model.SingleSelectionModel;
import com.lxl.valvedemo.util.IOHelper;
import com.lxl.valvedemo.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangleiliu on 2017/9/26.
 */
public class TableConfig {

    private static TableConfig tableConfig;
    public SingleSelectionModel mSelectionModel = new SingleSelectionModel();

    private TableConfig() {
    }

    public static TableConfig getInstance(Context context) {
        if (tableConfig == null) {
            tableConfig = new TableConfig();
            tableConfig.initData(context);
        }
        return tableConfig;
    }

    public void initData(Context context) {
        List<String> lineList = new ArrayList<>();
        try {
            InputStream open = context.getAssets().open("map.txt");
            lineList.addAll(IOHelper.readListStrByCode(open, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSelectionModel.level = 0;
        for (String line : lineList) {
            addLine2ModelList(line, mSelectionModel);
        }
    }

    private void addLine2ModelList(String line, SingleSelectionModel selectionModel) {
        if (line.contains("//")) {
            line = line.substring(0, line.indexOf("//")).trim();
        } else {
            line = line.trim();
        }
        String[] split = line.split("~");
        if (split.length < 3) {
            return;
        }
        String plate = split[0];
        String major = split[1];
        String form = split[2];
        String parseType = "1";
        if (split.length == 4) {
            parseType = split[3];
        }
        SingleSelectionModel plateModel = findFromModel(selectionModel.selectList, plate);
        if (plateModel == null) {
            plateModel = new SingleSelectionModel();
            plateModel.itemStr = plate;
            plateModel.level = 1;
            plateModel.parentModel = selectionModel;
            selectionModel.selectList.add(plateModel);
        }
        SingleSelectionModel majorModel = findFromModel(plateModel.selectList, major);
        if (majorModel == null) {
            majorModel = new SingleSelectionModel();
            majorModel.itemStr = major;
            majorModel.level = 2;
            majorModel.parentModel = plateModel;
            plateModel.selectList.add(majorModel);
        }
        SingleSelectionModel formModel = findFromModel(majorModel.selectList, form);
        if (formModel == null) {
            formModel = new SingleSelectionModel();
            formModel.itemStr = form;
            formModel.level = 3;
            formModel.isCanJump = true;
            formModel.anwserStr = "anwserStr";//对应的表格名称
            formModel.parentModel = majorModel;
            formModel.parseType = parseType;
            majorModel.selectList.add(formModel);
        }
    }

    public SingleSelectionModel findFromModel(List<SingleSelectionModel> list, String line) {
        for (SingleSelectionModel model : list) {
            if (model.itemStr.equals(line)) {
                return model;
            }
        }
        return null;
    }

    /**
     * @param model 最底层model
     * @return 路径
     */
    public String findExeclByModel(Context context, SingleSelectionModel model) throws IOException {
        SingleSelectionModel secondModel = model.parentModel;
        SingleSelectionModel firstModel = secondModel.parentModel;
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("execl");
        String[] execls = context.getAssets().list(pathBuilder.toString());
        String first = "";
        for (String line : execls) {
            if (line.equals(firstModel.itemStr)) {
                first = firstModel.itemStr;
                break;
            }
        }
        if (StringUtil.emptyOrNull(first)) {
            return "";
        }
        pathBuilder.append(File.separator);
        pathBuilder.append(first);
        String[] firsts = context.getAssets().list(pathBuilder.toString());
        String second = "";
        for (String line : firsts) {
            if (line.equals(secondModel.itemStr)) {
                second = secondModel.itemStr;
                break;
            }
        }
        if (StringUtil.emptyOrNull(second)) {
            return "";
        }
        pathBuilder.append(File.separator);
        pathBuilder.append(second);
        pathBuilder.append(File.separator);
        pathBuilder.append(model.itemStr);
        return pathBuilder.toString();
    }


}
