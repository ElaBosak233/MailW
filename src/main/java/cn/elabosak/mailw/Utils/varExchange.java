package cn.elabosak.mailw.Utils;

import cn.elabosak.mailw.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.*;
import org.bukkit.entity.Player;

public class varExchange {
    static Main plugin;

  public static String readHtml(File file) {
    // ToDo 等待更改方法
//    File file = new File("C:\\Users\\ElaBosak\\Desktop\\example.html");
    if (file.exists()) {
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    } else {
        return "err";
    }
  }

    public static String getTitle(String s)
    {
        String regex;
        StringBuilder title = new StringBuilder();
        final List<String> list = new ArrayList<>();
        regex = "<title>.*?</title>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        for (String value : list) {
            title.append(value);
        }
        String result = title.toString().replace("<title>","");
        result = result.replace("</title>","");
        return result;
    }

    public static String getSender(String s)
    {
        String regex;
        StringBuilder sender = new StringBuilder();
        final List<String> list = new ArrayList<>();
        regex = "<sender hidden>.*?</sender>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        for (String value : list) {
            sender.append(value);
        }
        String result = sender.toString().replace("<sender hidden>","");
        result = result.replace("</sender>","");
        return result;
    }

//    public static void main(String[] args) {
//        File file = new File("plugins/MailW/template/example/index.html");
//        if (sendEmail.send("ElaBosak233@163.com","Ela",varExchange.getTitle(varExchange.readHtml(file)),"测试邮件")) {
//            System.out.println("发送成功");
//        } else {
//            System.out.println("发送失败");
//        }
//    }

   public static String deal(String before, Player target) {
      String after = null;
      after = before.replace("{{player.name}}", target.getName());
      after = after.replace("{{player.uuid}}", target.getUniqueId().toString());
      return after;
   }
}
