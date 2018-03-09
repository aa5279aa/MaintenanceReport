package com.lxl.valvedemo.model.viewmodel;

import com.lxl.valvedemo.model.buildModel.ReportSelectionSubItemEntity;

import java.util.ArrayList;
import java.util.List;

public class ReportSelectionItemEntity {

	public boolean isChecked;
	public String itemTitle;
	public List<ReportSelectionSubItemEntity> reportSelectionList=new ArrayList<ReportSelectionSubItemEntity>();
	
	
}
