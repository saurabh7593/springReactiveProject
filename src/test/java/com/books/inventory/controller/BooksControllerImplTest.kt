package com.books.inventory.controller

import com.books.inventory.beans.Book
import com.books.inventory.beans.ImageLinks
import com.books.inventory.service.BooksService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@RunWith(SpringJUnit4ClassRunner::class)
class BooksControllerImplTest {
    val booksService = mock(BooksService::class.java)
    val book1= Book(title = "INFERNO",authors = listOf("DAN BROWN"), imageLinks = ImageLinks("imageURL", "inameURL"),description = "description",price = "234",quantity = "25");
    val book2= Book(title = "FREEDOM IN EXILE",authors = listOf("DALAI LAMA"),imageLinks = ImageLinks("imageURL",null),description = "description",price = "334",quantity = "125");

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

    @Test
    fun `should be able to find a book`() {
        Mockito.`when`(booksService.findABook(ArgumentMatchers.anyString())).thenReturn(Flux.just(book1))

        val expectedBook= booksController.findABook(ArgumentMatchers.anyString());

        StepVerifier.create(expectedBook)
                .expectNext(book1)
                .verifyComplete()
    }

    @Test
    fun `should be able to delete a book`() {
        Mockito.`when`(booksService.deleteABook(ArgumentMatchers.anyString())).thenReturn(Mono.just(true));

        val expectedResult= booksController.deleteABook(ArgumentMatchers.anyString());

        StepVerifier.create(expectedResult)
                .expectNext(true)
                .verifyComplete()
    }

    @Test
    fun `should be able to edit a book`() {
        Mockito.`when`(booksService.updateTheBook(book1)).thenReturn(Mono.just(book1));

        val updatedBook= booksController.editABook(book1);

        StepVerifier.create(updatedBook)
                .expectNext(book1)
                .verifyComplete()
    }

    @Test
    fun `should be able to add a book`() {
        Mockito.`when`(booksService.addBooksToInventory(book1)).thenReturn(Mono.just(book1));

        val addedBook= booksController.addBookToInventory(book1);

        StepVerifier.create(addedBook)
                .expectNext(book1)
                .verifyComplete()
    }
}