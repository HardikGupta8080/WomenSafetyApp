package com.iiitu.WomenSecurity.service;

import com.iiitu.WomenSecurity.model.Coordinates;
import com.iiitu.WomenSecurity.model.EmergencyLocation;
import com.iiitu.WomenSecurity.model.PoliceStation;
import com.iiitu.WomenSecurity.repository.EmergencyRepository;
import com.iiitu.WomenSecurity.repository.PoliceStationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class EmergencyService {

    private static final double EARTH_RADIUS_METERS = 6371000;

    private final EmergencyRepository emergencyRepository;
    private final PoliceStationRepository policeStationRepository;
    private final double defaultRadiusMeters;
    private final int maxLinkedStations;

    public EmergencyService(
            EmergencyRepository emergencyRepository,
            PoliceStationRepository policeStationRepository,
            @Value("${geofencing.default-radius-meters:10000}") double defaultRadiusMeters,
            @Value("${geofencing.max-linked-stations:3}") int maxLinkedStations
    ){
        this.emergencyRepository = emergencyRepository;
        this.policeStationRepository = policeStationRepository;
        this.defaultRadiusMeters = defaultRadiusMeters;
        this.maxLinkedStations = maxLinkedStations;
    }

    public EmergencyLocation saveLocation(EmergencyLocation location){
        normalizeCoordinates(location);
        assignPoliceStations(location);
        return emergencyRepository.save(location);
    }

    private void normalizeCoordinates(EmergencyLocation location) {
        if (location.getCoordinates() == null) {
            location.setCoordinates(new Coordinates(location.getLatitude(), location.getLongitude()));
        }
        if (location.getTime() == null) {
            location.setTime(LocalDateTime.now());
        }
    }

    private void assignPoliceStations(EmergencyLocation emergency) {
        Coordinates coordinates = emergency.getCoordinates();
        if (coordinates == null) {
            return;
        }

        List<StationDistance> sortedStations = policeStationRepository.findAll().stream()
                .map(station -> new StationDistance(
                        station,
                        distanceMeters(
                                coordinates.getLat(),
                                coordinates.getLng(),
                                station.getLatitude(),
                                station.getLongitude()
                        )
                ))
                .sorted(Comparator.comparingDouble(StationDistance::distanceMeters))
                .toList();

        List<String> linkedStationIds = sortedStations.stream()
                .filter(stationDistance -> stationDistance.distanceMeters()
                        <= stationRadiusMeters(stationDistance.station()))
                .limit(maxLinkedStations)
                .map(stationDistance -> stationDistance.station().getBadgeNumber())
                .toList();

        if (linkedStationIds.isEmpty() && !sortedStations.isEmpty()) {
            linkedStationIds = sortedStations.stream()
                    .limit(maxLinkedStations)
                    .map(stationDistance -> stationDistance.station().getBadgeNumber())
                    .toList();
        }

        emergency.setLinkedStationIds(linkedStationIds);
        emergency.setAssignedStationId(linkedStationIds.isEmpty() ? null : linkedStationIds.get(0));
    }

    private double stationRadiusMeters(PoliceStation station) {
        return station.getRadiusMeters() != null ? station.getRadiusMeters() : defaultRadiusMeters;
    }

    private double distanceMeters(double lat1, double lng1, double lat2, double lng2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_METERS * c;
    }

    private record StationDistance(PoliceStation station, double distanceMeters) {
    }
}
