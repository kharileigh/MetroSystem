package com.fice.service;

import com.fice.entity.Station;
import com.fice.persistence.StationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDao stationDao;


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
        double price = distance * 1.50;

        return price;

    }
}
