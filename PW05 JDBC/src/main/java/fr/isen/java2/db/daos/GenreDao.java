package fr.isen.java2.db.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

import fr.isen.java2.db.entities.Genre;

public class GenreDao {

	private String sqliteURL = "jdbc:sqlite:sqlite.db";
	public List<Genre> listGenres() {
		List <Genre> genres = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(sqliteURL)){
			String sql = "SELECT * FROM genre";
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				ResultSet resultSet = statement.executeQuery();{
					while (resultSet.next()) {
						int id = resultSet.getInt("idgenre");
						String name = resultSet.getString("name");
						Genre genre = new Genre(id, name);
						genres.add(genre);
					}
				}
			}
			return genres;

		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



	public Genre getGenre(String name) {
		Genre genre = null;
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			String sql = "SELECT * FROM genre WHERE name=?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, name);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						int id = resultSet.getInt("idgenre");
						genre = new Genre(id, name);
					}
				}

			}
			return genre;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


public Genre getGenreById(Integer id) {
	try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE idgenre=?")) {
			statement.setInt(1, id);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					Genre genre = new Genre(
							result.getInt("idgenre"),
							result.getString("name")
					);
					return genre;
				}
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
}

	public void addGenre(String name) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			String sqlQuery = "INSERT INTO genre(name) " + "VALUES(?)";
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
				statement.setString(1, name);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}




