package info.zpss.uniwood.server.service;

import info.zpss.uniwood.server.entity.Floor;
import info.zpss.uniwood.server.service.impl.FloorServiceImpl;

public interface FloorService {
    static FloorService getInstance() {
        return FloorServiceImpl.getInstance();
    }

    Floor getFloor(Integer floorID, Integer postID);

    void addFloor(Integer postID, Integer userID, String content);
}
