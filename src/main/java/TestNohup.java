/**
 * Created by Jary on 2017/8/2 0002.
 */
public class TestNohup {
    public static void main(String[] args) {
        int i  = 0;
        while (i<= 3){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sdfasdfa");
            i++;
        }
    }
}
