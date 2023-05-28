package info.zpss.uniwood.server.service;

import info.zpss.uniwood.server.service.impl.ZoneServiceImpl;

public interface ZoneService {
    // TODO
    static ZoneService getInstance() {
        return ZoneServiceImpl.getInstance();
    }

    String[] getUniversities();

    Integer getZoneID(String zoneName);
}
