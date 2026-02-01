package org.gfg.mmtdemo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndigoFlightSearchService implements FlightSearchInterface{
    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public List<FlightData> getFlightData(String src, String dest, Date date) {
        /*
        Indigo APIs
         */
        System.out.println("Executing IndigoFlightSearchService in:"+Thread.currentThread().getName());
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<FlightData> flightData = new ArrayList<>();
        flightData.add(new FlightData("A","B",10000.00));
        flightData.add(new FlightData("A","B",9000.00));
        flightData.add(new FlightData("A","B",11000.00));
        return flightData;
    }
}
