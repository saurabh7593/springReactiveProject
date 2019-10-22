package com.books.inventory.controller

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBooks
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.ArrayList

interface BooksController {

    @GetMapping("/listAllBooks")
    fun listOfBooks(): Flux<Book>?;

    @PostMapping("/editThisBook")
    fun editABook(@RequestBody book: Book): Mono<Book>?;

    @PostMapping("/deleteThisBook/{bookId}")
    fun deleteABook(@PathVariable(value="bookId") bookId: String): Mono<Void>?;

    @GetMapping("/findThisBook")
    fun findABook(@RequestParam("searchParameter")params:String?): Flux<Book>;

    @GetMapping("/fetchGoogleBooks/{queryParam}")
    fun getBooksFromGoogleApi(@PathVariable ("queryParam") query :String): Flux<GoogleBooks>;

    @PostMapping("/addBook")
    fun addBookToInventory (@RequestBody book:Book):Mono<Book>

    @GetMapping("/auditList")
    fun getAuditReport():ArrayList<String>?;
}