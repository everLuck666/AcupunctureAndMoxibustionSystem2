import com.google.zxing.WriterException;
import net.seehope.Application;
import net.seehope.PasswordDecryptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderTest{
//    String name;
//    public OrderTest(String name){
//        this.name = name;
//    }
//
//    public static synchronized void toLc(String name){
//        for(int i = 0;i<100;i++){
//            System.out.println(name+i);
//        }
//    }
//
//
//    @Override
//    public  void run() {
//      toLc(name);
//    }
//
//    public static void main(String[] args) {
//        OrderTest orderTest1 = new OrderTest("xiao");
//        OrderTest orderTest2 = new OrderTest("tian");
//        OrderTest orderTest3 = new OrderTest("di");
//        orderTest1.start();
//        orderTest2.start();
//        orderTest3.start();
//    }

    @Autowired
    PasswordDecryptService passwordDecryptService;
    @Test
    public void  test(){
        passwordDecryptService.getUserInfo("","","");
    }




}
