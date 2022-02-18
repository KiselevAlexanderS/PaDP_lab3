package lab3;

import scala.Serializable;

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

    public boolean isCanceled() {
        return Canceled;
    }

    public String getArrival() {
        return Arrival;
    }

    public String getDepature() {
        return Depature;
    }

    public int getDelay() {
        return Delay;
    }

    @Override
    public String toString() {
        String str = ""
    }
}
