package info.zpss.uniwood.server.dao;

import info.zpss.uniwood.server.entity.Zone;

import java.util.List;

public interface ZoneDao {
    Zone getZone(Integer zoneID);

    List<Zone> getZones();

    List<Zone> getZonesByUserID(Integer userID);

    Integer addZone(Zone zone);    // 返回新建分区的ID

    void updateZone(Zone zone);

    void deleteZone(Integer zoneID);
}
