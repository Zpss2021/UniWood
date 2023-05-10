package info.zpss.uniwood.desktop.server.mapper.Impl;

import info.zpss.uniwood.desktop.common.Log;
import info.zpss.uniwood.desktop.server.Main;
import info.zpss.uniwood.desktop.server.mapper.UserMapper;
import info.zpss.uniwood.desktop.server.model.User;
import info.zpss.uniwood.desktop.server.model.UserZone;
import info.zpss.uniwood.desktop.server.model.UserPost;
import info.zpss.uniwood.desktop.server.model.Follow;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {
    private static final UserMapper INSTANCE = new UserMapperImpl();

    private UserMapperImpl() {
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized User getUser(Integer userID) {
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
    public synchronized List<User> getUsers() {
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
    public synchronized String getStatus(Integer userID) {
        String sql = "SELECT status FROM tb_user WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
        return null;
    }

    @Override
    public synchronized List<User> getFollowers(Integer userID) {
        List<User> followers = new ArrayList<>();
        String sql = "SELECT * FROM tb_user WHERE id IN (SELECT follower FROM tb_follow WHERE following = ?)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setUniversity(resultSet.getString("university"));
                user.setStatus(resultSet.getString("status"));
                followers.add(user);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
        return followers;
    }

    @Override
    public synchronized List<User> getFollowings(Integer userID) {
        List<User> followings = new ArrayList<>();
        String sql = "SELECT * FROM tb_user WHERE id IN (SELECT following FROM tb_follow WHERE follower = ?)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userID);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setUniversity(resultSet.getString("university"));
                user.setStatus(resultSet.getString("status"));
                followings.add(user);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
        return followings;
    }

    @Override
    public synchronized void addUser(User user) {
        String sql = "INSERT INTO tb_user(username, password, avatar, university, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, user.getUsername());
            preStmt.setString(2, user.getPassword());
            preStmt.setString(3, user.getAvatar());
            preStmt.setString(4, user.getUniversity());
            preStmt.setString(5, user.getStatus());
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
    }

    @Override
    public synchronized void addUserZone(UserZone userZone) {
        String sql = "INSERT INTO tb_user_zone(user_id, zone_id) VALUES (?, ?)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userZone.getUserId());
            preStmt.setInt(2, userZone.getZoneId());
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
    }

    @Override
    public synchronized void addUserPost(UserPost userPost) {
        String sql = "INSERT INTO tb_user_post(user_id, post_id) VALUES (?, ?)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, userPost.getUserId());
            preStmt.setInt(2, userPost.getPostId());
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
    }

    @Override
    public synchronized void addFollow(Follow follow) {
        String sql = "INSERT INTO tb_follow(follower, following) VALUES (?, ?)";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, follow.getFollowerId());
            preStmt.setInt(2, follow.getFollowingId());
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
    }

    @Override
    public synchronized void updateUser(User user) {
        String sql = "UPDATE tb_user SET username = ?, password = ?, avatar = ?, university = ?, status = ? WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, user.getUsername());
            preStmt.setString(2, user.getPassword());
            preStmt.setString(3, user.getAvatar());
            preStmt.setString(4, user.getUniversity());
            preStmt.setString(5, user.getStatus());
            preStmt.setInt(6, user.getId());
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
    }

    @Override
    public synchronized void updateStatus(Integer userID, String status) {
        String sql = "UPDATE tb_user SET status = ? WHERE id = ?";
        try (Connection conn = Main.database().getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, status);
            preStmt.setInt(2, userID);
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
        }
    }
}
