import java.time.LocalDateTime;
import java.util.ArrayList;

public class BlockChain{

    ArrayList<Block> block_chain;

    public static void main(String[] args){
        new BlockChain();
    }

    public BlockChain(){

        block_chain = new ArrayList<Block>();

        Block genesis_block = new Block("-","-",0,0,LocalDateTime.now(),"-");
        block_chain.add(genesis_block);
        printBlockChain();
    }

    public void printBlockChain(){
        for(int i = 0;i<block_chain.size();i++){
            (block_chain.get(i)).printBlock();
        }
    }

}