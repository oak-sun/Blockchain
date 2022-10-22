package blockchain.block;


import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;
import static blockchain.StringUtil.applySha256;

@Data
public class Block implements Serializable {

    @Serial
    private static final long serialVersionUID = 1738588544404978242L;
    private final int id;
    private final long timestamp;
    private final String prevBlockHash;
    private long magicNumber;
    private String blockHash;
    private int timeToGenerate;

    public static Block getProved(int id,
                                  String prevBlockHash,
                                  int zeroes) {

        final var startTime = Instant.now();
        final var block = new Block(id,
                                    new Date().getTime(),
                                    prevBlockHash);
        block.findMagicNumber(zeroes);
        block.timeToGenerate = Math.toIntExact(
                        Duration
                                .between(startTime, Instant.now())
                                .toSeconds());
        return block;
    }
    private Block(int id,
                  long timestamp,
                  String prevBlockHash) {
        this.id = id;
        this.timestamp = timestamp;
        this.prevBlockHash = prevBlockHash;
    }

    @Override
    public String toString() {
        return String.format("""
                                Block:\s
                                Id: %d\s
                                Timestamp: %d\s
                                Magic number: %d\s
                                Hash of the previous block:\s
                                %s\s
                                Hash of the block:\s
                                %s\s
                                Block was generating for %d seconds""",
                id,
                timestamp,
                magicNumber,
                prevBlockHash,
                blockHash,
                timeToGenerate);
    }

    private void findMagicNumber(int zeroes) {
        final var random = new Random();
        var hash = "";
        do {
            magicNumber = random.nextLong();
            hash = applySha256(stringify());
        } while (!isProved(zeroes, hash));
        blockHash = hash;
    }
    public boolean isProved(int zeroes) {
        return isProved(zeroes,
                applySha256(stringify()));
    }

    private boolean isProved(int zeroes, String blockHash) {
        for (int i = 0; i < zeroes; i++) {
            if (blockHash.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    private String stringify() {
        return "" +
                id +
                timestamp +
                prevBlockHash +
                magicNumber;
    }
}
