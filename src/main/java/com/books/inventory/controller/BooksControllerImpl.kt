package com.books.inventory.controller

import com.books.inventory.beans.Book
import com.books.inventory.service.BooksService
import com.books.inventory.service.BooksServiceImpl
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
 class BooksControllerImpl (private val booksService: BooksService) :BooksController {

    override fun listOfBooks(): Flux<Book>? {
        return booksService.getAllBooks();
    }

    override fun editABook(book: Book): Book {
        return booksService.updateTheBook(book);
    }

    override fun deleteABook(book: String) :Boolean {
        return booksService.deleteABook(book);
    }

     override fun findABook(params: String?): List<Book> {
        return booksService.findABook(params);
    }


}