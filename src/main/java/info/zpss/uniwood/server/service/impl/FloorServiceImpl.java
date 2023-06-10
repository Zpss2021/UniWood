package info.zpss.uniwood.server.service.impl;

import info.zpss.uniwood.server.dao.FloorDao;
import info.zpss.uniwood.server.dao.impl.FloorDaoImpl;
import info.zpss.uniwood.server.entity.Floor;
import info.zpss.uniwood.server.service.FloorService;

public class FloorServiceImpl implements FloorService {
    private static final FloorServiceImpl INSTANCE;
    private static final FloorDao floorDao;

    static {
        INSTANCE = new FloorServiceImpl();
        floorDao = FloorDaoImpl.getInstance();
    }

    private FloorServiceImpl() {
    }

    public static FloorServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Floor getFloor(Integer floorID, Integer postID) {
        return floorDao.getFloor(floorID, postID);
    }

    @Override
    public void addFloor(Integer postID, Integer authorID, String content) {
        floorDao.addFloor(postID, authorID, content);
    }
}
