package com.lxl.maintenance.dao;

import com.lxl.maintenance.config.MaintenanceConfig;
import com.lxl.maintenance.model.RecordModel;
import com.lxl.maintenance.util.StringUtil;

import java.sql.*;
import java.util.*;

public class MaintenanceDaoImpl implements MaintenanceDao {
    Connection conn;

    public MaintenanceDaoImpl() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://112.253.42.243:3306/zydb1?useUnicode=true&characterEncoding=utf-8";
            String user = "lxl";
            String password = "lxl";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int insertWxdYbzdhjltx(RecordModel recordModel) {
//        String sql = "insert into wxd_ybzdhjltx (xuhao,jiancharen,jianchariqi,zuoyequ,zhanchang,plcgongdian,cpujijia) values (?)";
        String sql = map2sql("wxd_ybzdhjltx", recordModel.data);
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = state.executeUpdate();
            if (i <= 0) {
                return 0;
            }
            rs = state.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                return userId;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeSql(state, rs);
        }
    }

    @Override
    public int insertWxdSbWeihu(RecordModel recordModel) {
        String sql = map2sql("wxd_sb_weihu", recordModel.data);
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = state.executeUpdate();
            if (i <= 0) {
                return 0;
            }
            rs = state.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                return userId;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeSql(state, rs);
        }
    }

    @Override
    public int insertZyqDq(RecordModel recordModel) {
        String sql = map2sql("zyq_dq", recordModel.data);
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = state.executeUpdate();
            if (i <= 0) {
                return 0;
            }
            rs = state.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                return userId;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeSql(state, rs);
        }
    }

    @Override
    public int insertZyqGq(RecordModel recordModel) {
        String sql = map2sql("zyq_gd", recordModel.data);
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = state.executeUpdate();
            if (i <= 0) {
                return 0;
            }
            rs = state.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                return userId;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeSql(state, rs);
        }
    }

    @Override
    public int insertZyqSbXunjian(RecordModel recordModel) {
        String sql = map2sql("zyq_sb_xunjian", recordModel.data);
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = state.executeUpdate();
            if (i <= 0) {
                return 0;
            }
            rs = state.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                return userId;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeSql(state, rs);
        }
    }

    @Override
    public int insertZyqSbWeihu(RecordModel recordModel) {
        String sql = map2sql("zyq_sb_weihu", recordModel.data);
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = state.executeUpdate();
            if (i <= 0) {
                return 0;
            }
            rs = state.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                return userId;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeSql(state, rs);
        }
    }

    @Override
    public List<HashMap<String, String>> selectDataFromDbByTable(RecordModel recordModel, String table) {
        List<HashMap<String, String>> list = new ArrayList<>();
        String sql = "select * from " + table + " where jiancharen = ? and zuoyequ = ? and zhanchang = ? and jianchariqi like ?";
        PreparedStatement preStmt = null;
        try {
            preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, recordModel.checker);
            preStmt.setString(2, recordModel.area);
            preStmt.setString(3, recordModel.station);
            preStmt.setString(4, recordModel.date);
            ResultSet rs = preStmt.executeQuery();
            Set<String> noNeedKey = MaintenanceConfig.getNoNeedKey();
            while (rs.next()) {
                int count = rs.getMetaData().getColumnCount();
                HashMap<String, String> params = new HashMap<>();
                for (int i = 1; i <= count; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    if (noNeedKey.contains(columnName)) {
                        continue;
                    }
                    String value = rs.getString(columnName);
                    params.put(columnName, value);
                }
                list.add(params);
                break;
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSql(preStmt, null);
        }
        return list;
    }

    @Override
    public int insertZyqYbzdhjltx(RecordModel recordModel) {
        String sql = map2sql("zyq_ybzdhjltx", recordModel.data);
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = state.executeUpdate();
            if (i <= 0) {
                return 0;
            }
            rs = state.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                return userId;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeSql(state, rs);
        }
    }

    private String map2sql(String tableName, Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder valueBuidler = new StringBuilder();

        for (String key : map.keySet()) {
            String value = map.get(key);
            fieldBuilder.append(key + ",");
            if (StringUtil.emptyOrNull(value)) {
                value = "未填";
            }
            valueBuidler.append("\"" + value + "\",");
        }
        if (fieldBuilder.toString().endsWith(",")) {
            fieldBuilder.delete(fieldBuilder.length() - 1, fieldBuilder.length());
        }
        if (valueBuidler.toString().endsWith(",")) {
            valueBuidler.delete(valueBuidler.length() - 1, valueBuidler.length());
        }
        builder.append("insert into ");
        builder.append(tableName);
        builder.append(" (" + fieldBuilder.toString() + ")");
        builder.append(" values (" + valueBuidler.toString() + ")");
        return builder.toString();
    }


    private void closeSql(Statement stmt, ResultSet rs) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
