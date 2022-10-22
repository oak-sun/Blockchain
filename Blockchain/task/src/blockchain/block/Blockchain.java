package blockchain.block;

import blockchain.util.Pause;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

@Data
public
class Blockchain implements Serializable {

    @Serial
    private static final long serialVersionUID = 9L;
    ArrayList<Block> blocks = new ArrayList<>();
    private int zeroesInHash;
    Blockchain(int zeroesInHash) {
        this.zeroesInHash = zeroesInHash;
    }
    public int size() {
        return blocks.size();
    }

    Block getBlockBy(int id) {
        return blocks.get(id - 1);
    }
    Block getLastBlock() {
        return (size() > 0) ?
                blocks.get(size() - 1)
                : null;
    }
    void add(Block block) {
        if (canAdd(block)) {
            blocks.add(block);
            adjustZeroesInHash();
        }
    }
    void adjustZeroesInHash() {
        var elapsedTime = Pause.getElapsedSeconds();
        printGenerationTimeMsg(elapsedTime);

        if (elapsedTime > 60) {
            zeroesInHash -= 1;
            System.out.printf("N was decreased to %d\n\n",
                              zeroesInHash);
        } else if (elapsedTime < 10) {
            zeroesInHash += 1;
            System.out.printf("N was increased to %d\n\n",
                              zeroesInHash);
        } else
            System.out.println("N stays the same\n\n");
    }
    private static void printGenerationTimeMsg(long sec) {
        System.out.printf(
                "Block was generating for %d seconds\n", sec);
    }
    boolean canAdd(Block block) {
        return block
                .hasValid(zeroesInHash)
                &
                canHaveValid(block);
    }

    boolean canHaveValid(Block block) {
        var id = block.id;
        var isValid = true;
        if (id > 1) {
            var previousHash = block.previousHash;
            var previousBlock = getBlockBy(id - 1);
            var hash = previousBlock.generateHash();
            if (!hash.equals(previousHash)) {
                isValid = false;
            }
        }
        return isValid;
    }
    boolean isValid() {
        var chainIsValid = true;
        for (Block bl : blocks) {
            if (!canHaveValid(bl)) {
                chainIsValid = false;
                break;
            }
        }
        return chainIsValid;
    }
    void printAllBlock() {
        for (Block bl : blocks) {
            bl.printInfo();
            System.out.println();
        }
    }
}