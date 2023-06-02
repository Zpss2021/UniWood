package info.zpss.uniwood.server.service.impl;

import info.zpss.uniwood.server.dao.impl.ZoneDaoImpl;
import info.zpss.uniwood.server.dao.ZoneDao;
import info.zpss.uniwood.server.entity.Zone;
import info.zpss.uniwood.server.service.ZoneService;

import java.util.ArrayList;
import java.util.List;

public class ZoneServiceImpl implements ZoneService {
    private static final ZoneServiceImpl INSTANCE;
    private static final ZoneDao zoneDao;

    static {
        INSTANCE = new ZoneServiceImpl();
        zoneDao = ZoneDaoImpl.getInstance();
    }

    private ZoneServiceImpl() {
    }

    public static ZoneServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public String[] getUniversities() {
        List<Zone> zones = zoneDao.getZones();
        ArrayList<String> univList = new ArrayList<>();
        for (Zone zone : zones)
            if (zone.getName().endsWith("大学"))
                univList.add(zone.getName());
        return univList.toArray(new String[0]);
    }

    @Override
    public Integer getZoneID(String zoneName) {
        List<Zone> zones = zoneDao.getZones();
        for (Zone zone : zones)
            if (zone.getName().equals(zoneName))
                return zone.getId();
        return null;
    }

    @Override
    public Zone getZone(Integer zoneID) {
        return zoneDao.getZone(zoneID);
    }

    @Override
    public List<Zone> getZonesByUserId(Integer userID) {
        return zoneDao.getZonesByUserID(userID);
    }
}
