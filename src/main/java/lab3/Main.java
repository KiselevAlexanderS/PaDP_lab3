package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Main {
    private static JavaRDD<String> flights;
    private static JavaRDD<String> airports;

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext context = new JavaSparkContext(conf);

        flights = context.textFile(args[0]);
        airports = context.textFile(args[1]);
        String finalFlights = flights.first();
        String finalAirports = airports.first();
        flights = flights.filter(a -> !a.equals(finalFlights));
        airports = airports.filter(a -> !a.equals(finalAirports));
    }

}
