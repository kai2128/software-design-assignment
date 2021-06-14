package flight.management.dao;

import flight.management.lib.Database;

import java.sql.SQLException;

public class BaseDao {
    protected static Database db;

    static {
        try {
            db = Database.getInstance();
        } catch (SQLException ignored) {
        }
    }
}
