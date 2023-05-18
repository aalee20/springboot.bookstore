package com.example.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.model.Book;

@Repository
public interface Bookrepository extends MongoRepository<Book, String>{
	
List<Book> findByPrice(String price);
List<Book> findByAuthorname(String authorname);
}
