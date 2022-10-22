package blockchain.block;

import blockchain.util.StringUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
class Block implements Serializable {

    @Serial
    private static final long serialVersionUID = 8L;
    String previousHash;
    long timesStamp = new Date().getTime();
    int id;
    int magicNumber;
    int minerId;

    Block(String hash, int id) {
        this.previousHash = hash;
        this.id = id;
    }
    void printInfo() {
        var output = "Block:" +
                "\nCreated by miner # " +
                minerId +
                "\nId: " +
                id +
                "\nTimestamp: " +
                timesStamp +
                "\nMagic number: " +
                magicNumber +
                "\nHash of the previous block:\n" +
                previousHash +
                "\nHash of the block:\n" +
                generateHash();
        System.out.println(output);
    }
    boolean hasValid(int zeroesInHash) {
        return generateHash()
                    .matches("0{" + zeroesInHash + "}\\w*");
    }
    String generateHash() {
        return StringUtil
                .applySha256(previousHash +
                             id +
                             minerId +
                             timesStamp +
                             magicNumber);
    }
    String generateHashUsing(int magicNumber) {
        return StringUtil
                .applySha256(previousHash +
                                  id +
                                  minerId +
                                  timesStamp +
                                  magicNumber);
    }
}
