package com.books.inventory.controller

import com.books.inventory.beans.Book
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

interface BooksController {

    @GetMapping("/listAllBooks")
    fun listOfBooks(): List<Book>;

    @GetMapping("/editThisBook")
    fun editABook(book: Book);

    @GetMapping("/deleteThisBook")
    fun deleteABook(book: Book);

    @GetMapping("/findThisBook/{searchParameter}")
    fun findABook(@PathVariable("searchParameter")params:String?): List<Book>;
}