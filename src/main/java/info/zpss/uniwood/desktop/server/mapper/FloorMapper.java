package info.zpss.uniwood.desktop.server.mapper;

import info.zpss.uniwood.desktop.server.model.Floor;

import java.util.List;

public interface FloorMapper {
    Floor getFloor(Integer floorID, Integer postID);

    List<Floor> getFloors(Integer postID);

    Integer addFloor(Integer postID, Integer authorID, String content);    // 传入贴子ID，作者ID，内容，返回新建楼层号

    void deleteFloor(Integer floorID, Integer postID);

    void updateFloor(Integer floorID, Integer postID, String content);
}