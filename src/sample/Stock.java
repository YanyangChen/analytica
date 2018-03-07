package sample;

import javafx.event.ActionEvent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Stock {
    public String ticket;
    public String binpattern = "";
    private Integer udt_startdate;
    private Integer udt_endate;
    private int udt_step;
    private ArrayList<Float> prices;
    private ArrayList<Float> volumns;
    private int counter = 0;
    ArrayList<stkrcd> stkrcds;
    private HSSFWorkbook wb;
    public Stock(String tk){

        ticket = tk;
    }

    class stkrcd{
        Date date;
        String datestr;
        float open;
        float high;
        float low;
        float close;
        float adj_close;
        float volumn;
        float close_diff;

    }

    Comparator<stkrcd> cii = new Comparator<stkrcd>(){

        @Override
        public int compare(stkrcd i,stkrcd j) {

            return i.date.compareTo(j.date);
        }};

    public void getStock2Excel() throws Exception {
        String path =  "HKstks" + File.separator + ticket + ".xls";
// Use relative path for Unix systems
        File f = new File(path);

        f.getParentFile().mkdirs();
        f.createNewFile();


        FileOutputStream fs = new FileOutputStream(f);
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
        counter = 0; // clear counter before execution
        for(Integer i=start.intValue(); i < end.intValue(); i += gap ) {
            Integer i2 = i + gap;
//            if(i2 + 120 > end) i2 = end;
            if(i2 + gap > end) i2 = end;
            counter =  getData2excel(ticket, i.toString(), i2.toString(),sheet, counter);
        }
        wb.write(fs);
        fs.close();
        wb.close();
    }

    public int getData2excel(String stock, String start, String end, HSSFSheet sheet, int counter) throws Exception{
//        URL url = new URL("https://finance.yahoo.com/quote/"+stock+"/history?period1="+start.toString()+"&period2="+end.toString()+"&interval=1d&filter=history&frequency=1d");
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.edb.gov.hk", 8080)); // or whatever your proxy is
//        System.setProperty("http.proxyHost", "proxy1.edb.gov.hk");
//        System.setProperty("http.proxyPort", "8080");
//        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
//
//        uc.connect();
//
//        String line = null;
//        StringBuilder tmp = new StringBuilder();
//        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//        while ((line = in.readLine()) != null) {
//            tmp.append(line);
//        }
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

            stkque.addFirst(tr.get(i).text());
            if(        !tr.get(i).text().substring(12).contains("-")
                    && !tr.get(i).text().substring(12).contains("Dividend")
                    && !tr.get(i).text().substring(12).contains("Stock Split")){
                counter++;
                HSSFRow row = sheet.createRow(counter);
                row.createCell(0).setCellValue(tr.get(i).text().substring(0,12));

                String[] splited = tr.get(i).text().substring(12).split(" ");
                String replaced=tr.get(i).text().substring(0,12).replace(" ","-").replace(",","");
                System.out.println(ticket + " tr.get(i).text().substring(0,12)" + tr.get(i).text().substring(0,12));
                System.out.println("replaced " + replaced);
                row.createCell(1).setCellValue(replaced);
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

    public void update() throws Exception{ //update everyday stock change
        String file = "HKstks" + File.separator + ticket + ".xls";
//        FileInputStream fi = new FileInputStream(file);
        System.out.println("\"HKstks\" + File.separator + ticket + \".xls\"" + file);
        POIFSFileSystem fs = new POIFSFileSystem( new FileInputStream(file));

         wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        if(sheet.getLastRowNum()>0){  // only update for stocks with data
      String lastday = sheet.getRow(sheet.getLastRowNum()).getCell(1).getStringCellValue();
        System.out.println("sheet.getLastRowNum()).getCell(1).getStringCellValue() :" + lastday);

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
//        lastday.replace(" ","-");
//        lastday.replace(",","");
        date = sdf.parse(lastday);
        cal1.setTime(date);
        cal2.setTime(new Date());
        if(sheet.getLastRowNum() != 0) {
            System.out.println("rows available :" + sheet.getLastRowNum());
            System.out.println("lastday :" + sheet.getRow(sheet.getLastRowNum() - 1).getCell(1).getStringCellValue());
            System.out.println(" cal1.setTime(date); :" + cal1.getTime());
            System.out.println(" daysBetween :" + Update.daysBetween(cal1.getTime(), cal2.getTime()));
            int step = (Update.daysBetween(cal1.getTime(), cal2.getTime())) * 3600 * 24;
            udt_endate = Update.todaysecs;
            udt_startdate = udt_endate - step;
        }
        }
        fs.close();
//        wb.close();

    }

public void Update2excel() throws Exception{
    String path =  "HKstks" + File.separator + ticket + ".xls";
// Use relative path for Unix systems
    File f = new File(path);

    FileOutputStream fso = new FileOutputStream(f);
//    HSSFWorkbook wbo = new HSSFWorkbook(fso);
    HSSFSheet sheeto = wb.getSheetAt(0);
    counter = sheeto.getLastRowNum();
//            for(Integer i=udt_startdate; i < udt_endate; i += udt_step ) {
//        Integer i2 = i + udt_step;
//        if(i2 + 120 > udt_endate) i2 = udt_endate;
    if(udt_startdate != udt_endate){
    if(udt_startdate+60 > udt_endate) { //under 60 days' data fetching workload
        counter = getData2excel(ticket, udt_startdate.toString(), udt_endate.toString(), sheeto, counter);
    }
    else{                                //over 60 days' data fetching workload, set work load to 120 days
        int gap = 3600*24*60;
        for(Integer i=udt_startdate.intValue(); i < udt_endate.intValue(); i += gap ) {
            Integer i2 = i + gap;
//            if(i2 + 120 > end) i2 = end;
            if(i2 + gap > udt_endate.intValue()) i2 = udt_endate.intValue();
            counter =  getData2excel(ticket, i.toString(), i2.toString(),sheeto, counter);
        }
    }}
//    }
            wb.write(fso);
            fso.close();
            wb.close();
}
    public void markUpDown2_binaries() //mark day up as 1 and day down as 0
        {
            //do subtraction to get difference between 2 days

            //if difference is greater than 0, set the binary to 1, otherwise 0
        }

    public void readFromExcel() throws Exception{
        //open excel file
        String file = "HKstks" + File.separator + ticket + ".xls";
//        FileInputStream fi = new FileInputStream(file);
        System.out.println("\"HKstks\" + File.separator + ticket + \".xls\"" + file);
        POIFSFileSystem fs = new POIFSFileSystem( new FileInputStream(file));

        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);

        //read data to arraylist
        stkrcds= new ArrayList<stkrcd>();
        for (int i = 1; i < sheet.getLastRowNum(); i++)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
            stkrcds.add(new stkrcd());
            stkrcds.get(i-1).datestr = sheet.getRow(i).getCell(0).getStringCellValue();
            stkrcds.get(i-1).date = sdf.parse(sheet.getRow(i).getCell(1).getStringCellValue());
            stkrcds.get(i-1).open =Float.parseFloat(sheet.getRow(i).getCell(2).getStringCellValue().replace(",","").replace(".00",""));
            stkrcds.get(i-1).high =Float.parseFloat(sheet.getRow(i).getCell(3).getStringCellValue().replace(",","").replace(".00",""));
            stkrcds.get(i-1).low =Float.parseFloat(sheet.getRow(i).getCell(4).getStringCellValue().replace(",","").replace(".00",""));
            stkrcds.get(i-1).close = Float.parseFloat(sheet.getRow(i).getCell(5).getStringCellValue().replace(",","").replace(".00",""));
            stkrcds.get(i-1).adj_close =Float.parseFloat(sheet.getRow(i).getCell(6).getStringCellValue().replace(",","").replace(".00",""));
            stkrcds.get(i-1).volumn =Float.parseFloat(sheet.getRow(i).getCell(7).getStringCellValue().replace(",","").replace(",","").replace(".00",""));
            if(i>1)stkrcds.get(i-1).close_diff =Float.parseFloat(sheet.getRow(i).getCell(5).getStringCellValue().replace(",","").replace(".00","")) - Float.parseFloat(sheet.getRow(i-1).getCell(5).getStringCellValue().replace(",","").replace(".00",""));
        }


        //close the file
        wb.close();
        fs.close();
    }

    public void getLast7sData(){

//        Float volc = new Float(0.0);
        String datestrc = "";
        //remove duplicate
//        List<stkrcd> al = stkrcds;
        Collections.sort(stkrcds,cii);
        ArrayList<stkrcd> stkrcds2 = new  ArrayList<stkrcd>();
        for (int i =0; i < stkrcds.size(); i++)
        {
//            {stkrcds.remove(i);}
            if (!datestrc.equals(stkrcds.get(i).datestr)){
            datestrc = stkrcds.get(i).datestr;
                System.out.println(datestrc);
                stkrcds2.add(stkrcds.get(i));
            }

        }
        stkrcds = stkrcds2;
        //get last 7 day's data from arraylist
        for (int i =0; i < stkrcds.size()-7; i++)
        {
            stkrcds.remove(i);
        }
        for (int i =stkrcds.size()-7; i < stkrcds.size(); i++)
        {
            System.out.println("date : "+stkrcds.get(i).date+ "  volumn : " + stkrcds.get(i).volumn + "  close_diff : " + stkrcds.get(i).close_diff);
            if (stkrcds.get(i).close_diff / stkrcds.get(i).close > 0.02)
            {binpattern +="1";}
            else{binpattern += "0";}
        }
       System.out.println("binpattern is " + binpattern);
    }
}
