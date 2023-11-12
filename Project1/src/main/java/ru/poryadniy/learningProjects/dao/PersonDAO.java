package ru.poryadniy.learningProjects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.poryadniy.learningProjects.models.Book;
import ru.poryadniy.learningProjects.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index (){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person){
    jdbcTemplate.update("INSERT INTO person(full_name, date_of_birth) VALUES (?,?)",
            person.getFullName(),
            person.getDateOfBirth());
    }

    public Person show (int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void update(Person updatedPerson, int id){
        jdbcTemplate.update("UPDATE person set full_name=?, date_of_birth=? WHERE id=?",
                updatedPerson.getFullName(), updatedPerson.getDateOfBirth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> showBooks (int id){
        return jdbcTemplate.query("SELECT person.full_name, book.name from book left join person on " +
                "person.id = book.person_id WHERE person.id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
