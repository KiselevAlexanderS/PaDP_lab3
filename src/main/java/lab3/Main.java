package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class Main {
    private static JavaRDD<String> flights;
    private static JavaRDD<String> airports;
    private static JavaPairRDD<String,String> airport;
    private static JavaPairRDD<Tuple2<String,String>, Flight> flight;

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext context = new JavaSparkContext(conf);

        flights = context.textFile(args[0]);
        airports = context.textFile(args[1]);
        String finalFlights = flights.first();
        String finalAirports = airports.first();
        flights = flights.filter(a -> !a.equals(finalFlights));
        airports = airports.filter(a -> !a.equals(finalAirports));

        airport = airports.mapToPair(
            line -> {
                String[] cols = line.split(",");
                String code = cols[0].replace("\"","");
                String description = cols[1].replace("\"","");
                return new Tuple2<>(code, description);
            });
    }

}
