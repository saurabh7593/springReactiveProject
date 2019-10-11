package com.books.inventory.repository

import com.books.inventory.beans.Book
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BooksMongoRepository : MongoRepository<Book,String> {

}