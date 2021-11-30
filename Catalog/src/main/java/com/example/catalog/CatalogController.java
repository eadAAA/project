package com.example.catalog;

import com.example.catalog.models.Books;
import com.example.catalog.models.Users;
import com.example.catalog.repositories.BooksRepository;
import com.example.catalog.repositories.UsersRepository;
import com.example.catalog.tokens.Token;
import com.example.catalog.tokens.TokenManager;
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

    @Autowired
    private TokenManager tokenManager;

    @GetMapping("/{token}/")
    public String showCatalog(@PathVariable("token") String token, Model model) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));

        Iterator<Books> booksIterator = booksRepository.findAll().iterator();
        List<Books> booksList = new ArrayList<>();
        while (booksIterator.hasNext()) {
            Books book = booksIterator.next();
            if (book.getUsedBy() == null) {
                booksList.add(book);
            }
        }
        model.addAttribute("booksList", booksList);
        Users user = usersRepository.findById(id).get();

        addModelAttributes(model, token);

        if (user.getAdminRoot() != null) {
            if (user.getAdminRoot()){
                return "catalogAdmin";
            }
        }
        return "catalog";
    }

    @PostMapping("/{token}/takebook")
    public String takeBook(@PathVariable("token") String token, @RequestParam("bookId") Long bookId) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));

        Users user = usersRepository.findById(id).get();
        Books book = booksRepository.findById(bookId).get();

        Date dt = new Date();
        book.setTakenTime(dt.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 30);
        book.setEndTime(c.getTime().getTime());

        book.setUsedBy(id);
        booksRepository.save(book);
        /*
        if(user.getSubscriber() != null){
            if(user.getSubscriber()){
                book.setUsedBy(userId);
                booksRepository.save(book);
            }
        }
        */


        return "redirect:http://localhost:8080/catalog/" + token + "/";
    }

    @PostMapping("/{token}/editbook")
    public String editBook(@PathVariable("token") String token, @RequestParam("bookId") Long bookId, @RequestParam("name") String name, @RequestParam("author") String author) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));


        Users user = usersRepository.findById(id).get();
        Books book = booksRepository.findById(bookId).get();

        book.setName(name);
        book.setAuthor(author);

        booksRepository.save(book);

        return "redirect:http://localhost:8080/catalog/" + token + "/";
    }

    @PostMapping("/{token}/deletebook")
    public String deleteBook(@PathVariable("token") String token, @RequestParam("bookId") Long bookId) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));

        //нужна проверка личности админа

        Users user = usersRepository.findById(id).get();

        booksRepository.deleteById(bookId);

        return "redirect:http://localhost:8080/catalog/" + token + "/";
    }

    @PostMapping("/{token}/addbook")
    public String editBook(@PathVariable("token") String token, @RequestParam("name") String name, @RequestParam("author") String author) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));

        Users user = usersRepository.findById(id).get();
        Books book = new Books(name, author);

        booksRepository.save(book);

        return "redirect:http://localhost:8080/catalog/" + token + "/";
    }

    private Model addModelAttributes(Model model, String token){
        model.addAttribute("toAuthorisation", "http://localhost:8080/authorization/");
        model.addAttribute("toProfile", "http://localhost:8080/profile/" + token + "/");
        model.addAttribute("toCatalog", "http://localhost:8080/catalog/" + token + "/");
        model.addAttribute("toSubscription", "http://localhost:8080/subscription/" + token + "/");
        model.addAttribute("toPay", "http://localhost:8080/pay/" + token + "/");
        model.addAttribute("toPenalties", "http://localhost:8080/penalties/" + token + "/");
        return model;
    }
}
