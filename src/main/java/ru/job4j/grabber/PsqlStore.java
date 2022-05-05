package ru.job4j.grabber;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
        String sql = "INSERT INTO post(name, link, description, created) values(?, ?, ?, ?);";
        try (PreparedStatement statement = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getLink());
            statement.setString(3, post.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        String sql = "SELECT * FROM post;";
        List<Post> postList = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement(sql)) {
            try (ResultSet rslSet = statement.executeQuery()) {
                while (rslSet.next()) {
                    postList.add(getPost(rslSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        String sql = "SELECT * FROM post WHERE id = ?;";
        try (PreparedStatement statement = cnn.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rslSet = statement.executeQuery()) {
                while (rslSet.next()) {
                    post = getPost(rslSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private Post getPost(ResultSet rslSet) throws Exception {
        return new Post(
                rslSet.getInt("id"),
                rslSet.getString("name"),
                rslSet.getString("link"),
                rslSet.getString("description"),
                rslSet.getTimestamp("created").toLocalDateTime()
        );
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        Properties config = new Properties();
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            config.load(in);
            try (PsqlStore store = new PsqlStore(config)) {
                store.save(new Post("Java developer(Middle)",
                        "https://career.habr.com/vacancies/1000097887",
                        "Описание вакансии", LocalDateTime.now()));
                store.save(new Post("Java developer(Junior)",
                        "https://career.habr.com/vacancies/1099097887",
                        "Описание вакансии", LocalDateTime.now()));
                System.out.println("Вывести все вакансии");
                store.getAll().forEach(System.out::println);
                System.out.println("Найти вакансию с id = 2");
                System.out.println(store.findById(2));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
