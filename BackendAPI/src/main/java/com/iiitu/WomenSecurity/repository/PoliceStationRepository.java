package com.iiitu.WomenSecurity.repository;

import com.iiitu.WomenSecurity.model.PoliceStation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PoliceStationRepository extends MongoRepository<PoliceStation, String> {
}
