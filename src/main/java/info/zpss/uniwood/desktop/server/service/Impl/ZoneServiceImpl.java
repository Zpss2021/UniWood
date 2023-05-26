package info.zpss.uniwood.desktop.server.service.Impl;

import info.zpss.uniwood.desktop.server.mapper.Impl.ZoneMapperImpl;
import info.zpss.uniwood.desktop.server.mapper.ZoneMapper;
import info.zpss.uniwood.desktop.server.model.Zone;
import info.zpss.uniwood.desktop.server.service.ZoneService;

import java.util.ArrayList;
import java.util.List;

public class ZoneServiceImpl implements ZoneService {
    private static final ZoneServiceImpl INSTANCE;
    private static final ZoneMapper zoneMapper;

    static {
        INSTANCE = new ZoneServiceImpl();
        zoneMapper = ZoneMapperImpl.getInstance();
    }

    private ZoneServiceImpl() {
    }

    public static ZoneServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public String[] getUniversities() {
        List<Zone> zones = zoneMapper.getZones();
        ArrayList<String> univList = new ArrayList<>();
        for (Zone zone : zones)
            if (zone.getName().endsWith("大学"))
                univList.add(zone.getName());
        return univList.toArray(new String[0]);
    }

    @Override
    public Integer getZoneID(String zoneName) {
        List<Zone> zones = zoneMapper.getZones();
        for (Zone zone : zones)
            if (zone.getName().equals(zoneName))
                return zone.getId();
        return null;
    }


    // TODO
}
