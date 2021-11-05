package com.example.penalties;

import com.example.penalties.models.Books;
import com.example.penalties.models.Users;
import com.example.penalties.repositories.BooksRepository;
import com.example.penalties.repositories.UsersRepository;
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

    @GetMapping("/{id}/")
    public String getPenalties(@PathVariable("id") Long userId, Model model) {
        List<BookWithPenalty> booksList = new ArrayList<>();
        Iterator<Books> booksIterator = booksRepository.findAll().iterator();
        while (booksIterator.hasNext()) {
            Books book = booksIterator.next();
            if (book.getUsedBy() != null) {
                if (book.getUsedBy().equals(userId) && book.getEndTime() < new Date().getTime()) {
                    Long oneDayInMs = new Long(1000 * 60 * 60 * 24);
                    Long penalty = ((new Date().getTime() - book.getEndTime()) / oneDayInMs + 1);
                    BookWithPenalty bookWithPenalty = new BookWithPenalty(book.getId(), new Date(book.getEndTime()), penalty, book.getName(), book.getAuthor());
                    booksList.add(bookWithPenalty);
                }
            }
        }

        model.addAttribute("booksList", booksList);

        addModelAttributes(model, userId);

        return "penalties";
    }

    @PostMapping("/{id}/paypenalties")
    public String payPenalties(@PathVariable("id") Long userId, @RequestParam("bookId") Long bookId, @RequestParam("penalty") Long penalty, Model model) {
        Books book = booksRepository.findById(bookId).get();
        Users user = usersRepository.findById(userId).get();
        String toMainURL = "/penalties/" + userId + "/";
        if (Double.valueOf(user.getBalance()) < penalty) {
            model.addAttribute("toMainURL", toMainURL);
            model.addAttribute("message", "Недостаточно средств! Пополните баланс");

            addModelAttributes(model, userId);

            return "message";
        } else {
            user.setBalance(user.getBalance() - penalty);
            usersRepository.save(user);
            Long oneDayInMs = new Long(1000 * 60 * 60 * 24);
            book.setEndTime(new Date().getTime() + oneDayInMs);
            booksRepository.save(book);
            model.addAttribute("toMainURL", toMainURL);
            model.addAttribute("message", "Штраф оплачен книгу нужно сдать в течение дня!");

            addModelAttributes(model, userId);

            return "message";
        }
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
