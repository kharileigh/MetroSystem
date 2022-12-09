package com.fice.service;

import com.fice.entity.Station;
import com.fice.persistence.StationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDao stationDao;

    private static final BigDecimal ADJACENT_COST = new BigDecimal("1.50");



    @Override
    public BigDecimal checkRoute(String sourceStation, String destinationStation) {
        Station sourceStationObject = stationDao.getStationByStationName(sourceStation);
        Station destinationStationObject = stationDao.getStationByStationName(destinationStation);
        int source = sourceStationObject.getStationId();
        int destination = destinationStationObject.getStationId();

        int distance;

        if (source == destination)
            return new BigDecimal("0.00");
        else if (source > destination) {
            distance = source - destination;
        } else {
            distance = destination - source;
        }

        BigDecimal price = ADJACENT_COST.multiply(new BigDecimal(String.valueOf(distance)));

        return price;

    }
}
