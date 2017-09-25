package com.lxl.valvedemo.entity;

import java.util.ArrayList;
import java.util.List;

public class SingleSelectionEntity {

	public String key = "";
	public int level = 0;
	public boolean isCanSelect = false;
	public String itemStr;
	public String anwserStr;
	public boolean isCanJump = false;
	public List<SingleSelectionEntity> selectList = new ArrayList<SingleSelectionEntity>();
	public SingleSelectionEntity parentSingleSelection;

}
