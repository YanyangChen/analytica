package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.*;
import java.util.Scanner;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
public class Controller2 implements Initializable{
    public ArrayList<String> stks;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Properties pty = new Properties();
//        File f = new File("resource.cntion.properties");
//        System.out.println("show absolute" +f.getAbsolutePath());
//        //System.out.println("get absolute " + f.getPath());
//        if (f.exists()) {
//            System.out.println("file found: " + f.getAbsolutePath());
//        } else {
//            System.out.println("file not exists ! NOT expected");
//        }
        InputStream in = getClass().getResourceAsStream("/tickets.properties");
        try {
            pty.load(in);
            Update.getUpdate();
            Update.countStartsecs("01012016");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stks = new ArrayList<String>();
        for (Integer i = 1; i <= 1949; i++){

        stks.add(pty.getProperty(i.toString()));

        }
        int j = 0;
//        for(String stk : stks) {
//            j++;
//            System.out.println(j +" tickets is :" + stk);
//        }
    }
    int counter = 0;
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

        org.jsoup.nodes.Document doc = Jsoup.parse(Connection.cntAndGetString("https://www.mpfinance.com/fin/main.php"));
        Elements ul = doc.select("ul");
        Elements li = ul.select("li");
        for (int i = 0; i < li.size(); i++)
            System.out.print(li.get(i).text() + "\n") ;

    }

    @FXML  protected void getNewsFromHackerNews (ActionEvent event) throws Exception {
//        URL url = new URL("https://news.ycombinator.com/");
////        int gap = 3600*24*120;
////        Integer start = 1107792000;
////        Integer end = 1454860800;
////        org.jsoup.nodes.Document doc = Jsoup.connect("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d").proxy(proxy).get();
//
////        URL url = new URL("https://finance.yahoo.com/quote/0700.HK/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");
//
////       for(Integer i=start.intValue(); i < end.intValue(); i += gap ) {
////           Integer i2 = i + gap;
////           getData("0700.HK", i.toString(), i2.toString());
////       }
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
////        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
////        System.setProperty("http.proxyPort", "8080");
//        HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);
////
//        uc.connect();
////
//        String line = null;
//        StringBuffer tmp = new StringBuffer();
//        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//        while ((line = in.readLine()) != null) {
//            tmp.append(line);
////        }
//        //---------------------------------------------------------------------------
////        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
////        System.setProperty("http.proxyPort", "8080");
            org.jsoup.nodes.Document doc = Jsoup.parse(Connection.cntAndGetString("https://news.ycombinator.com/"));
            Elements tbody = doc.select("tbody");
            Elements tr = tbody.select("tr");
            for (int i = 0; i < tr.size(); i++)
                System.out.print(tr.get(i).text() + "\n");

        }

    @FXML  protected void getNewsFromnewsAM730 (ActionEvent event) throws Exception {
//        URL url = new URL("https://www.am730.com.hk/latest_news/%E8%B2%A1%E7%B6%93");
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

        org.jsoup.nodes.Document doc = Jsoup.parse(Connection.cntAndGetString("https://www.am730.com.hk/latest_news/%E8%B2%A1%E7%B6%93"));
        Elements tbody = doc.getElementsByClass("col-xs-12 news-article-title text-overflowed twoLine newsTitle");
//        Elements tr = tbody.select("tr");
        for (int i = 0; i < tbody.size(); i++)
            System.out.print(tbody.get(i).text() + "\n") ;

    }

    //https://finance.yahoo.com/quote/0700.HK/history?period1=1076169600&period2=1518019200&interval=1d&filter=history&frequency=1d



    @FXML protected void getTencentStock(ActionEvent event) throws Exception {
        int gap = 3600*24*60;//second 60 is the time period
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
        Update.getUpdate();
        Update.countStartsecs("01012010");
        String file = "ts0700HK.xls";

//        POIFSFileSystem fs = new POIFSFileSystem(new FileOutputStream(file));

        FileOutputStream fs = new FileOutputStream(file);
        HSSFWorkbook wb =  new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("test");
        HSSFRow row;
        HSSFCell cell;

        int gap = 3600*24* 60;//second 60 is the time period
//        Integer start = 1087315200;
        Integer start = Update.startsecs;
//        Integer start = 1518018400;
        Integer end = Update.todaysecs;
        System.out.println("Update.todaysecs : " + end);
        System.out.println("Update.startsecs : " + Update.startsecs);
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




    void getData(String stock, String start, String end) throws Exception {
//        URL url = new URL("https://finance.yahoo.com/quote/" + stock + "/history?period1=" + start.toString() + "&period2=" + end.toString() + "&interval=1d&filter=history&frequency=1d");
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
//        HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);
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
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
        org.jsoup.nodes.Document doc = Jsoup.parse(Connection.cntAndGetString("https://finance.yahoo.com/quote/" + stock + "/history?period1=" + start.toString() + "&period2=" + end.toString() + "&interval=1d&filter=history&frequency=1d"));
        Elements tbody = doc.select("tbody");
        Elements tr = tbody.select("tr");
        int counter = 0;
        ArrayDeque<String> stkque = new ArrayDeque<String>();
        for (int i = 0; i < tr.size(); i++) {
//            System.out.print(tr.get(i).text() + "\n") ;
            stkque.addFirst(tr.get(i).text());
        }
        for (int i = 0; i < tr.size(); i++) {
//            System.out.print(tr.get(i).text() + "\n") ;

            System.out.println(stkque.pollFirst() + "\n");
        }
//
    }
        int getData2excel(String stock, String start, String end, HSSFSheet sheet, int counter) throws Exception{
//            URL url = new URL("https://finance.yahoo.com/quote/"+stock+"/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
//            System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//            System.setProperty("http.proxyPort", "8080");
//            HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
//
//            uc.connect();
//
//            String line = null;
//            StringBuffer tmp = new StringBuffer();
//            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//            while ((line = in.readLine()) != null) {
//                tmp.append(line);
//            }
            //---------------------------------------------------------------------------
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
            org.jsoup.nodes.Document doc = Jsoup.parse(Connection.cntAndGetString("https://finance.yahoo.com/quote/"+stock+"/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d"));
            Elements tbody = doc.select("tbody");
            Elements tr = tbody.select("tr");

            ArrayDeque<String> stkque = new ArrayDeque<String>();
            for (int i =  tr.size() -1 ; i >= 0; i--)
            {
            System.out.println(tr.get(i).text() + "\n") ;
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

        @FXML protected void StocksTest(ActionEvent event) throws Exception {
        for(int i = 1170; i <1171; i++){
         Stock stk = new Stock(stks.get(i));
         stk.getStock2Excel();
//         stk.update();
        }
        }

    @FXML protected void StocksUpdate(ActionEvent event) throws Exception {
        for(int i = 611; i <613; i++){
            Stock stk = new Stock(stks.get(i));
//            stk.getStock2Excel();
         stk.update();
         stk.Update2excel();
        }
    }

    @FXML protected void Test(ActionEvent event) throws Exception {

            Stock stk = new Stock("0700.HK");
//            stk.getStock2Excel();
           stk.readFromExcel();
           stk.getLast7sData();

    }

    @FXML protected void TestBinPattern(ActionEvent event) throws Exception {

        String bided = "";
        for(int i = 0; i <500; i++){
            Stock stk = new Stock(stks.get(i));
//            stk.getStock2Excel();

            stk.readFromExcel();
            if (stk.stkrcds.size()>7){
            stk.getLast7sData();}
            if (stk.binpattern.contains("111"))
            {
                bided += stk.ticket + "\n";
            }
        }

        System.out.println(bided);
//        Stock stk = new Stock("0700.HK");
////            stk.getStock2Excel();
//        stk.readFromExcel();
//        stk.getLast7sData();

    }

    @FXML protected void getQuestion (ActionEvent event) throws Exception {
        Scanner s = null;
        String summary ="";
        String title = "";
        boolean summary_flag = false;
        boolean title_flag = false;

        try {
            s = new Scanner(new BufferedReader(new FileReader("xml.txt")));

            while (s.hasNextLine()) {
                String str = s.nextLine().toString();
//                String str2 = s.nextLine().toString();
                if( str.contains("<title>") ) title_flag = true;
               if( title_flag || str.contains("<title>") || str.contains("</title>"))
               {
                    title += str;

               }
                if( str.contains("</title>")){
                   title_flag = false;
                    String title_replaced=title.replace("<title>","").replace("</title>","").replace("\"","\\\"");
                    System.out.println("{");
                    System.out.println("\"id\" : " + title_replaced.substring(title_replaced.indexOf("-")+1,title_replaced.indexOf("]")) +",");
                    System.out.println("\"title\" : \"" + title_replaced.substring(title_replaced.indexOf("]")+2,title_replaced.length())+"\",");
                    title = "";
               }

                if( str.contains("<summary>") ) summary_flag = true;
                if( summary_flag || str.contains("<summary>") || str.contains("</summary>"))
                {
                    summary += str;

                }
                if( str.contains("</summary>")){
                    summary_flag = false;
                    String summary_replaced=summary.replace("<summary>","").replace("</summary>","").replace("\"","\\\"");
                    System.out.println("\"body\" : \"" + summary_replaced + "\"");
                    System.out.println("},");
                    summary = "";
                }


            }
        } finally {
            if (s != null) {
                s.close();
            }
    }
    }
}

