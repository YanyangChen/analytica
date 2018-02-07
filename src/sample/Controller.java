package sample;

import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
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
}
