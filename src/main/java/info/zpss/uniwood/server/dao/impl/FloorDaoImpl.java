package info.zpss.uniwood.server.dao.impl;

import info.zpss.uniwood.server.Main;
import info.zpss.uniwood.server.dao.FloorDao;
import info.zpss.uniwood.server.entity.Floor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FloorDaoImpl implements FloorDao {
    private static final FloorDao INSTANCE = new FloorDaoImpl();

    private FloorDaoImpl() {
    }

    public static FloorDao getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized Floor getFloor(Integer floorID, Integer postID) {
        Floor floor = new Floor();
        String sql = "SELECT * FROM tb_floor WHERE id = ? AND post_id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, floorID);
            prepStmt.setInt(2, postID);
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                floor.setId(resultSet.getInt("id"));
                floor.setAuthorId(resultSet.getInt("author_id"));
                floor.setCreateTime(resultSet.getTimestamp("create_time"));
                floor.setContent(resultSet.getString("content"));
            }
            resultSet.close();
            prepStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }

        return floor;
    }

    @Override
    public synchronized List<Floor> getFloors(Integer postID) {
        List<Floor> floors = new ArrayList<>();
        String sql = "SELECT * FROM tb_floor WHERE post_id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, postID);
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Floor floor = new Floor();
                floor.setId(resultSet.getInt("id"));
                floor.setAuthorId(resultSet.getInt("author_id"));
                floor.setCreateTime(resultSet.getTimestamp("create_time"));
                floor.setContent(resultSet.getString("content"));
                floors.add(floor);
            }
            resultSet.close();
            prepStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return floors;
    }

    @Override
    @SuppressWarnings("SqlInsertValues")
    public synchronized Integer addFloor(Integer postID, Integer authorID, String content) {
        String sql_add = "INSERT INTO tb_floor (post_id, author_id, content) VALUES (?, ?, ?)";
        String sql_get = "SELECT MAX(id) FROM tb_floor WHERE post_id = ? ";
        Integer id = null;
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmtAdd = conn.prepareStatement(sql_add);
            preStmtAdd.setInt(1, postID);
            preStmtAdd.setInt(2, authorID);
            preStmtAdd.setString(3, content);
            preStmtAdd.executeUpdate();
            preStmtAdd.close();
            PreparedStatement preStmtGet = conn.prepareStatement(sql_get);
            ResultSet resultSet = preStmtGet.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt(1);
            resultSet.close();
            preStmtGet.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return id;
    }

    @Override
    public synchronized void deleteFloor(Integer floorID, Integer postID) {
        String sql = "DELETE FROM tb_floor WHERE id = ? AND post_id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, floorID);
            prepStmt.setInt(2, postID);
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }

    @Override
    public synchronized void updateFloor(Integer floorID, Integer postID, String content) {
        String sql = "UPDATE tb_floor SET content = ? WHERE id = ? AND post_id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, content);
            prepStmt.setInt(2, floorID);
            prepStmt.setInt(3, postID);
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }
}
