import java.lang.*;
import java.time.*;
import java.util.*;
import java.nio.charset.*;
import java.security.*;

public class Block{
    private Transaction tran;
    private int index;
    private LocalDateTime timeStamp;
    private String previousHash;
    private String myHash;

    public Block(String sp,String rp,int m,int ind,LocalDateTime ts,String ph){
        tran = new Transaction(sp,rp,m);
        index = ind;
        timeStamp = ts;
        previousHash = ph;
        myHash = calcHash(tran,index,timeStamp,previousHash);
    }

    private String calcHash(Transaction ts,int ind,LocalDateTime ldt,String pHash){
        String result = "error";
        try{
        String source = (ts.getString()).concat(String.valueOf(ind)).concat(pHash);
        source = source.concat(ldt.toString());

        Charset charset = StandardCharsets.UTF_8;
        String algorithm = "SHA-512";
        byte[] bytes = MessageDigest.getInstance(algorithm).digest(source.getBytes(charset));
        result = new String(bytes, "UTF-8");
        }catch(Exception e){}
        return result;
    }
    public void printBlock(){
        tran.printTransaction();
        System.out.println("ID　　： " + index);
        System.out.println("年月日： " + timeStamp.toString());
        System.out.println("前塊　： " + previousHash);
        System.out.println("ハシ　： " + myHash);
        return;
    }
}

class Transaction{
    protected String sendPerson;
    protected String receivePerson;
    protected int money;

    public Transaction(String s,String r,int m){
        sendPerson = s;
        receivePerson = r;
        money = m;
    }

    public Transaction getTransaction(){
        return this;
    }

    public String getString(){
        String result;
        result = (sendPerson.concat(receivePerson)).concat(String.valueOf(money));
        return result;
    }

    public void printTransaction(){
        System.out.println("差出人： " + sendPerson);
        System.out.println("あて先： " + receivePerson);
        System.out.println("金額　： " + money);
        return;
    }

}