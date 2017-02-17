package oet.wouter.primes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collections;

public class Main {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");
    private static final int DIMENSION = 2000;
    private static final int X_OFFSET=DIMENSION/2;
    private static final int Y_OFFSET=DIMENSION/2;

    public static void main(String[] args) throws IOException {
        StringBuilder buffer = new StringBuilder();
        appendHeader(buffer);
        int[] primes = Primes.get();
        System.out.println("Printing " + primes.length + " primes");
        double a = 2;

        int previousSquared = 1;
        int current = 2;
        int currentSquared = 4;

//        print(0*a, 0 , "grey", "", buffer);
//        print(1*a, 0 , "grey", "", buffer);
//        print(2*a, 0 , "grey", "", buffer);

        for (int i = 0; i < 20000; i++) {
            int prime = primes[i];
            if(prime > currentSquared) {
                previousSquared = currentSquared;
                current++;
                currentSquared = current*current;
//                print(current*a, 0 , "grey", ""+currentSquared, buffer);
            }

            int range = currentSquared - previousSquared;
            int value = prime - previousSquared;
            double angle = (0.0 + value)/range;

            double radius = a * (current-1) + (angle * angle);
            double x = radius * Math.cos(angle * 2 * Math.PI);
            double y = radius * Math.sin(angle * 2 * Math.PI);

            print(x, -y, "" + prime, prime, buffer);
        }
        appendFooter(buffer);
        String data = buffer.toString();

        Files.write(Paths.get("/home/wouter/primes.svg"), Collections.singleton(data));
    }

    private static void print(double x, double y, String comment, int prime, StringBuilder buffer) {
        String xx = DECIMAL_FORMAT.format(x + X_OFFSET);
        String yy = DECIMAL_FORMAT.format(y + Y_OFFSET);

        int hue = (int) (2.0 / 3 * Math.sqrt(prime) + 100);
        buffer.append("<circle cx=\"")
                .append(xx)
                .append("\" cy=\"")
                .append(yy)
                .append("\" r=\"2\" ")
                .append("style=\"fill:hsl(").append(hue).append(", 50%, 50%)\"")
                .append(" />")//.append("<text x=\"").append(x+X_OFFSET).append("\" y=\"").append(y+Y_OFFSET).append("\" font-family=\"Verdana\" font-size=\"10\">").append(comment).append("</text>")
                .append('\n');
    }

    private static void appendHeader(StringBuilder builder) {
        builder.append("<svg version=\"1.1\" baseProfile=\"full\" width=\"")
                .append(DIMENSION)
                .append("\" height=\"")
                .append(DIMENSION)
                .append("\" xmlns=\"http://www.w3.org/2000/svg\" style=\"background-color: black\">");
    }

    private static void appendFooter(StringBuilder builder) {
        builder.append("</svg> \n");
    }
}
