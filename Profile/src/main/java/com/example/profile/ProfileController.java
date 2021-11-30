package com.example.profile;

import com.example.profile.models.Books;
import com.example.profile.models.Users;
import com.example.profile.repositories.BooksRepository;
import com.example.profile.repositories.UsersRepository;
import com.example.profile.tokens.Token;
import com.example.profile.tokens.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TokenManager tokenManager;

    @GetMapping("/{token}/")
    public String profileGet(@PathVariable("token") String token, Model model) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));

        List<Books> booksList = booksUsedBy(id);
        Users users = usersRepository.findById(id).get();
        model.addAttribute("userId", id);
        model.addAttribute("fullName", users.getFullName());
        model.addAttribute("address", users.getAddress());
        model.addAttribute("booksList", booksList);

        addModelAttributes(model, token);

        return "profile";
    }

    @PostMapping("/{token}/returnBook")
    public String returnBook(@PathVariable("token") String token, @RequestParam("bookId") Long bookId) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));

        try {
            Books book = booksRepository.findById(bookId).get();
            book.setUsedBy(null);
            booksRepository.save(book);
        } catch (Exception e) {
        }
        return "redirect:http://localhost:8080/profile/" + token + "/";
    }

    public List<Books> booksUsedBy(Long id) {
        Iterator<Books> booksIterator = booksRepository.findAll().iterator();
        List<Books> booksList = new ArrayList<>();
        while (booksIterator.hasNext()) {
            Books book = booksIterator.next();
            if (book.getUsedBy() != null) {
                if (book.getUsedBy().equals(id)) {
                    booksList.add(book);
                }
            }
        }
        return booksList;
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
