package blockchain;

import blockchain.managment.TasksManager;

public class Main {
    public static void main(String[] args) {

        var manager = new TasksManager();
        manager.loadBlockchain();
        for (int i = 0; i < 15; i++) {
            manager.addBlock();
            manager.printBlock(
                    manager.getLastBlock());
        }
    }
}



