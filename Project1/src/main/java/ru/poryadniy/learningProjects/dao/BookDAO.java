package ru.poryadniy.learningProjects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.poryadniy.learningProjects.models.Book;
import ru.poryadniy.learningProjects.models.Person;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index (){
        return jdbcTemplate.query("SELECT id,name,author,year FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(person_id, name, author, year) VALUES (?,?,?,?)",
                null,
                book.getName(),
                book.getAuthor(),
                book.getYear());
    }

    public Book show (int id){
        return jdbcTemplate.query("SELECT id,name,author,year FROM book WHERE id = ?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void update(Book updatedBook, int id){
        jdbcTemplate.update("UPDATE book set name=?, author=?, year=? WHERE id=?",
                updatedBook.getName(), updatedBook.getAuthor(),updatedBook.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public Person checkOwner(int id){
        return jdbcTemplate.query("SELECT person.full_name from person join book on " +
                "person.id = book.person_id where book.id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void releaseBook(int id){
        jdbcTemplate.update("UPDATE book SET person_id = null WHERE id = ?", id);
    }

    public void appoint(int personId, int bookId){
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE id = ?", personId, bookId);
    }
}
