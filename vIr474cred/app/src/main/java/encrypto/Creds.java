package encrypto;

/**
 * Created by VIR474 on 8/17/14.
 */
public class Creds {

    //private variables
    int _id;
    String _name;
    String _uname;
    String _pw;

    // Empty constructor
    public Creds(){

    }
    // constructor
    public Creds(int id, String name, String uname, String pw){
        this._id = id;
        this._name = name;
        this._uname = uname;
        this._pw = pw;
    }

    // constructor
    public Creds(String name, String _uname, String _pw){
        this._name = name;
        this._uname = _uname;
        this._pw = _pw;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting username
    public String getUserName(){
        return this._uname;
    }

    // setting username
    public void setUserName(String username){
        this._uname = username;
    }

    // getting pw
    public String getPassword(){
        return this._pw;
    }

    // setting pw
    public void setPassword(String pw){
        this._pw = pw;
    }
}
