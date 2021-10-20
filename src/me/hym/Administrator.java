package me.hym;

import java.util.ArrayList;
import java.util.Iterator;

public class Administrator {
    private ArrayList<User> users;
    static final int failCountMax = 3;
    int failCount;

    /**
     * 以给定长度初始化 users
     *
     * @param length users 长度
     */
    public Administrator(int length) {
        users = new ArrayList<>(length);
    }

    /**
     * 以 ArrayList 默认长度初始化 users
     */
    public Administrator() {
        users = new ArrayList<>();
    }

    /**
     * 用户登录
     *
     * @param name     账号
     * @param password 密码
     * @return 用户类实例
     */
    public User logIn(String name, String password) {
        for(User u : users){
            if(u.getName().equals(name)&&u.getPassword().equals(password)){
                //重置失败计数
                failCount=0;
                return u;
            }
        }
        failCount = failCount >= failCountMax ? failCountMax : failCount + 1;
        return null;
    }

    /**
     * 用户注册
     *
     * @param name     账号
     * @param password 密码
     */
    public User signUp(String name, String password) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return null;
            }
        }
        //重复会员号检测
        String num =lottery();
        loop:
        while (true){
            for (User u: users) {
                if(u.getNumber().equals(num)){
                    num = lottery();
                    continue loop;
                }
            }
            break;
        }
        User u = new User(name, password, lottery());
        users.add(u);
        return u;
    }

    /**
     * 修改密码，调用前应当确认是否有权限修改
     * @param u 要修改密码的用户
     * @param newPassword 新的密码
     * @return 修改后的实例
     */
    public User changePassword(User u,String newPassword){
        u.setPassword(newPassword);
        return u;
    }

    /**
     * 删除账号，调用前应当确认是否有权限删除
     * @param u 要删除的账号
     */
    public void delete(User u){

        //ConcurrentModificationException
        //解决方法https://www.cnblogs.com/dolphin0520/p/3933551.html
//        for(User ui: users){
//            if(ui.getName().equals(u.getName())){
//                users.remove(ui);
//            }
//        }
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User ui = iterator.next();
            if(ui.getName().equals(u.getName())){
                iterator.remove();
                return;
            }
        }
    }
    /**
     * 抽奖
     *
     * @return 选中的会员号，范围1000~9999
     */
    public String lottery() {
        return "" + ((int) (Math.random() * 9000) + 1000);
    }
    public void listAll(){
        System.out.println("用户名\t会员号");
        for(User u :users){
            System.out.println(u.getName()+"\t"+u.getNumber());
        }
    }
}
