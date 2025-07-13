package com.neusoft.coreserver.service;

import com.neusoft.coreserver.entity.Bed;

import java.util.List;
import java.util.Map;

public interface BedService {
    Map<String, List<Bed>> listByFloor(int floor);


}
