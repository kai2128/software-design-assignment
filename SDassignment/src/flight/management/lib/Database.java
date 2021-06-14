package flight.management.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * Singleton Database class
 * db used: MySql
 * read config from conf
 */
public class Database {
    private static volatile Database instance = null;

    private final static String CONFIG_PATH = "./db_config.properties";
    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    private Connection conn = null;
    private Statement statement = null;
    private ResultSet result = null;

    //read config file and load driver
    static {
        InputStream is;
        try{
            is = new FileInputStream(CONFIG_PATH);
            Properties prop = new Properties();

            try{
                prop.load(is);

                driver = prop.getProperty("driver");
                url = prop.getProperty("url");
                username = prop.getProperty("username");
                password = prop.getProperty("password");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //load driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Database getInstance() throws SQLException {
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

//    Private constructor to prevent
    private Database() throws SQLException {connect();}

    public void release(){
        if(result != null){
            try {
                result.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void disconnect(){
        release();
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void connect()throws SQLException{
        if(conn==null){
            try {
                conn = DriverManager.getConnection(url,username,password);

            } catch (SQLException se) {
                throw new SQLException();
            }
        }
    }


    /*
     * SQL Execution
     */

    //UPDATE DELETE CREATE
    public int execUpdate(String sql){
        int affectedLine = 0;

        try {
            statement = conn.createStatement();
            affectedLine = statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            System.out.println(sql);
            throwables.printStackTrace();
        } finally {
            release();
        }
        return affectedLine;
    }

    //use to execute select sql
    private void execQuery(String sql){
        String action = sql.substring(0,6);

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int rowCount(String sql){
        int size= 0;
        try {
          execQuery(sql);
          while (result.next()){
              size++;
          }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            release();
        }
            return size;
    }


    /**
     * Select query that returns one line
     * @param sql select query
     * @return {a_prefix=A, a_id=1, a_username=test, a_phone=0111234567, a_email=test@test.com, a_password=test}
     */
    public Map<String, String> selectOne(String sql){
        Map<String, String> row  = new LinkedHashMap<String,String>();

        execQuery(sql);
        try {
            if (result.next()) {
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++){
                    row.put(result.getMetaData().getColumnLabel(i), result.getString(i));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            release();
        }

        return row;
    }

    /**
     *
     * @param sql select query
     * @return {1=A, 2=1, 3=test, 4=0111234567, 5=test@test.com, 6=test}
     */
    public Map<Integer, String> selectOneAsNumMap(String sql){

        Map<Integer, String> row  = new LinkedHashMap<Integer,String>();

        execQuery(sql);
        try {
            if (result.next()) {
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++){
                    row.put(i, result.getString(i));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            release();
        }

        //{1=A, 2=1, 3=test, 4=0111234567, 5=test@test.com, 6=test}
        return row;
    }

    /**
     * Select query that returns multiple row
     * as map with key value
     * @param sql select query
     * @return [{a_prefix=A, a_id=1, a_username=test, a_phone=0111234567, a_email=test@test.com, a_password=test},
     */
    public List<Map<String, String>> selectAll(String sql){
        execQuery(sql);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        try {
            while (result.next()) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++){
                    map.put(result.getMetaData().getColumnLabel(i), result.getString(i));
                }
                list.add(map);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            release();
        }
        return list;
    }

    /**
     *
     * @param sql select query
     * @return {1=A, 2=1, 3=test, 4=0111234567, 5=test@test.com, 6=test}
     */
    public List<Map<Integer, String>> selectAllAsNumMap(String sql){
        execQuery(sql);

        List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

        try {
            while (result.next()) {
                Map<Integer, String> map = new LinkedHashMap<Integer, String>();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++){
                    map.put(i, result.getString(i));
                }
                list.add(map);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            release();
        }
        return list;
    }
}

