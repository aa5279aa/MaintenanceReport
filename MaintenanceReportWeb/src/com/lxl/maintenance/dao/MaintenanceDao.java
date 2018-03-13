package com.lxl.maintenance.dao;

import com.lxl.maintenance.model.RecordModel;

public interface MaintenanceDao {
    int insertWxdYbzdhjltx(RecordModel recordModel);

    int insertWxdSbWeihu(RecordModel recordModel);

    int insertZyqYbzdhjltx(RecordModel recordModel);

    int insertZyqDq(RecordModel recordModel);

    int insertZyqGq(RecordModel recordModel);

    int insertZyqSbXunjian(RecordModel recordModel);

    int insertZyqSbWeihu(RecordModel recordModel);
}
