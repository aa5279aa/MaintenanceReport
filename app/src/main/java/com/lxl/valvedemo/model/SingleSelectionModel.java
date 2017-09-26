package com.lxl.valvedemo.model;

import java.util.ArrayList;
import java.util.List;

public class SingleSelectionModel {

	public String key = "";
	public int level = 0;
	public boolean isCanSelect = false;
	public String itemStr;
	public String anwserStr;
	public boolean isCanJump = false;
	public List<SingleSelectionModel> selectList = new ArrayList<SingleSelectionModel>();
	public SingleSelectionModel parentSingleSelection;

}
