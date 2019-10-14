package com.books.inventory.service

import com.books.inventory.beans.Book
import com.books.inventory.repository.BooksMongoRepository
import com.books.inventory.repository.BooksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux

@Service
class BooksServiceImpl(private val booksRepository : BooksMongoRepository):BooksService{
    override fun getAllBooks(): Flux<Book>? {
        return Flux.fromIterable(booksRepository.findAll());
    }

    override fun updateTheBook(book: Book): Book {
        if(findABook(book.id)!=null){
            booksRepository.deleteById(book.id);
        }
        return booksRepository.save(book);
    }

    override fun deleteABook(book: String): Mono<Boolean> {
        if (findABook(book)!=null){
            booksRepository.deleteById(book) ;
            return Mono.just(true);
        }else return Mono.just(false);

    }

    override fun findABook(params: String?): Flux<Book> {
        return Flux.fromIterable(booksRepository.findAll().filter { book->
            book.title.equals(params!!.toUpperCase())|| book.authors.contains(params!!.toUpperCase());
         });
    }
}