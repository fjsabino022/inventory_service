package com.goeuro.inventory.bdd.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record OmioSearch(
    String message,
    String searchEvent,
    String searchId,
    String arrivalPositionCountry,
    Integer arrivalPositionId,
    String arrivalPositionName,
    String departurePositionCountry,
    Integer departurePositionId,
    String departurePositionName,
    String ferryStatus,
    String flightStatus,
    String trainStatus,
    String busStatus,
    int numberOfBusResults,
    int numberOfFerryResults,
    int numberOfFlightResults,
    int numberOfTrainResults,
    int totalResults,
    int numberOfRoutedBusResults,
    int numberOfRoutedFerryResults,
    int numberOfRoutedFlightResults,
    int numberOfRoutedMixedModeResults,
    int numberOfRoutedRoutes,
    int numberOfRoutedTrainResults,
    int totalRoutedResults) {}
