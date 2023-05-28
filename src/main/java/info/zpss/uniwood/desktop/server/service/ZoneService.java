package info.zpss.uniwood.desktop.server.service;

import info.zpss.uniwood.desktop.server.service.impl.ZoneServiceImpl;

public interface ZoneService {
    // TODO
    static ZoneService getInstance() {
        return ZoneServiceImpl.getInstance();
    }

    String[] getUniversities();

    Integer getZoneID(String zoneName);
}
