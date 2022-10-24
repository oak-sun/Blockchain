package blockchain;

import blockchain.util.StringUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Data
public class Block {
    private final String previousHash;
    private final String hash;
    private final long timestamp;
    private final int uid;
    private final int magicNumber;
    private final int miningDuration;
    private final String message;

    public Block(Block previous,
                 int zeros,
                 List<String> messages) {

        previousHash = previous == null ?
                                "0" : previous.hash;
        timestamp = new Date().getTime();
        uid = previous == null ?
                      0 : previous.uid + 1;
        message = messages
                .isEmpty() ?
                null : messages
                      .stream()
                      .collect(
                              Collectors
                                      .joining(
                                              System.lineSeparator()));

        String hashProbe;
        int magicNumberProbe;
        var timer = System.currentTimeMillis();
        do {
            if (Thread
                    .currentThread()
                    .isInterrupted())
                throw new RuntimeException(
                        new InterruptedException());

            magicNumberProbe = randomInt();
            hashProbe = StringUtil
                             .applySha256(serialize(uid,
                                                   timestamp,
                                                   magicNumberProbe,
                                                   previousHash,
                                                   message));
        } while (!hashProbe
                         .startsWith("0".repeat(zeros)));
        miningDuration = (int) ((System.currentTimeMillis() -
                                 timer)
                                / 1000);
        magicNumber = magicNumberProbe;
        hash = hashProbe;
    }
    private int randomInt() {
        return ThreadLocalRandom
                .current()
                .nextInt();
    }
    private String serialize(int uid,
                             long timestamp,
                             int magicNumber,
                             String previousHash,
                             String message) {
        return String.format("%d-%d-%d-%s-%s",
                             uid,
                             timestamp,
                             magicNumber,
                             previousHash,
                             message);
    }

}