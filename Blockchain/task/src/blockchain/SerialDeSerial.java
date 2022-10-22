package blockchain;

import lombok.NoArgsConstructor;

import java.io.*;


@NoArgsConstructor
public class SerialDeSerial {

    public static void serialize(Object obj, String fileName)
                                            throws IOException {
        try (var fileOut = new FileOutputStream(fileName);

             var bufferedOut = new BufferedOutputStream(fileOut);
             var objectOut = new ObjectOutputStream(bufferedOut)) {
            objectOut.writeObject(obj);
        }
    }

    public static Object deserialize(String fileName)
                   throws IOException, ClassNotFoundException {
        Object obj;
        try (var fileIn = new FileInputStream(fileName);

             var bufferedIn = new BufferedInputStream(fileIn);
             var objectIn = new ObjectInputStream(bufferedIn)) {
            obj = objectIn.readObject();
        }
        return obj;
    }
}
