package org.gfg.mmtdemo;

import java.util.*;
import java.util.concurrent.*;

public class MMTSearchService {

    private ExecutorService executorService = Executors.newFixedThreadPool(10); // Use ThreadPool in production

    private List<FlightSearchInterface> flightSearchInterfaces;

    public MMTSearchService( List<FlightSearchInterface> flightSearchInterfaces) {
        this.flightSearchInterfaces = flightSearchInterfaces;
    }

    public List<FlightData> getAllFlightData(String src, String dest, Date date) throws ExecutionException, InterruptedException {


        List<FlightData> result = new ArrayList<>();
        List<Future<List<FlightData>>> futureList = new ArrayList<>();

        Map<Future<List<FlightData>>, FlightSearchInterface> mapFutureWithImpl = new HashMap<>();

        for(FlightSearchInterface searchInterface: flightSearchInterfaces){
            Callable<List<FlightData>> task = () -> searchInterface.getFlightData(src,dest,date);
            Future<List<FlightData>> flightDataFuture = executorService.submit(task);
            futureList.add(flightDataFuture);
            mapFutureWithImpl.put(flightDataFuture, searchInterface);
        }

        //FlightSearchInterface searchInterfaceAirIndia = new AirIndiaFlightSearchService();
        //Callable<List<FlightData>> airIndiaTask = () -> searchInterfaceAirIndia.getFlightData(src,dest,date);
        // List<FlightData> airIndiaFlightData = searchInterfaceAirIndia.getFlightData(src,dest,date);


       // FlightSearchInterface searchInterfaceIndigo = new IndigoFlightSearchService();
        //Callable<List<FlightData>> indigoTask = () -> searchInterfaceIndigo.getFlightData(src,dest,date);
        //List<FlightData> indigoFlightData = searchInterfaceIndigo.getFlightData(src,dest,date);

       // Future<List<FlightData>> airIndiaFlightDataFuture = executorService.submit(airIndiaTask);
        //Future<List<FlightData>> indigoFlightDataFuture = executorService.submit(indigoTask);

//        result.addAll(airIndiaFlightData);
//        result.addAll(indigoFlightData);

        //result.addAll(airIndiaFlightDataFuture.get());
        //result.addAll(indigoFlightDataFuture.get());

        futureList.forEach(x -> {
            try {
                if(x.isDone() && !mapFutureWithImpl.get(x).isError()){
                    result.addAll(x.get());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });


        return result;
    }

}
