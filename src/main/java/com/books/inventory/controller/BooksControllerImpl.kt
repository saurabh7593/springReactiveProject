package com.books.inventory.controller

import com.books.inventory.beans.Book
import com.books.inventory.service.BooksService
import com.books.inventory.service.BooksServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
 class BooksControllerImpl (private val booksService: BooksService) :BooksController {

    override fun listOfBooks(): List<Book> {
        return booksService.getAllBooks();
    }

    override fun editABook(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteABook(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

     override fun findABook(params: String?): List<Book> {
        return booksService.findABook(params);
    }


}