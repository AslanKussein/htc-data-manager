package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Building;

public interface BuildingService {
    Building getByPostcode(String postcode);
}
