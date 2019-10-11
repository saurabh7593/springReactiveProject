package com.books.inventory.service

import com.books.inventory.beans.Book
import com.books.inventory.repository.BooksMongoRepository
import com.books.inventory.repository.BooksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BooksServiceImpl(private val booksRepository : BooksMongoRepository):BooksService{
    override fun getAllBooks(): List<Book> {
        return booksRepository.findAll()
    }

    override fun updateTheBook(title: String, book: Book) {
    }

    override fun deleteABook(book: Book) {
        booksRepository.delete(book);
    }

    override fun findABook(params: String?): List<Book> {
        return booksRepository.findAll().filter { book->
            book.title.equals(params!!.toUpperCase())|| book.authors.contains(params!!.toUpperCase());
         }
    }
}