package com.five.service;

import com.five.entity.Station;
import com.five.entity.StationList;
import java.math.BigDecimal;

public interface StationService {
    
    public StationList getAllStations();
    
    public Station getStationByStationName(String stationName);

    BigDecimal checkRoute(String sourceStation, String destinationStation);
}
