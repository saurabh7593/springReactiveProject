package com.books.inventory.controller

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBooks
import com.books.inventory.service.BooksService
import com.books.inventory.service.BooksServiceImpl
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
 class BooksControllerImpl (private val booksService: BooksService) :BooksController {

    override fun addBookToInventory(book: Book): Mono<Book> {
        return booksService.addBooksToInventory(book)
    }

    override fun listOfBooks() = booksService.getAllBooks()

    override fun editABook(book: Book) = booksService.updateTheBook(book)


    override fun deleteABook(book:String) = booksService.deleteABook(book)

    override fun findABook(params: String?) = booksService.findABook(params)

    override fun getBooksFromGoogleApi(query: String) = booksService.getBooksFromGoogleApi(query)

    override fun getAuditReport() =booksService.getAuditReport();

}