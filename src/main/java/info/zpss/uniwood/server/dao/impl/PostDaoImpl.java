package info.zpss.uniwood.server.dao.impl;

import info.zpss.uniwood.server.Main;
import info.zpss.uniwood.server.dao.PostDao;
import info.zpss.uniwood.server.entity.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDaoImpl implements PostDao {
    private static final PostDao INSTANCE = new PostDaoImpl();

    private PostDaoImpl() {
    }

    public static PostDao getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized Post getPost(Integer postID) {
        Post post = new Post();
        String sql = "SELECT * FROM tb_post WHERE id = ? LIMIT 1";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, postID);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                post.setId(resultSet.getInt("id"));
                post.setZoneId(resultSet.getInt("zone_id"));
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return post;
    }

    @Override
    public List<Post> getPostsByUserID(Integer userID) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM tb_post WHERE id IN (SELECT post_id FROM tb_floor WHERE author_id = ? AND id = 1)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setZoneId(resultSet.getInt("zone_id"));
                posts.add(post);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return posts;
    }

    @Override
    public synchronized List<Post> getFavorsByUserID(Integer userID) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM tb_post WHERE id IN (SELECT post_id FROM tb_user_post WHERE user_id = ?)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setZoneId(resultSet.getInt("zone_id"));
                posts.add(post);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return posts;
    }

    @Override
    public synchronized Integer addPost(Integer zoneID) {
        String sql_add = "INSERT INTO tb_post (zone_id) VALUES (?)";
        String sql_get = "SELECT MAX(id) FROM tb_post";
        Integer id = null;
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmtAdd = conn.prepareStatement(sql_add);
            preStmtAdd.setInt(1, zoneID);
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
    public synchronized void deletePost(Integer postID) {
        String sql_1 = "DELETE FROM tb_floor WHERE post_id = ?";
        String sql_2 = "DELETE FROM tb_user_post WHERE post_id = ?";
        String sql_3 = "DELETE FROM tb_post WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt1 = conn.prepareStatement(sql_1);
            preStmt1.setInt(1, postID);
            preStmt1.executeUpdate();
            preStmt1.close();
            PreparedStatement preStmt2 = conn.prepareStatement(sql_2);
            preStmt2.setInt(1, postID);
            preStmt2.executeUpdate();
            preStmt2.close();
            PreparedStatement preStmt3 = conn.prepareStatement(sql_3);
            preStmt3.setInt(1, postID);
            preStmt3.executeUpdate();
            preStmt3.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }

    @Override
    public Integer getFloorCount(Integer postID) {
        Integer count = null;
        String sql = "SELECT COUNT(*) FROM tb_floor WHERE post_id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, postID);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next())
                count = resultSet.getInt(1);
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return count;
    }

    @Override
    public List<Post> getLimitPostsByZoneID(Integer zoneID, Integer from, Integer pageSize) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM tb_post WHERE zone_id = ? " +
                "ORDER BY FROM_UNIXTIME(UNIX_TIMESTAMP(create_time)) DESC LIMIT ?, ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, zoneID);
            preStmt.setInt(2, from);
            preStmt.setInt(3, pageSize);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setZoneId(resultSet.getInt("zone_id"));
                posts.add(post);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        return posts;
    }
}
