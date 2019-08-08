import java.lang.*;
import java.time.*;
import java.util.*;
import java.nio.charset.*;
import java.security.*;

public class Block{
    private Transaction tran;//取引データ
    private int index;//通し番号のID
    private String previousHash;//前のブロックのハッシュ値
    private String myHash;//このブロックのハッシュ値
    private LocalDateTime timeStamp;//時間

    private final int difficulty = 4;//ハッシュの条件。ここでは先頭4桁が0でなければならない。
    private int proof;//ハッシュの条件を満たす値。これを探すことをマイニングと呼ぶ。

    //コンストラクタ
    public Block(String sp,String rp,int m,int ind,LocalDateTime ts,String ph){
        tran = new Transaction(sp,rp,m);
        index = ind;
        timeStamp = ts;
        previousHash = ph;
        proof = 0;
        myHash = calcHash(tran,index,previousHash,timeStamp);
    }

    //ハッシュの計算を行う関数
    private String calcHash(Transaction ts,int ind,String pHash,LocalDateTime ldt){
        String result = "errorHash";
        try{
            String source = (ts.getString()).concat(String.valueOf(ind)).concat(pHash);
            source = source.concat(ldt.toString());

            //条件に合うハッシュ値が見つかるまで回す
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
    //詳細を表示する関数
    public void printBlock(){
        tran.printTransaction();
        System.out.println("ID　　： " + index);
        System.out.println("時間　： " + timeStamp);
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
    //ハッシュ値が条件に合うかどうか調べる関数
    private boolean isProofHash(String hash){
        String str = hash.substring(0, difficulty);
        for(int i = 0;i<str.length();i++){
            if(str.charAt(i)!='0'){
                return false;
            }
        }
        return true;
    }
    //byte配列を16新数に変換する関数
    private String byteToString(byte[] bytes){
        String result = "";
        for(int i = 0;i<bytes.length;i++){
            int a16 = bytes[i] + 128;
            result = result.concat(String.format("%02x", a16));
        }
        return result;
    }
    //byte配列の中身を確認する関数(デバッグ用)
    private void bytesCheck(byte[] bytes){
        for(int i = 0;i<bytes.length;i++){
            System.out.println(bytes[i]);
        }
    }
}

//取引に関するデータをまとめたもの
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

    //ハッシュを計算するために渡す文字列を生成する関数
    public String getString(){
        String result;
        result = (sendPerson.concat(receivePerson)).concat(String.valueOf(money));
        return result;
    }

    //詳細を表示する関数
    public void printTransaction(){
        System.out.println("取引データ");
        System.out.println("差出人： " + sendPerson);
        System.out.println("あて先： " + receivePerson);
        System.out.println("金額　： " + money);
        return;
    }

}