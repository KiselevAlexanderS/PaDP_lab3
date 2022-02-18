package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Iterator;
import java.util.Map;

public class Main {
    private static JavaRDD<String> flights;
    private static JavaRDD<String> airports;
    private static JavaPairRDD<String,String> airport;
    private static JavaPairRDD<Tuple2<String,String>, Flight> flight;

    private static void map_to_pair() {
        airport = airports.mapToPair(
                line -> {
                    String[] cols = line.split(",");
                    String code = cols[0].replace("\"","");
                    String description = cols[1].replace("\"","");
                    return new Tuple2<>(code, description);
                });

        flight = flights.mapToPair(
                line -> {
                    String[] cols = line.split(",");
                    boolean cancelled = Float.parseFloat(cols[19]) != 0;
                    String departure = cols[11];
                    System.out.println(cols[19]);
                    String destination = cols[14];
                    float timeOfDelay = cols[18].isEmpty() ? 0 : Float.parseFloat(cols[18]);
                    return new Tuple2<>(new Tuple2<>(departure,destination),new Flight(departure,destination,cancelled, timeOfDelay));
                });
    }

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext context = new JavaSparkContext(conf);

        flights = context.textFile(args[0]);
        airports = context.textFile(args[1]);
        String finalFlights = flights.first();
        String finalAirports = airports.first();
        flights = flights.filter(a -> !a.equals(finalFlights));
        airports = airports.filter(a -> !a.equals(finalAirports));

        map_to_pair();

        final Broadcast<Map<String,String>> airBrodcast = context.broadcast(airport.collectAsMap());
        flight.groupByKey().mapValues(
                flights -> {
                    Iterator<Flight> iterator = flights.iterator();
                    int canceledAmount = 0;
                    int DelayAmount = 0;
                    int count = 0;
                    float maxDelay = 0;
                    float currDelay = 0;
                    for (Iterator<Flight> flightIter = iterator; iterator.hasNext();) {
                        Flight flight = flightIter.next();
                        currDelay = flight.getDelay();
                        if (flight.isCanceled()) {
                            canceledAmount++;
                        }
                        if (flight.getDelay() > 0) {
                            DelayAmount++;
                            if (flight.getDelay() > maxDelay) {
                                maxDelay = flight.getDelay();
                            }
                        }
                    }
                });
    }

}
