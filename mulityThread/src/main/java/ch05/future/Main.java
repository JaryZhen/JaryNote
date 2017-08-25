package ch05.future;

/**
 * Created by Jary on 2017/8/21 0021.
 */
public class Main
{
    public static void main(String[] args) {
        Client c = new Client();
        Data data = c.request("name");
        System.out.println("request over"+data.getResult());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("data: "+ data.getResult());
    }
}
