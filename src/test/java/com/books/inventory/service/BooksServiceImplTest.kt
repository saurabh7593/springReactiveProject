package com.books.inventory.service

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBooks
import com.books.inventory.beans.ImageLinks
import com.books.inventory.repository.BooksMongoRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.test.StepVerifier


@AutoConfigureWebTestClient
@RunWith(SpringRunner::class)
@SpringBootTest
open class BooksServiceImplTest {
     @Autowired
     private lateinit var webTestClient: WebTestClient
     @Autowired
     private lateinit var mongoRepository: BooksMongoRepository

    @Before
    fun datasetUp() {
        println("Before Each Test")
        Flux.just(
                Book(title = "INFERNO", authors = listOf("DAN BROWN"), imageLinks = ImageLinks("imageURL", "inameURL"), description = "description", price = "234", quantity = "25"),
                Book(title = "FREEDOM IN EXILE", authors = listOf("DALAI LAMA"), imageLinks = ImageLinks("imageURL", null), description = "description", price = "334", quantity = "125"),
                Book(title = "ColdPeace", authors = listOf("Ruskin Bond"), imageLinks = ImageLinks("imageURL", null), description = "description", price = "434", quantity = "125"))
                .flatMap { mongoRepository.save(it) }
                .thenMany(mongoRepository.findAll())
                .subscribe { println(it) }
    }

    @Test
    fun `should return list of books`() {
        webTestClient.get().uri("/listAllBooks")
                .exchange()
                .expectBodyList(Book::class.java)
                .consumeWith<WebTestClient.ListBodySpec<Book>> { list ->
                    assertEquals(3, list.responseBody!!.size)
                }
    }

    @Test
    fun `should return list of Google books available`() {
        webTestClient.get().uri("/fetchGoogleBooks/someQuery")
                .exchange()
                .expectBodyList(GoogleBooks::class.java)
                .consumeWith<WebTestClient.ListBodySpec<GoogleBooks>> { list ->
                assertTrue(list.responseBody!=null)                }
    }

    @Test
    fun `should delete book with given id`() {
        webTestClient.post().uri("/deleteThisBook/bookId")
                .exchange()
        val listOfBooks = mongoRepository.findById("bookId")
        StepVerifier.create(listOfBooks)
                .verifyComplete()
    }

    @Test
    fun `should return book with given title or author`() {
        webTestClient.get().uri("/findThisBook?searchParameter=INFERNO")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(Book::class.java)
                .consumeWith<WebTestClient.ListBodySpec<Book>> { list ->
                    assertEquals(1, list.responseBody!!.size)
                    assertEquals("234", list.responseBody!!.first().price)
                    assertEquals("25", list.responseBody!!.first().quantity)
                    assertEquals("INFERNO", list.responseBody!!.first().title.toString())

                }
    }

    @Test
    fun `should edit book with new details`() {
        val book1= Book(title = "INFERNO",authors = listOf("DAN BROWN"), imageLinks = ImageLinks("imageURL", "inameURL"),description = "description",price = "234",quantity = "25")
        webTestClient.post().uri("/editThisBook")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(book1))
                .exchange()
                .expectStatus().isOk
    }

    @Test
    fun `should get audit report`() {
        webTestClient.get().uri("/auditList")
                .exchange()
                .expectStatus().isOk
    }

    @Test
    fun `should add book to inventory from google books`() {
        val book1= Book(title = "INFERNO",authors = listOf("DAN BROWN"), imageLinks = ImageLinks("imageURL", "inameURL"),description = "description",price = "234",quantity = "25")
        webTestClient.post().uri("/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(book1))
                .exchange()
                .expectStatus().isOk
    }

    @After
    fun dataTearDown(){
        mongoRepository.deleteAll().subscribe()
        println("After Each Test")

    }
}