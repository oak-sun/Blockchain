package blockchain.block;

import blockchain.SerialDeSerial;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Blockchain implements Serializable {

    @Serial
    private static final long serialVersionUID = 3705442926703754261L;
    private final List<Block> blocks;
    private final int zeroes;

    public static Blockchain getInstance(int zeroes) {
        return new Blockchain(zeroes);
    }

    private Blockchain(int zeroes) {
        this.blocks = new ArrayList<>();
        this.zeroes = zeroes;
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        for (var block : blocks) {
            sb.append(block).append("\n\n");
        }
        return String.valueOf(sb);
    }

    public void generateBlocks(int blocksNumber) {
        for (var i = 0; i < blocksNumber; i++) {
            generateBlock();
        }
    }

    private void generateBlock() {
        blocks
                .add(Block.getProved(
                blocks.size(), blocks.isEmpty()
                                ? "0" : blocks
                                 .get(blocks.size() - 1)
                                 .getBlockHash(), zeroes));
        try {
            SerialDeSerial.serialize(this,
                            "./Database.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid() {
        for (var i = 0; i < blocks.size(); i++) {

            if (i == 0) {
                if (!blocks
                        .get(i)
                        .getPrevBlockHash()
                        .equals("0"))
                    return false;
            } else {
                if (!blocks
                        .get(i)
                        .getPrevBlockHash()
                        .equals(
                                blocks
                                        .get(i - 1)
                                        .getBlockHash()))
                    return false;
            }
            if (!blocks.get(i).isProved(zeroes))
                return false;
        }
        return true;
    }
}
