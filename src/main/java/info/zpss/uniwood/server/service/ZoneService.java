package info.zpss.uniwood.server.service;

import info.zpss.uniwood.server.entity.Zone;
import info.zpss.uniwood.server.service.impl.ZoneServiceImpl;

import java.util.List;

public interface ZoneService {
    static ZoneService getInstance() {
        return ZoneServiceImpl.getInstance();
    }

    String[] getUniversities();

    Integer getZoneID(String zoneName);

    Zone getZone(Integer zoneID);

    List<Zone> getZonesByUserId(Integer userID);
}
