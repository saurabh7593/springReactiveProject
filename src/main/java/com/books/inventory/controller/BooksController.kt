package com.books.inventory.controller

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBooks
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BooksController {

    @GetMapping("/listAllBooks")
    fun listOfBooks(): Flux<Book>?;

    @PostMapping("/editThisBook")
    fun editABook(@RequestBody book: Book): Mono<Book>;

    @GetMapping("/deleteThisBook/{bookId}")
    fun deleteABook(@PathVariable (value = "bookId")book: String): Mono<Boolean>;

    @GetMapping("/findThisBook/{searchParameter}")
    fun findABook(@PathVariable("searchParameter")params:String?): Flux<Book>;

    @GetMapping("/fetchGoogleBooks/{queryParam}")
    fun getBooksFromGoogleApi(@PathVariable ("queryParam") query :String): Flux<GoogleBooks>;

    @GetMapping("/addBook")
    fun addBookToInventory (@RequestBody book:Book):Mono<Book>
}