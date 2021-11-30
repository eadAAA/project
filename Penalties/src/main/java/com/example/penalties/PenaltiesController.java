package com.example.penalties;

import com.example.penalties.models.Books;
import com.example.penalties.models.Users;
import com.example.penalties.repositories.BooksRepository;
import com.example.penalties.repositories.UsersRepository;
import com.example.penalties.tokens.Token;
import com.example.penalties.tokens.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/penalties")
public class PenaltiesController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private TokenManager tokenManager;

    @GetMapping("/{token}/")
    public String getPenalties(@PathVariable("token") String token, Model model) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));

        List<BookWithPenalty> booksList = new ArrayList<>();
        Iterator<Books> booksIterator = booksRepository.findAll().iterator();
        while (booksIterator.hasNext()) {
            Books book = booksIterator.next();
            if (book.getUsedBy() != null) {
                if (book.getUsedBy().equals(id) && book.getEndTime() < new Date().getTime()) {
                    Long oneDayInMs = new Long(1000 * 60 * 60 * 24);
                    Long penalty = ((new Date().getTime() - book.getEndTime()) / oneDayInMs + 1);
                    BookWithPenalty bookWithPenalty = new BookWithPenalty(book.getId(), new Date(book.getEndTime()), penalty, book.getName(), book.getAuthor());
                    booksList.add(bookWithPenalty);
                }
            }
        }

        model.addAttribute("booksList", booksList);

        addModelAttributes(model, token);

        return "penalties";
    }

    @PostMapping("/{token}/paypenalties")
    public String payPenalties(@PathVariable("token") String token, @RequestParam("bookId") Long bookId, @RequestParam("penalty") Long penalty, Model model) {

        if(tokenManager.userIdentify(new Token(token)) == null){
            return "redirect:http://localhost:8080/authorization/";
        }
        long id = new Long(tokenManager.userIdentify(new Token(token)));

        Books book = booksRepository.findById(bookId).get();
        Users user = usersRepository.findById(id).get();
        String toMainURL = "/penalties/" + token + "/";
        if (Double.valueOf(user.getBalance()) < penalty) {
            model.addAttribute("toMainURL", toMainURL);
            model.addAttribute("message", "Недостаточно средств! Пополните баланс");

            addModelAttributes(model, token);

            return "message";
        } else {
            user.setBalance(user.getBalance() - penalty);
            usersRepository.save(user);
            Long oneDayInMs = new Long(1000 * 60 * 60 * 24);
            book.setEndTime(new Date().getTime() + oneDayInMs);
            booksRepository.save(book);
            model.addAttribute("toMainURL", toMainURL);
            model.addAttribute("message", "Штраф оплачен книгу нужно сдать в течение дня!");

            addModelAttributes(model, token);

            return "message";
        }
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
