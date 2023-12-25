package org.qiuhua.playerbackpacker.sql;

import org.qiuhua.playerbackpacker.Main;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteControl {


    private static String dbPath;
    private static Connection connection;

    //加载驱动
    public static void loadSQLiteJDBC() {
        try {
            Class.forName("org.sqlite.JDBC");
            String var10000 = Main.getMainPlugin().getDataFolder().getAbsolutePath();
            dbPath = var10000 + File.separator + "Database.db";
        } catch (ClassNotFoundException var1) {
            Main.getMainPlugin().getLogger().severe("无法加载 SQLite JDBC 驱动程序");
            throw new RuntimeException(var1);
        }

        Main.getMainPlugin().getLogger().info("已加载 SQLite JDBC 驱动程序");
    }
    //连接
    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (SQLException var1) {
            Main.getMainPlugin().getLogger().severe("无法连接到 SQLite 数据库");
            throw new RuntimeException(var1);
        }

        Main.getMainPlugin().getLogger().info("已连接到 SQLite 数据库");
    }

    //断开
    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var1) {
            Main.getMainPlugin().getLogger().severe("无法关闭 SQLite 数据库连接");
            throw new RuntimeException(var1);
        }

        Main.getMainPlugin().getLogger().info("已连关闭 SQLite 数据库连接");
    }

    //创建表
    public static void createTable(String uuid) {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS " + getTableName(uuid) + " (slot INTEGER PRIMARY KEY,item TEXT, title VARCHAR(255), size INTEGER, realSize INTEGER);";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException var3) {
            Main.getMainPlugin().getLogger().severe("执行 CREATE TABLE 语句时出错");
            System.out.println(var3.getMessage());
            throw new RuntimeException(var3);
        }
    }
    //删除表
    public static void dropTable(String uuid) {
        String sql = "DROP TABLE IF EXISTS" + getTableName(uuid) + ";";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException var3) {
            throw new RuntimeException(var3);
        }
    }

    //添加物品
    public static void addItemSlot(String uuid, Integer slot, String item) {
        String sql = "INSERT INTO " + getTableName(uuid) + " (slot,item) VALUES(?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, slot);
            pst.setString(2, item);
            pst.executeUpdate();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }
    //添加标题和容量
    public static void addTitleSizeRealSize(String uuid, String title, Integer size, Integer realSize) {
        String sql = "INSERT INTO " + getTableName(uuid) + " (title,size,realSize) VALUES(?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, title);
            pst.setInt(2, size);
            pst.setInt(3, realSize);
            pst.executeUpdate();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }

    //获取物品列表
    public static Map<Integer, String> getItemMap(String uuid) {
        String sql = "select * from " + getTableName(uuid) + ";";
        Map<Integer, String> map = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                Integer slot = resultSet.getInt("slot");
                String item = resultSet.getString("item");
                map.put(slot, item);
            }
            return map;
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    //获取标题
    public static String getTitle(String uuid) {
        String sql = "SELECT COALESCE((SELECT title FROM " + getTableName(uuid) + " WHERE title IS NOT NULL), '默认标题') AS title;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("title");
            } else {
                // 处理结果为空的情况，例如返回默认值或抛出异常
                return "默认标题";
            }
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    //获取大小
    public static Integer getSize(String uuid){
        String sql = "SELECT COALESCE((SELECT size FROM " + getTableName(uuid) + " WHERE size IS NOT NULL), 54) AS size;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("size");
            } else {
                // 处理结果为空的情况，例如返回默认值或抛出异常
                return 54;
            }
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    //获取实际大小
    public static Integer getRealSize(String uuid) {
        String sql = "SELECT COALESCE((SELECT realSize FROM " + getTableName(uuid) + " WHERE realSize IS NOT NULL), 54) AS realSize;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("realSize");
            } else {
                // 处理结果为空的情况，例如返回默认值或抛出异常
                return 54;
            }
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }
    //获取全部uuid
    public static List<String> allTableName() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table';";
        List<String> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                String tableName = resultSet.getString(1);
                list.add(tableName);
            }
            return list;
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }




    public static String getTableName(String uuid) {
        return String.format("`%s`", uuid);
    }

}
