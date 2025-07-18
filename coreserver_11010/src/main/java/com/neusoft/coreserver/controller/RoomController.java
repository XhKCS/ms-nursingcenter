package com.neusoft.coreserver.controller;

import com.neusoft.coreserver.entity.ResponseBean;
import com.neusoft.coreserver.entity.Room;
import com.neusoft.coreserver.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin("*")
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomMapper roomMapper;

    @PostMapping("/getById")
    public ResponseBean<Room> getById(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        Room room = roomMapper.selectById(id);
        ResponseBean<Room> rb = null;

        if (room != null) {
            rb = new ResponseBean<>(room);
        } else {
            rb = new ResponseBean<>(500, "不存在该id的房间数据");
        }
        return rb;
    }

    @PostMapping("/getByNumber")
    public ResponseBean<Room> getByNumber(@RequestBody Map<String, Object> request) {
        String roomNumber = (String) request.get("roomNumber");
        Room room = roomMapper.getRoomByNumber(roomNumber);
        ResponseBean<Room> rb = null;

        if (room != null) {
            rb = new ResponseBean<>(room);
        } else {
            rb = new ResponseBean<>(500, "不存在该房间号的房间数据");
        }
        return rb;
    }

    @PostMapping("/listByFloor")
    public ResponseBean<List<Room>> listByFloor(@RequestBody Map<String, Object> request) {
        int floor = (int) request.get("floor");
        List<Room> roomList = roomMapper.listRoomsByFloor(floor);

        ResponseBean<List<Room>> rb = null;
        if (roomList.size() > 0) {
            rb = new ResponseBean<>(roomList);
        } else {
            rb = new ResponseBean<>(500, "不存在该楼层的房间数据");
        }
        return rb;
    }

    @PostMapping("/listAll")
    public ResponseBean<List<Room>> listAll() {
        List<Room> roomList = roomMapper.selectList(null);

        ResponseBean<List<Room>> rb = null;
        if (roomList.size() > 0) {
            rb = new ResponseBean<>(roomList);
        } else {
            rb = new ResponseBean<>(500, "No data");
        }
        return rb;
    }
}
