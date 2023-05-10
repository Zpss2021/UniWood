package info.zpss.uniwood.desktop.server.mapper;

import info.zpss.uniwood.desktop.server.model.Zone;

import java.util.List;

public interface ZoneMapper {
    Zone getZone(Integer zoneID);

    List<Zone> getZones();

    List<Zone> getZonesByUserID(Integer userID);

    Integer addZone(Zone zone);    // 返回新建分区的ID

    void updateZone(Zone zone);

    void deleteZone(Integer zoneID);
}
