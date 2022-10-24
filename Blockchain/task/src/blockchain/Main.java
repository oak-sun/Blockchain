package blockchain;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        var blocksNumber = 5;
        var zeros = 0;
        var miners = 10;
        Executor<Miner> parallelExecutor = new Executor<>(12,
                                                          miners);

        List<List<String>> messagesList = List.of(
                                   List.of(),
                                   List.of("Vladimir: Hey, I'm first!"),
                                   List.of("Oxana: It's not fair!",
                                          "Oxana: You always will be " +
                                                  "first because it is your " +
                                                  "blockchain!",
                                          "Oxana: Anyway, thank you for this" +
                                                  " amazing chat."),
                                   List.of("Vladimir: You're welcome :)",
                                           "Valeriy: Hey Vladimir, nice chat"),
                                   List.of());

        Block cursor = null;

        for (int i = 0; i < blocksNumber; i++) {
            var finalCursor = cursor;
            var finalZeros = zeros;
            List<String> messages = messagesList.get(i);
            Miner miner = parallelExecutor.execute(
                    id -> new Miner(id,
                                    finalCursor,
                                    finalZeros,
                                    messages)::mine);

            cursor = miner.getBlock();
            printBlock(cursor, miner);
            var miningDuration = cursor.getMiningDuration();

            if (miningDuration < 10) {
                zeros++;
                System.out.println("N was increased to "
                                   + zeros);
            } else if (miningDuration > 30) {
                zeros--;
                System.out.println("N was decreased by 1");
            } else {
                System.out.println("N stays the same");
            }
            System.out.println();
        }
    }
    private static void printBlock(Block block,
                                   Miner miner) {
        var message = block.getMessage();
        message = message == null ?
                            "no messages" :
                             System.lineSeparator() + message;
        System.out.printf("Block:%n" +
                        "Created by miner # %d%n" +
                        "Id: %d%n" +
                        "Timestamp: %d%n" +
                        "Magic number: %d%n" +
                        "Hash of the previous block: %n" +
                        "%s%n" +
                        "Hash of the block: %n" +
                        "%s%n" +
                        "Block data: %s%n" +
                        "Block was generating for %d seconds%n",
                        miner.getId(),
                        block.getUid(),
                        block.getTimestamp(),
                        block.getMagicNumber(),
                        block.getPreviousHash(),
                        block.getHash(),
                        message,
                        block.getMiningDuration());
    }
}




