package oet.wouter.primes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Primes {

    public static int[] get() {
        try {
            String primes = new String(Files.readAllBytes(Paths.get("src/main/resources/primes.txt")));
            String[] split = primes.split(",");
            return Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//    return new int[]{2,3,5,6,7,8,10,11,12,13,14};
    }

}
