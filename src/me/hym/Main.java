package me.hym;

import java.util.Scanner;

public class Main {
    static String menu="欢迎进入富翁抽奖系统\n"
            +"\t1.注册\n\t2.登录\n\t3.抽奖\n\t4.查询\n\t5.修改密码\n\t6.删除账号\n"
            +"\t其他数字退出系统";
    /**
     * 程序入口
     * @param args 从命令行传入的参数
     */
    public static void main(String[] args) {
        InputWithInfo iwi = new InputWithInfo();
        Administrator adm = new Administrator();
        String luckyNumber = adm.lottery();
        User nowLoggedIn=null;
        loop:
        for(;;){
            System.out.println(menu);
            switch (iwi.next("请输入你的选择:")){
                case "1":{
                    System.out.println("大富翁系统->注册");
                    //注册账号
                    User u = adm.signUp(iwi.next("请输入账号:"),iwi.next("请输入密码:"));
                    if(u!=null){
                        System.out.println("注册成功！\n"+u);
                        break;
                    }
                    System.out.println("注册失败");
                    break;}
                case "2":{
                    System.out.println("大富翁系统->登录");
                    //登录验证
                    if(adm.failCount>=Administrator.failCountMax){
                        System.out.println("已超过最大尝试次数！");
                        System.exit(0);
                        break;
                    }
                    User u = adm.logIn(iwi.next("请输入账号:"),iwi.next("请输入密码:"));
                    if(u!=null){
                        System.out.println("登录成功！欢迎用户"+u.getName());
                        nowLoggedIn=u;
                        break;
                    }
                    System.out.println("登录失败");
                    break;}
                case "3":{
                    System.out.println("大富翁系统->抽奖");
                    //登录状态检查，当前账户被删除时nowLoggedIn为null
                    if(nowLoggedIn==null){
                        System.out.println("尚未登录！");
                        break;
                    }
                    System.out.print("今天的幸运数字为:"+luckyNumber+",你的会员号为"+nowLoggedIn.getNumber());
                    if(luckyNumber.equals(nowLoggedIn.getNumber())){
                        System.out.println(",今天是你的幸运日!!!");
                        break;
                    }
                    System.out.println(",今天不是你的幸运日!!!");
                    break;}
                case "4":
                    System.out.println("大富翁系统->查询");
                    System.out.println(nowLoggedIn);
                    adm.listAll();
                    break;
                case "5":{
                    System.out.println("大富翁系统->修改密码");
                    //使用登录方法验证
                    User u = adm.logIn(iwi.next("请输入账号:"),iwi.next("请输入密码:"));
                    if(u==null){
                        System.out.println("账号密码不匹配，请重新输入");
                        break;
                    }
                    String newPassword = iwi.next("请输入新密码:");
                    if(newPassword.equals(iwi.next("请再次输入新密码:"))){
                        //更改密码
                        adm.changePassword(u,newPassword);
                        break;
                    }
                    System.out.println("两次密码不匹配，请重新输入");
                    break;}
                case "6":{
                    System.out.println("大富翁系统->删除账号");
                    //使用登录方法验证
                    User u = adm.logIn(iwi.next("请输入账号:"),iwi.next("请输入密码:"));
                    if(u==null){
                        System.out.println("账号密码不匹配，请重新输入");
                        break;
                    }
                    adm.delete(u);
                    break;}
                default:
                    //打断主循环
                    break loop;
            }
        }
        //释放资源
        iwi.close();
    }
}
class InputWithInfo{
    Scanner scanner = new Scanner(System.in);
    public int nextInt(String message){
        System.out.print(message);
        return scanner.nextInt();
    }
    public String next(String message){
        System.out.print(message);
        return scanner.next();
    }
    public void close(){
        scanner.close();
    }
}