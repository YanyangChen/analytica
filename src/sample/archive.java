
package sample;

import javafx.fxml.FXML;
import redis.clients.jedis.Jedis;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static java.lang.Thread.sleep;

public class Controller implements Initializable{
    @Override
    public void initialize(URL url, ResourceBundle rb) {



    }

    @FXML  protected void getNewsfromEastmoney (ActionEvent event) throws Exception {


        //proxy connection module
//        URL url = new URL("http://hk.eastmoney.com/news/cggyw.html");
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.h", 8080)); // or whatever your proxy is
//        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
//
////        uc.connect();
//
//        String line = null;
//        StringBuffer tmp = new StringBuffer();
//        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//        while ((line = in.readLine()) != null) {
//            tmp.append(line);
//        }
        //---------------------------------------------------------------------------

        org.jsoup.nodes.Document doc = Jsoup.connect("http://hk.eastmoney.com/news/cggyw.html").get();
        Elements ul = doc.select("ul");
        Elements li = ul.select("li");
        for (int i = 0; i < li.size(); i++) {
           System.out.print( li.get(i).text() +"\n");
        }



}

    @FXML  protected void getNewsFromMingPao (ActionEvent event) throws Exception {
//        URL url = new URL("https://www.mpfinance.com/fin/main.php");
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
//        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
//
//        uc.connect();
//
//        String line = null;
//        StringBuffer tmp = new StringBuffer();
//        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//        while ((line = in.readLine()) != null) {
//            tmp.append(line);
//        }
        //---------------------------------------------------------------------------

        org.jsoup.nodes.Document doc = Jsoup.connect("https://www.mpfinance.com/fin/main.php").get();
        Elements ul = doc.select("ul");
        Elements li = ul.select("li");
        for (int i = 0; i < li.size(); i++)
            System.out.print(li.get(i).text() + "\n") ;

    }

    @FXML  protected void getNewsFromHackerNews (ActionEvent event) throws Exception {
//        URL url = new URL("https://news.ycombinator.com/");
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
//        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
//
//        uc.connect();
//
//        String line = null;
//        StringBuffer tmp = new StringBuffer();
//        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//        while ((line = in.readLine()) != null) {
//            tmp.append(line);
//        }
        //---------------------------------------------------------------------------

        org.jsoup.nodes.Document doc = Jsoup.connect("https://news.ycombinator.com").get();
        Elements tbody = doc.select("tbody");
        Elements tr = tbody.select("tr");
        for (int i = 0; i < tr.size(); i++)
            System.out.print(tr.get(i).text() + "\n") ;

    }

    @FXML protected void getTencentStock(ActionEvent event) throws Exception {
        int gap = 3600*24*60;
        Integer start = 1107792000;
        Integer end = 1518019200;
//        Integer end = 1107792000 +  3600*24*60;
//        org.jsoup.nodes.Document doc = Jsoup.connect("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d").proxy(proxy).get();

//        URL url = new URL("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");

        for(Integer i=start.intValue(); i < end.intValue(); i += gap ) {
            Integer i2 = i + gap;
            if(i2 + 120 > end) i2 = end;
            getData("0700.HK", i.toString(), i2.toString());
        }
    }
    
     @FXML protected void getTencentStock2Excel(ActionEvent event) throws Exception {
        String file = "0700HK.xls";
//        POIFSFileSystem fs = new POIFSFileSystem(new FileOutputStream(file));

        FileOutputStream fs = new FileOutputStream(file);
        HSSFWorkbook wb =  new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("test");
        HSSFRow row;
        HSSFCell cell;

        int gap = 3600*24*60;
        Integer start = 1107792000;
        Integer end = 1518019200;
//        Integer end = 1107792000 +  3600*24*60;
//        org.jsoup.nodes.Document doc = Jsoup.connect("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d").proxy(proxy).get();

//        URL url = new URL("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");

        for(Integer i=start.intValue(); i < end.intValue(); i += gap ) {
            Integer i2 = i + gap;
            if(i2 + 120 > end) i2 = end;
           counter =  getData2excel("0700.HK", i.toString(), i2.toString(),sheet, counter);
        }
        wb.write(fs);
        fs.close();
        wb.close();
    }

    @FXML protected void getTencentStock2Redis(ActionEvent event) throws Exception {
        int gap = 3600*24*60;
        Integer start = 1107792000;
        Integer end = 1518019200;
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(12);
//        JedisPool pool = new JedisPool("localhost");
//        Jedis jedis = pool.getResource();
        Jedis jedis = new Jedis();
//        Integer end = 1107792000 +  3600*24*60;
//        org.jsoup.nodes.Document doc = Jsoup.connect("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d").proxy(proxy).get();

//        URL url = new URL("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");

        for(Integer i=start.intValue(); i < end.intValue(); i += gap ) {
            Integer i2 = i + gap;
            if(i2 + 120 > end) i2 = end;
            getStkData2redis("0700.HK", i.toString(), i2.toString(), jedis);
            sleep(2000);
        }
    }

