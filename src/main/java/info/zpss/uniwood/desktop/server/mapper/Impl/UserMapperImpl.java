package info.zpss.uniwood.desktop.server.mapper.Impl;

import info.zpss.uniwood.desktop.common.Log;
import info.zpss.uniwood.desktop.server.Main;
import info.zpss.uniwood.desktop.server.mapper.UserMapper;
import info.zpss.uniwood.desktop.server.model.Post;
import info.zpss.uniwood.desktop.server.model.User;
import info.zpss.uniwood.desktop.server.model.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {
    public UserMapperImpl() {
    }

    @Override
    public User getUser(Integer userID) {
        User user = new User();
        String sql = "SELECT * FROM tb_user WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setUniversity(resultSet.getString("university"));
                user.setStatus(resultSet.getString("status"));
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM tb_user";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setUniversity(resultSet.getString("university"));
                user.setStatus(resultSet.getString("status"));
                users.add(user);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
        return users;
    }

    @Override
    public List<User> getUserFollowers(Integer userID) {
        List<User> followers = new ArrayList<>();
        String sql = "SELECT follower FROM tb_follow WHERE following = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next())
                followers.add(getUser(resultSet.getInt("follower")));
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
        return followers;
    }

    @Override
    public List<User> getUserFollowings(Integer userID) {
        List<User> followings = new ArrayList<>();
        String sql = "SELECT following FROM tb_follow WHERE follower = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next())
                followings.add(getUser(resultSet.getInt("following")));
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
        return followings;
    }

    @Override
    public List<Zone> getUserZones(Integer userID) {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT * FROM tb_zone WHERE id IN " +
                "(SELECT zone_id FROM tb_user_zone WHERE user_id = ?)"; // TODO
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
            Log.add(e, Thread.currentThread());
        }
        return zones;
    }

    @Override
    public List<Post> getUserPosts(Integer userID) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM tb_post WHERE id IN " +
                "(SELECT post_id FROM tb_user_post WHERE user_id = ?)"; // TODO
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
//                post.setZone(resultSet.getInt("zone_id"));
                post.setAuthor(getUser(userID));
//                post.setFloors(resultSet.getString("content"));
//                post.setTime(resultSet.getTimestamp("create_time"));  TODO
                posts.add(post);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
        return posts;
    }
}
