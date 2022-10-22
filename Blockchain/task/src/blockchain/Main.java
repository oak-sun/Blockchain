package blockchain;

import blockchain.block.BlockchainFactory;
import java.util.concurrent.ExecutionException;
public class Main {
    public static void main(String[] args) throws ExecutionException,
                                                 InterruptedException {
        var zeroesInHash = 0;
        var chainFactory = BlockchainFactory
                                          .getInstance();
        var blockChain = chainFactory
                                   .generateChainSizeOf(5,
                                  zeroesInHash);
    }
}



