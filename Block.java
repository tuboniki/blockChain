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

    private int proof;
    private final int difficulty = 4;

    public Block(String sp,String rp,int m,int ind,LocalDateTime ts,String ph){
        tran = new Transaction(sp,rp,m);
        index = ind;
        timeStamp = ts;
        previousHash = ph;
        proof = 0;
        myHash = calcHash(tran,index,timeStamp,previousHash);
    }

    private String calcHash(Transaction ts,int ind,LocalDateTime ldt,String pHash){
        String result = "errorHash";
        try{
            String source = (ts.getString()).concat(String.valueOf(ind)).concat(pHash);
            source = source.concat(ldt.toString());

            while(!isProofHash(result)){
                proof++;
                String proofString = source.concat(String.valueOf(proof));
                Charset charset = StandardCharsets.UTF_8;
                String algorithm = "SHA-512";
                byte[] bytes = MessageDigest.getInstance(algorithm).digest(proofString.getBytes(charset));
                //bytesCheck(bytes);
                result = byteToString(bytes);
            }
        }catch(Exception e){}
        return result;
    }
    public void printBlock(){
        tran.printTransaction();
        System.out.println("ID　　： " + index);
        System.out.println("年月日： " + timeStamp.toString());
        System.out.println("前ハシ： " + previousHash);
        System.out.println("ハシ　： " + myHash);
        System.out.println("プルフ： " + proof);
        System.out.println("難しさ： " + difficulty);
        System.out.println("");
        return;
    }
    public String getMyHash(){
        return myHash;
    }
    private boolean isProofHash(String hash){
        String str = hash.substring(0, difficulty);
        for(int i = 0;i<str.length();i++){
            if(str.charAt(i)!='0'){
                return false;
            }
        }
        return true;
    }
    private String byteToString(byte[] bytes){
        String result = "";
        for(int i = 0;i<bytes.length;i++){
            int a16 = bytes[i] + 128;
            result = result.concat(String.format("%02x", a16));
        }
        return result;
    }
    private void bytesCheck(byte[] bytes){
        for(int i = 0;i<bytes.length;i++){
            System.out.println(bytes[i]);
        }
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