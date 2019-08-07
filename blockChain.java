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
        for(int i = 0;i<5;i++){
            generateBlock();
        }
        printBlockChain();
    }

    private void generateBlock(){
        Block block = new Block("Tim","Tim",100,block_chain.size(),LocalDateTime.now(),
                                block_chain.get(block_chain.size()-1).getMyHash());
        block_chain.add(block);
    }

    public void printBlockChain(){
        for(int i = 0;i<block_chain.size();i++){
            (block_chain.get(i)).printBlock();
        }
    }

}