package sample;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class Connection {
    public static String cntAndGetString (String http) throws Exception{

        InetAddress ipa = InetAddress.getLocalHost();
        String ip = ipa.getHostAddress();
        if(ip.equals("10.15.39.184")){
        URL url = new URL(http);
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
        return String.valueOf(tmp);
        }
        else{
            return String.valueOf(Jsoup.connect(http).get());
        }
    }

}
