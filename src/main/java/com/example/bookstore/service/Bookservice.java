package com.example.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.example.bookstore.exception.BookCollectionException;
import com.example.bookstore.model.Book;

import jakarta.validation.ConstraintViolationException;

public interface Bookservice {
	
	public void creatBook(Book book) throws ConstraintViolationException,BookCollectionException;
	
public List<Book> getBookByPrice(String price);
public List<Book> getBookByAuthorname(String authorname);
}
