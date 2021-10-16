package me.hym;

public class User {
    /**
     * 账号
     */
    private String name;
    /**
     * 密码串
     */
    private String password;
    /**
     * 会员号
     */
    private String number;

    public User(String name, String password, String number) {
        this.name = name;
        this.password = password;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "用户名\t密码\t会员号\n"+name+"\t"+password+"\t"+number;
    }
}
