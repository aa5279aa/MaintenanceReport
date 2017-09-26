package com.lxl.valvedemo.config;

import android.content.Context;

import com.lxl.valvedemo.model.SingleSelectionModel;
import com.lxl.valvedemo.util.IOHelper;

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
        for (String line : lineList) {
            addLine2ModelList(line, mSelectionModel.selectList);
        }
    }

    private void addLine2ModelList(String line, List<SingleSelectionModel> list) {
        String[] split = line.split("~");
        if (split.length != 3) {
            return;
        }
        String plate = split[0];
        String major = split[1];
        String form = split[2];
        SingleSelectionModel plateModel = findFromModel(list, plate);
        if (plateModel == null) {
            plateModel = new SingleSelectionModel();
            plateModel.itemStr = plate;
            plateModel.level = 1;
            list.add(plateModel);
        }
        SingleSelectionModel majorModel = findFromModel(plateModel.selectList, major);
        if (majorModel == null) {
            majorModel = new SingleSelectionModel();
            majorModel.itemStr = major;
            majorModel.level = 2;
            plateModel.selectList.add(majorModel);
        }
        SingleSelectionModel formModel = findFromModel(majorModel.selectList, form);
        if (formModel == null) {
            formModel = new SingleSelectionModel();
            formModel.itemStr = form;
            formModel.level = 3;
            formModel.isCanJump = true;
            formModel.anwserStr = "anwserStr";//对应的表格名称
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


}
