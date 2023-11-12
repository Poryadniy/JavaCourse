package ru.poryadniy.learningProjects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.poryadniy.learningProjects.dao.BookDAO;
import ru.poryadniy.learningProjects.dao.PersonDAO;

@Controller
public class TestController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public TestController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }


    @GetMapping("/index/{id}")
    public String index(@PathVariable("id") int id, Model model){
        model.addAttribute("person",bookDAO.checkOwner(id));

        return "test";
    }
}
