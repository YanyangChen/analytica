package sample;

public class XML2Json {
    public XML2Json(){

    }

    public void Cnvrt2Json(String str)
    {
        String title = str;
        System.out.println("str: " + title);
        System.out.println("title.indexOf('-'): " + title.indexOf('-'));
        System.out.println(" title.indexOf(']'): " +  title.indexOf(']'));
//                   System.out.println("title.indexOf('-'): " + title.indexOf('-'));
        System.out.println("{ \n \"id\" : \"" +
                title.substring(title.indexOf('-')
                        + 1, title.indexOf(']'))+ "\", \n \"title\" : \""+
                title.substring(title.indexOf(']')+2, title.length())+ "\",");
    }
}
