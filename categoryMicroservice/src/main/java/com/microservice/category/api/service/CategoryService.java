package com.microservice.category.api.service;

@Service
public class CategoryService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "callCategoryService_Fallback")
    public String callCategoryService(String book) {
        String response = restTemplate
                .exchange("http://localhost:8077/getCategoriesFromBook/{book}"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<String>() {
                        }, book).getBody();

        return "It's OK: book: " + book + " categories " + response + new Date();
    }

    @SuppressWarnings("unused")
    private String callCategoryService_Fallback(String book) {
        return "Error! " + new Date();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
