package com.fice.service;

import java.math.BigDecimal;

public interface StationService {

    BigDecimal checkRoute(String sourceStation, String destinationStation);
}
