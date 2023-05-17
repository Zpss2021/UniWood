package info.zpss.uniwood.desktop.server.mapper.Impl;

import info.zpss.uniwood.desktop.server.Main;
import info.zpss.uniwood.desktop.server.mapper.PostMapper;
import info.zpss.uniwood.desktop.server.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostMapperImpl implements PostMapper {
    private static final PostMapper INSTANCE = new PostMapperImpl();

    private PostMapperImpl() {
    }

    public static PostMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized Post getPost(Integer postID) {
        Post post = new Post();
        String sql = "SELECT * FROM tb_post WHERE id = ?";
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
    public synchronized List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM tb_post";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
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
    public synchronized List<Post> getPostsByZoneID(Integer zoneID) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM tb_post WHERE zone_id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, zoneID);
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
    public synchronized List<Post> getPostsByUserID(Integer userID) {
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
        String sql = "DELETE FROM tb_post WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, postID);
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }
}
