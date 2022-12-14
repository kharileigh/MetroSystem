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

    private static final BigDecimal ADJACENT_COST = new BigDecimal("1.50");


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
    public BigDecimal checkRoute(String sourceStation, String destinationStation) {
        Station sourceStationObject = stationDao.getStationByStationName(sourceStation);
        Station destinationStationObject = stationDao.getStationByStationName(destinationStation);
        BigDecimal source = new BigDecimal(String.valueOf(sourceStationObject.getStationId()));
        BigDecimal destination = new BigDecimal(String.valueOf(destinationStationObject.getStationId()));

        BigDecimal distance;

        if (source == destination)
            return new BigDecimal("0.00");
        else if (source.compareTo(destination) > 0) {
            distance = source.subtract(destination);
        } else {
            distance = destination.subtract(source);
        }

        BigDecimal price = ADJACENT_COST.multiply(distance);

        return price;

    }
}
