package sample;


import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.ResourceBundle;
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
public class Controller2 implements Initializable{
    @Override
    public void initialize(URL url, ResourceBundle rb) {



    }

    @FXML  protected void getNewsfromEastmoney (ActionEvent event) throws Exception {


        //proxy connection module
//        URL url = new URL("http://hk.eastmoney.com/news/cggyw.html");
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

        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
        System.setProperty("http.proxyPort", "8080");
        org.jsoup.nodes.Document doc = Jsoup.connect("http://hk.eastmoney.com/news/cggyw.html").get();
        Elements ul = doc.select("ul");
        Elements li = ul.select("li");
        for (int i = 0; i < li.size(); i++) {
           System.out.print( li.get(i).text() +"\n");
        }



}

    @FXML  protected void getNewsFromMingPao (ActionEvent event) throws Exception {
        URL url = new URL("https://www.mpfinance.com/fin/main.php");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);

        uc.connect();

        String line = null;
        StringBuffer tmp = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while ((line = in.readLine()) != null) {
            tmp.append(line);
        }
        //---------------------------------------------------------------------------

        org.jsoup.nodes.Document doc = Jsoup.parse(String.valueOf(tmp));
        Elements ul = doc.select("ul");
        Elements li = ul.select("li");
        for (int i = 0; i < li.size(); i++)
            System.out.print(li.get(i).text() + "\n") ;

    }

    @FXML  protected void getNewsFromHackerNews (ActionEvent event) throws Exception {
//        URL url = new URL("https://news.ycombinator.com/");
        int gap = 3600*24*120;
        Integer start = 1107792000;
        Integer end = 1454860800;
//        org.jsoup.nodes.Document doc = Jsoup.connect("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d").proxy(proxy).get();

//        URL url = new URL("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");

       for(Integer i=start.intValue(); i < end.intValue(); i += gap ) {
           Integer i2 = i + gap;
           getData("0700.HK", i.toString(), i2.toString());
       }
        //        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
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
//        //---------------------------------------------------------------------------
////        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
////        System.setProperty("http.proxyPort", "8080");
//       org.jsoup.nodes.Document doc = Jsoup.parse(String.valueOf(tmp));
//        Elements tbody = doc.select("tbody");
//        Elements tr = tbody.select("tr");
//        for (int i = 0; i < tr.size(); i++)
//            System.out.print(tr.get(i).text() + "\n") ;

    }

    @FXML  protected void getNewsFromnewsAM730 (ActionEvent event) throws Exception {
        URL url = new URL("https://www.am730.com.hk/latest_news/%E8%B2%A1%E7%B6%93");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);

        uc.connect();

        String line = null;
        StringBuffer tmp = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while ((line = in.readLine()) != null) {
            tmp.append(line);
        }
        //---------------------------------------------------------------------------

        org.jsoup.nodes.Document doc = Jsoup.parse(String.valueOf(tmp));
        Elements tbody = doc.getElementsByClass("col-xs-12 news-article-title text-overflowed twoLine newsTitle");
//        Elements tr = tbody.select("tr");
        for (int i = 0; i < tbody.size(); i++)
            System.out.print(tbody.get(i).text() + "\n") ;

    }

    //https://finance.yahoo.com/quote/0700.HK/history?period1=1076169600&period2=1518019200&interval=1d&filter=history&frequency=1d



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


    void getData(String stock, String start, String end) throws Exception{
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
}
