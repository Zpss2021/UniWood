package info.zpss.uniwood.server.dao.impl;

import info.zpss.uniwood.server.Main;
import info.zpss.uniwood.server.dao.ZoneDao;
import info.zpss.uniwood.server.entity.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZoneDaoImpl implements ZoneDao {
    private static final ZoneDao INSTANCE = new ZoneDaoImpl();

    private ZoneDaoImpl() {
    }

    public static ZoneDao getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized Zone getZone(Integer zoneID) {
        Zone zone = new Zone();
        String sql = "SELECT * FROM tb_zone WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, zoneID);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                zone.setId(resultSet.getInt("id"));
                zone.setName(resultSet.getString("name"));
                zone.setIcon(resultSet.getString("icon"));
                zone.setDescription(resultSet.getString("description"));
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return zone;
    }

    @Override
    public synchronized List<Zone> getZones() {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT * FROM tb_zone";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                Zone zone = new Zone();
                zone.setId(resultSet.getInt("id"));
                zone.setName(resultSet.getString("name"));
                zone.setIcon(resultSet.getString("icon"));
                zone.setDescription(resultSet.getString("description"));
                zones.add(zone);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return zones;
    }

    @Override
    public synchronized List<Zone> getZonesByUserID(Integer userID) {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT * FROM tb_zone WHERE id IN (SELECT zone_id FROM tb_user_zone WHERE user_id = ?)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                Zone zone = new Zone();
                zone.setId(resultSet.getInt("id"));
                zone.setName(resultSet.getString("name"));
                zone.setIcon(resultSet.getString("icon"));
                zone.setDescription(resultSet.getString("description"));
                zones.add(zone);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return zones;
    }

    @Override
    public synchronized Integer addZone(Zone zone) {
        String sql_add = "INSERT INTO tb_zone (name, icon, description) VALUES (?, ?, ?)";
        String sql_get = "SELECT id FROM tb_zone WHERE name = ?";
        Integer id = null;
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmtAdd = conn.prepareStatement(sql_add);
            preStmtAdd.setString(1, zone.getName());
            preStmtAdd.setString(2, zone.getIcon());
            preStmtAdd.setString(3, zone.getDescription());
            preStmtAdd.executeUpdate();
            preStmtAdd.close();
            PreparedStatement preStmtGet = conn.prepareStatement(sql_get);
            preStmtGet.setString(1, zone.getName());
            ResultSet resultSet = preStmtGet.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt("id");
            resultSet.close();
            preStmtGet.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return id;
    }

    @Override
    public synchronized void updateZone(Zone zone) {
        String sql = "UPDATE tb_zone SET name = ?, icon = ?, description = ? WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, zone.getName());
            preStmt.setString(2, zone.getIcon());
            preStmt.setString(3, zone.getDescription());
            preStmt.setInt(4, zone.getId());
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }

    @Override
    public synchronized void deleteZone(Integer zoneID) {
        String sql = "DELETE FROM tb_zone WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, zoneID);
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }
}