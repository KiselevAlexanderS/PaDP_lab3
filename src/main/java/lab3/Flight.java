package lab3;

import java.io.Serializable;

public class Flight implements Serializable {
    private String Depature;
    private String Arrival;
    private int Delay;
    private boolean Canceled;

    Flight(String Depature, String Arrival, boolean Canceled, int Delay) {
        this.Depature = Depature;
        this.Arrival = Arrival;
        this.Canceled = Canceled;
        this.Delay = Delay;
    }
}
