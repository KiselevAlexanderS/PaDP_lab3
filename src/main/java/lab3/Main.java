package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Main {
    private JavaRDD<String> flights;
    private JavaRDD<String> airports;
    
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext context = new JavaSparkContext(conf);

//        JavaRDD<String> flights = context.textFile(args[0]);
//        JavaRDD<String> airports = context.textFile(args[1]);
        JavaRDD<String> finalFlights = flights;
        JavaRDD<String> finalAirports = airports;

    }

}
