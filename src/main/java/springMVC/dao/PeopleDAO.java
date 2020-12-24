package springMVC.dao;

import org.springframework.stereotype.Component;
import springMVC.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleDAO {
    private static int ID_PERSON;

    private static final String URL = "jdbc:postgresql://localhost:5432/Spring-MVC-CRUD";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "rfgbnfy";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return people;
    }

    public Person getPerson(int id){
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM Person WHERE id=?");
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    public void savePerson(Person person){
        try {
//            Statement statement = connection.createStatement();
//            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
//                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

//            statement.executeUpdate(SQL);
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person VALUES(1,?,?,?)");
            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(3,person.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updatePerson(int id, Person updatedPerson){
//        Person personToBeUptated = getPerson(id);
//
//        personToBeUptated.setName(updatedPerson.getName());
//        personToBeUptated.setAge(updatedPerson.getAge());
//        personToBeUptated.setEmail(updatedPerson.getEmail());
        try {
        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");

            preparedStatement.setString(1,updatedPerson.getName());
            preparedStatement.setInt(2,updatedPerson.getAge());
            preparedStatement.setString(3,updatedPerson.getEmail());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void deletePerson(int id) {
//        people.removeIf(p -> p.getId() == id);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Person WHERE id=?");
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
