package com.example.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.example.bookstore.exception.BookCollectionException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.Bookrepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class Bookserviceimpl implements Bookservice {
	
	@Autowired
	private Bookrepository bookrepo;
	
	
@Override
public void creatBook(Book book) throws ConstraintViolationException,BookCollectionException {
	Optional<Book> BookOptional=bookrepo.findById(book.getBookname());
	if(BookOptional.isPresent()) {
		throw new BookCollectionException(BookCollectionException.BookAlreadyExists());
	}
	else {
		bookrepo.save(book);
	}
}


@Override
public List<Book> getBookByPrice(String price) {
	return bookrepo.findByPrice(price);
}


@Override
public List<Book> getBookByAuthorname(String authorname) {
	return bookrepo.findByAuthorname(authorname);
}



}
