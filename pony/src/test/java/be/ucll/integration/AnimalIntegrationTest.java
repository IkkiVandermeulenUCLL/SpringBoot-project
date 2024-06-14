package be.ucll.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.repository.DbInitializer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")
public class AnimalIntegrationTest {
    @Autowired
    private WebTestClient client;

    @Autowired
    private DbInitializer dbInitializer;
    
    @BeforeEach
    public void cleanUp(){
        dbInitializer.initialize();
    }

    @Test
    public void givenStartAnimals_whenGettingAllAnimals_thenAnimalsAreReturned(){
        String link = "/animals";
        String expected = """
                [
                    {
                        "id": 1,
                        "name": "Bella",
                        "age": 20
                    },
                    {
                        "id": 2,
                        "name": "Luna",
                        "age": 10
                    },
                    {
                        "id": 3,
                        "name": "Muriel",
                        "age": 2
                    },
                    {
                        "id": 4,
                        "name": "Little",
                        "age": 1
                    }
                    ]
                """;

        client
            .get()
            .uri(link)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody()
            .json(expected);
    }

    @Test
    public void givenStartAnimals_whenGettingOldestAnimal_thenBellaIsReturned(){
        String link = "/animals/oldest";
        String expected = """
            {
                "id": 1,
                "name": "Bella",
                "age": 20
            }
                """;
        client
        .get()
        .uri(link)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .json(expected);
    }

    @Test
    public void givenStartAnimals_whenGettingAnimalsAboveAge5_thenRightAnimalsAreReturned(){
        String link = String.format("/animals/age/%2d", 5);
        String expected = """
                [
                    {
                        "id": 1,
                        "name": "Bella",
                        "age": 20
                    },
                    {
                        "id": 2,
                        "name": "Luna",
                        "age": 10
                    }
                ]
                """;
        client
        .get()
        .uri(link)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .json(expected);
    }
}
