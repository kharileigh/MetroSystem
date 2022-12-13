package com.five.service;

import com.five.entity.Station;
import com.five.entity.StationList;
import com.five.persistence.StationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDao stationDao;

    private static final double ADJACENT_COST = 1.50;


     @Override
    public StationList getAllStations() {
        
        StationList stationList = new StationList();
        
        stationList.setStations(stationDao.findAll());
        
        return stationList;
        
    }
    

    @Override
    public Station getStationByStationName(String stationName) {
        
        return stationDao.getStationByStationName(stationName);
    }
    

    @Override
    public double checkRoute(String sourceStation, String destinationStation) {
        Station sourceStationObject = stationDao.getStationByStationName(sourceStation);
        Station destinationStationObject = stationDao.getStationByStationName(destinationStation);
        int source = sourceStationObject.getStationId();
        int destination = destinationStationObject.getStationId();

        int distance;

        if (source == destination)
            return 0;
        else if (source > destination) {
            distance = source - destination;
        } else {
            distance = destination - source;
        }

        double price = ADJACENT_COST * distance;

        return price;

    }

   
}
