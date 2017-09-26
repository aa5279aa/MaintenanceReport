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
    public List<SingleSelectionModel> mSelectionModelList = new ArrayList<>();

    private TableConfig() {
    }

    public static TableConfig getInstance() {
        if (tableConfig == null) {
            tableConfig = new TableConfig();
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
        mSelectionModelList = new ArrayList<>();
        for (String line : lineList) {
            String[] split = line.split("~");
            String plate = split[0];
            String major = split[1];
            String form = split[2];
            SingleSelectionModel plateModel = findFromModel(mSelectionModelList, plate);
            if (plateModel == null) {
                plateModel = new SingleSelectionModel();
                mSelectionModelList.add(plateModel);
            }

            plateModel

        }

    }

    public SingleSelectionModel findFromModel(List<SingleSelectionModel> list, String line) {
        for (SingleSelectionModel model : list) {
            if (model.key.equals(line)) {
                return model;
            }
        }
        return null;
    }


}
