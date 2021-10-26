package com.microservice.category.api.controller;

@RestController
public class HystrixController {

    @Autowired
    CategoryService categoryervice;

    @RequestMapping(value = "/getBookDetails/{book}", method = RequestMethod.GET)
    public String getCategories(@PathVariable String book) {
        return categoryervice.callCategoryService(book);
    }

}