    @FXML protected void getRedis(ActionEvent event) throws Exception
    {
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server sucessfully");
        //check whether server is running or not
        System.out.println("Server is running: "+jedis.ping());
//        jedis.set("tutorial-name", "Redis tutorial");
        // Get the stored data and print it
        System.out.println("Stored string in redis:: "+ jedis.get("tutorial-name"));

        //store data in redis list
//        jedis.lpush("tutorial-list", "Redis");
//        jedis.lpush("tutorial-list", "Mongodb");
//        jedis.lpush("tutorial-list", "Mysql");
        // Get the stored data and print it
        List<String> list = jedis.lrange("0700.HK", 0 ,75);

        for(int i = 0; i<list.size(); i++) {
            System.out.println("Stored string in redis:: "+list.get(i));
        }

        Set<String> keyset = jedis.keys("*");

//        for(; keylist.hasne; i++) {
            System.out.println("Set of stored keys:: "+keyset);
//        }


    }
    void getData(String stock, String start, String end) throws Exception{
        URL url = new URL("https://finance.yahoo.com/quote/"+stock+"/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
        HttpURLConnection uc = (HttpURLConnection)url.openConnection();

        uc.connect();

        String line = null;
        StringBuffer tmp = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while ((line = in.readLine()) != null) {
            tmp.append(line);
        }
        //---------------------------------------------------------------------------
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
        org.jsoup.nodes.Document doc = Jsoup.parse(String.valueOf(tmp));
        Elements tbody = doc.select("tbody");
        Elements tr = tbody.select("tr");
        ArrayDeque<String> stkque = new ArrayDeque<String>();
        for (int i = 0; i < tr.size(); i++)
        {
//            System.out.print(tr.get(i).text() + "\n") ;
            stkque.addFirst(tr.get(i).text());
        }
        for (int i = 0; i < tr.size(); i++)
        {
//            System.out.print(tr.get(i).text() + "\n") ;
            System.out.print(stkque.pollFirst() + "\n");
        }
//        for (int i = 0; i < stkque.size(); i++)
//        {System.out.println(stkque.pollFirst());}
//    }
    }
     void getStkData2redis(String stock, String start, String end, Jedis jedis) throws Exception{
        URL url = new URL("https://finance.yahoo.com/quote/"+stock+"/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
        HttpURLConnection uc = (HttpURLConnection)url.openConnection();

        uc.connect();

        String line = null;
        StringBuffer tmp = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while ((line = in.readLine()) != null) {
            tmp.append(line);
        }
        //---------------------------------------------------------------------------
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
        org.jsoup.nodes.Document doc = Jsoup.parse(String.valueOf(tmp));
        Elements tbody = doc.select("tbody");
        Elements tr = tbody.select("tr");

        ArrayDeque<String> stkque = new ArrayDeque<String>(60);
         ArrayList<String> stkl = new ArrayList<String>();
        for (int i = 0; i < tr.size(); i++)
        {
//            System.out.print(tr.get(i).text() + "\n") ;
            stkque.addFirst(tr.get(i).text());
        }
        for (int i = 0; i < tr.size(); i++)
        {
//            System.out.print(tr.get(i).text() + "\n") ;
//            Jedis jd = jedis.getResource();
            System.out.print("2 redis: "+stkque.pollFirst() + "\n");
            if(stkque.pollFirst() == null){
           jedis.lpush(stock, "null");}
            else{jedis.lpushx(stock,stkque.pollFirst());}

        }
        stkque.clear();
//        stkque=null;
//        for (int i = 0; i < stkque.size(); i++)
//        {System.out.println(stkque.pollFirst());}
//    }
    }
    
    int getData2excel(String stock, String start, String end, HSSFSheet sheet, int counter) throws Exception{
            URL url = new URL("https://finance.yahoo.com/quote/"+stock+"/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
            System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
            System.setProperty("http.proxyPort", "8080");
            HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);

            uc.connect();

            String line = null;
            StringBuffer tmp = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }
            //---------------------------------------------------------------------------
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
            org.jsoup.nodes.Document doc = Jsoup.parse(String.valueOf(tmp));
            Elements tbody = doc.select("tbody");
            Elements tr = tbody.select("tr");

            ArrayDeque<String> stkque = new ArrayDeque<String>();
            for (int i =  tr.size() -1 ; i >= 0; i--)
            {
            System.out.print(tr.get(i).text() + "\n") ;
                counter++;
               HSSFRow row = sheet.createRow(counter);
                stkque.addFirst(tr.get(i).text());
                if(        !tr.get(i).text().substring(12).contains("-")
                        && !tr.get(i).text().substring(12).contains("Dividend")
                        && !tr.get(i).text().substring(12).contains("Stock Split")){
                row.createCell(0).setCellValue(tr.get(i).text().substring(0,12));

                String[] splited = tr.get(i).text().substring(12).split(" ");
                row.createCell(1).setCellValue(splited[0]);
                row.createCell(2).setCellValue(splited[1]);
                row.createCell(3).setCellValue(splited[2]);
                row.createCell(4).setCellValue(splited[3]);
                row.createCell(5).setCellValue(splited[4]);
                row.createCell(6).setCellValue(splited[5]);
                row.createCell(7).setCellValue(splited[6]);}
            }
//
//    }
            return counter;
        }
}
