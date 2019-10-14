package com.books.inventory.controller

import com.books.inventory.beans.Book
import com.books.inventory.service.BooksService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@RunWith(SpringJUnit4ClassRunner::class)
class BooksControllerImplTest {
    val booksService = mock(BooksService::class.java)
    val book1= Book(title = "INFERNO",authors = listOf("DAN BROWN"),image = "imageURL",description = "description",price = "234",quantity = "25");
    val book2= Book(title = "FREEDOM IN EXILE",authors = listOf("DALAI LAMA"),image = "imageURL",description = "description",price = "334",quantity = "125");

    val booksController = BooksControllerImpl(booksService)



    @Test
    fun `should return list of books`() {
        Mockito.`when`(booksService.getAllBooks()).thenReturn(Flux.just(book1, book2))

        val expectedBookList = booksController.listOfBooks();

        StepVerifier.create(expectedBookList)
                .expectNext(book1)
                .expectNext(book2)
                .verifyComplete()
    }
}