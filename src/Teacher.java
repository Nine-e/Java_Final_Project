/**
 * Created by del on 2017/12/19.
 */
public class Teacher {
    private String t_id;
    private String t_name;
    private String t_sex;
    private String t_title;
    private String t_major;
    private String t_phone;
    private String t_password;
    private int t_count;
    private String t_isfull;


    public Teacher(String t_id, String t_name, String t_sex, String t_title, String t_major, String t_phone, String t_password, String t_isfull) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.t_sex = t_sex;
        this.t_title = t_title;
        this.t_major = t_major;
        this.t_phone = t_phone;
        this.t_password = t_password;
        this.t_isfull = t_isfull;
    }

    public Teacher(String t_id, String t_name, String t_sex, String t_title, String t_major, String t_phone, String t_password, int t_count, String t_isfull) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.t_sex = t_sex;
        this.t_title = t_title;
        this.t_major = t_major;
        this.t_phone = t_phone;
        this.t_password = t_password;
        this.t_count = t_count;
        this.t_isfull = t_isfull;
    }


    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_sex() {
        return t_sex;
    }

    public void setT_sex(String t_sex) {
        this.t_sex = t_sex;
    }

    public String getT_title() {
        return t_title;
    }

    public void setT_title(String t_title) {
        this.t_title = t_title;
    }

    public String getT_major() {
        return t_major;
    }

    public void setT_major(String t_major) {
        this.t_major = t_major;
    }

    public String getT_phone() {
        return t_phone;
    }

    public void setT_phone(String t_phone) {
        this.t_phone = t_phone;
    }

    public String getT_password() {
        return t_password;
    }

    public void setT_password(String t_password) {
        this.t_password = t_password;
    }


    public void setT_isfull(String t_isfull) {
        this.t_isfull = t_isfull;
    }

    public int getT_count() {
        return t_count;
    }

    public void setT_count(int t_count) {
        this.t_count = t_count;
    }

    public String getT_isfull() {
        return t_isfull;
    }



}
