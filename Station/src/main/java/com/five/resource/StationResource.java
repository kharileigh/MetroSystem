package com.five.resource;

import com.five.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class StationResource {

    @Autowired
    StationService stationService;

    @GetMapping(path = "/stations/{sourceStation}/{destinationStation}", produces = MediaType.TEXT_PLAIN_VALUE)
    public BigDecimal checkRouteResource(@PathVariable("sourceStation") String sourceStation, @PathVariable("destinationStation") String destinationStation) {
        return stationService.checkRoute(sourceStation,destinationStation);
    }

}
