package org.gfg.mmtdemo;

import java.util.Date;
import java.util.List;

public interface FlightSearchInterface {

    boolean isError();
    List<FlightData> getFlightData(String src, String dest, Date date);
}
