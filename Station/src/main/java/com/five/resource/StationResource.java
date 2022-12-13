package com.five.resource;

import com.five.entity.Station;
import com.five.entity.StationList;
import com.five.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StationResource {

    @Autowired
    StationService stationService;
    
    @GetMapping(path = "/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    public StationList stationListResource() {
        return stationService.getAllStations();
    }
    
    
    @GetMapping(path = "stations/{stationName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Station stationResource(@PathVariable("stationName") String stationName) {
        return stationService.getStationByStationName(stationName);
    }
    

    @GetMapping(path = "/stations/{sourceStation}/{destinationStation}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String checkRouteResource(@PathVariable("sourceStation") String sourceStation, @PathVariable("destinationStation") String destinationStation) {
        return String.valueOf(stationService.checkRoute(sourceStation,destinationStation));
    }

}
