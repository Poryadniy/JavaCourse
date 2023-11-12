package ru.poryadniy.learningProjects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.poryadniy.learningProjects.dao.BookDAO;
import ru.poryadniy.learningProjects.dao.PersonDAO;
import ru.poryadniy.learningProjects.models.Book;
import ru.poryadniy.learningProjects.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("books",bookDAO.index());

        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book){
        bookDAO.save(book);
        return "redirect:/books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("owner")Person person){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("person", bookDAO.checkOwner(id));
        model.addAttribute("people", personDAO.index());

        return "books/show";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Book book, @PathVariable("id") int id){
        bookDAO.update(book, id);

        return "redirect:/books/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books/index";
    }

    @PatchMapping("/release/{id}")
    public String release(@PathVariable("id") int id){
        bookDAO.releaseBook(id);
        return "redirect:/books/index";
    }

    @PatchMapping("/appoint/{id}")
    public String appoint(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookDAO.appoint(person.getId(), id);

        return "redirect:/books/index";
    }
}
