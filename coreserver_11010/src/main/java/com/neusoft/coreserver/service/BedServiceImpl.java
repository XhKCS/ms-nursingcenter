package com.neusoft.coreserver.service;

import com.neusoft.coreserver.entity.Bed;
import com.neusoft.coreserver.entity.Room;
import com.neusoft.coreserver.mapper.BedMapper;
import com.neusoft.coreserver.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BedServiceImpl implements BedService {
    @Autowired
    private BedMapper bedMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public Map<String, List<Bed>> listByFloor(int floor) {
        List<Room> roomList = roomMapper.listRoomsByFloor(floor);
        Map<String, List<Bed>> resultMap = new HashMap<>();
        for (Room room : roomList) {
            resultMap.put(room.getRoomNumber(), bedMapper.listBedsByRoomNumber(room.getRoomNumber()));
        }
        return resultMap;
    }
}
