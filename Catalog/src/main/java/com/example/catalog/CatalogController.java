package com.example.catalog;

import com.example.catalog.models.Books;
import com.example.catalog.models.Users;
import com.example.catalog.repositories.BooksRepository;
import com.example.catalog.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/{id}/")
    public String showCatalog(@PathVariable("id") Long userId, Model model) {
        Iterator<Books> booksIterator = booksRepository.findAll().iterator();
        List<Books> booksList = new ArrayList<>();
        while (booksIterator.hasNext()) {
            Books book = booksIterator.next();
            if (book.getUsedBy() == null) {
                booksList.add(book);
            }
        }
        model.addAttribute("booksList", booksList);
        Users user = usersRepository.findById(userId).get();

        addModelAttributes(model, userId);

        if (user.getAdminRoot() != null) {
            if (user.getAdminRoot()){
                return "catalogAdmin";
            }
        }
        return "catalog";
    }

    @PostMapping("/{id}/takebook")
    public String takeBook(@PathVariable("id") Long userId, @RequestParam("bookId") Long bookId) {
        Users user = usersRepository.findById(userId).get();
        Books book = booksRepository.findById(bookId).get();

        Date dt = new Date();
        book.setTakenTime(dt.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 30);
        book.setEndTime(c.getTime().getTime());

        book.setUsedBy(userId);
        booksRepository.save(book);
        /*
        if(user.getSubscriber() != null){
            if(user.getSubscriber()){
                book.setUsedBy(userId);
                booksRepository.save(book);
            }
        }
        */


        return "redirect:/catalog/" + userId + "/";
    }

    @PostMapping("/{id}/editbook")
    public String editBook(@PathVariable("id") Long userId, @RequestParam("bookId") Long bookId, @RequestParam("name") String name, @RequestParam("author") String author) {

        //нужна проверка личности админа

        Users user = usersRepository.findById(userId).get();
        Books book = booksRepository.findById(bookId).get();

        book.setName(name);
        book.setAuthor(author);

        booksRepository.save(book);

        return "redirect:/catalog/" + userId + "/";
    }

    @PostMapping("/{id}/deletebook")
    public String deleteBook(@PathVariable("id") Long userId, @RequestParam("bookId") Long bookId) {

        //нужна проверка личности админа

        Users user = usersRepository.findById(userId).get();

        booksRepository.deleteById(bookId);

        return "redirect:/catalog/" + userId + "/";
    }

    @PostMapping("/{id}/addbook")
    public String editBook(@PathVariable("id") Long userId, @RequestParam("name") String name, @RequestParam("author") String author) {

        //нужна проверка личности админа

        Users user = usersRepository.findById(userId).get();
        Books book = new Books(name, author);

        booksRepository.save(book);

        return "redirect:/catalog/" + userId + "/";
    }

    private Model addModelAttributes(Model model, Long userId){
        model.addAttribute("toAuthorisation", "http://localhost:8081/authorisation/");
        model.addAttribute("toProfile", "http://localhost:8082/profile/" + userId + "/");
        model.addAttribute("toCatalog", "http://localhost:8083/catalog/" + userId + "/");
        model.addAttribute("toSubscription", "http://localhost:8084/subscription/" + userId + "/");
        model.addAttribute("toPay", "http://localhost:8085/pay/" + userId + "/");
        model.addAttribute("toPenalties", "http://localhost:8086/penalties/" + userId + "/");
        return model;
    }
}
