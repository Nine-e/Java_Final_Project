/**
 * Created by del on 2017/12/19.
 */
public class Student {

    private String s_id;
    private String s_name;
    private String s_sex;
    private String s_major;
    private String s_clas;
    private String s_phone;
    private String s_password;
    private String s_state;
    private String t_id;
    private String t_name;

    public Student(String s_id, String s_name, String s_sex, String s_major, String s_clas, String s_phone, String s_password, String s_state, String t_id, String t_name) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_sex = s_sex;
        this.s_major = s_major;
        this.s_clas = s_clas;
        this.s_phone = s_phone;
        this.s_password = s_password;
        this.s_state = s_state;
        this.t_id = t_id;
        this.t_name = t_name;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_sex() {
        return s_sex;
    }

    public void setS_sex(String s_sex) {
        this.s_sex = s_sex;
    }

    public String getS_major() {
        return s_major;
    }

    public void setS_major(String s_major) {
        this.s_major = s_major;
    }

    public String getS_clas() {
        return s_clas;
    }

    public void setS_clas(String s_clas) {
        this.s_clas = s_clas;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getS_password() {
        return s_password;
    }

    public void setS_password(String s_password) {
        this.s_password = s_password;
    }

    public String getS_state() {
        return s_state;
    }

    public void setS_state(String s_state) {
        this.s_state = s_state;
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

}
