package flight.management.dao;

import flight.management.entity.Admin;
import flight.management.entity.Client;
import flight.management.entity.User;

import java.util.Map;

public class UserDao extends BaseDao {

    //SELECT username
    //from admin WHERE username = 'test'
    //UNION
    //SELECT username
    //from
    //client
    //WHERE username = 'test'
    public static boolean isUsernameRepeated(String username){
        String sql = String.format("SELECT username from admin where username = '%s' UNION SELECT username from client WHERE username = '%s'", username, username);

        return db.rowCount(sql) > 0;
    }

    private static Map<String, String> findUser(String table, String username, String password){
        String sql = String.format("SELECT * FROM %s WHERE username='%s' AND password='%s'", table, username, password);
        return db.selectOne(sql);
    }

    public static User login(String username, String password){

        Map<String, String> result = null;

        result = findUser("client", username, password);
        if(!(result.isEmpty())){
            return new Client(result);
        }

        result = findUser("admin", username, password);
        if(!(result.isEmpty())){
            return new Admin(result);
        }
        return null;
    }

    public static boolean signUpUser(String[] args) {

        String username  = args[0];
        String name = args[1];
        String phone = args[2];
        String email = args[3];
        String password = args[4];


        String sql = String.format("INSERT INTO client VALUES(DEFAULT, null, '%s', '%s', '%s', '%s', '%s')"
                        , username, name, phone, email, password);
        if(db.execUpdate(sql)>0){
            return true;
        }

        return false;
    }


}
