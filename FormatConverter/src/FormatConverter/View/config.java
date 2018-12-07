package FormatConverter.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class config {
    public static void main(String[] args) {
        String appTitle = "app_title";
        String appVersion = "app_version";
        String appProperties = "app.properties";
        String appAuthors = "app.authors";
        Properties settings = new Properties();
        settings.put(appTitle, "格式转换器");
        settings.put(appVersion, "1.0");
        settings.put(appAuthors, "张雷，王翀，张鑫");
        settings.put("filePath", "");
        settings.put("fileName", "");

        try {

            //写入配置
            FileOutputStream out = new FileOutputStream(appProperties);
            settings.store(out, "AppConfig");

            //读取配置
            FileInputStream in = new FileInputStream(appProperties);
            settings.load(in);

            String title = settings.getProperty(appTitle);
            String version = settings.getProperty(appVersion);

            System.out.println(title + "\t" + version);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
