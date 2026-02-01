package org.gfg.mmtdemo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MmtDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<FlightSearchInterface> flightSearchInterfaceList = new ArrayList<>();
        flightSearchInterfaceList.add(new IndigoFlightSearchService());
        flightSearchInterfaceList.add(new AirIndiaFlightSearchService());

        MMTSearchService mmtSearchService = new MMTSearchService(flightSearchInterfaceList);
        long start = System.currentTimeMillis();
        System.out.println(mmtSearchService.getAllFlightData("A","B", new Date()));
        long end = System.currentTimeMillis();
        System.out.println("total time:"+(end-start)+" ms");
    }
}
