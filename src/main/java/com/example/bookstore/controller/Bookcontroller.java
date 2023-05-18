package com.example.bookstore.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.bookstore.SortPrice;
import com.example.bookstore.exception.BookCollectionException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.Bookrepository;
import com.example.bookstore.service.Bookservice;

import jakarta.validation.ConstraintViolationException;

@RestController
public class Bookcontroller {
	@Autowired
	private Bookrepository bookrepo;
	
	@Autowired
	private Bookservice bookservice;
	
	public Bookrepository getRepository() {
		return bookrepo;
	}
	
	

	@GetMapping("/Book")
	public ResponseEntity<?> getAllBook(){
		//this function check the list if it contains anything if yes so we have books so it reply with an Http
		//message saying Ok else it will be empty so it reply with No books avialabe and http message Not found
	List<Book> Book=bookrepo.findAll();

	if(Book.size()>0) { //checking if the list contain something
		return new ResponseEntity<List<Book>>(Book,HttpStatus.OK);
	}
	else {
		return new ResponseEntity<>("No Books avialable", HttpStatus.NOT_FOUND);
	}

	}
	
	

@PostMapping("/Book")
public ResponseEntity<?> createBook(@RequestBody Book book){
	
	try {
   bookservice.creatBook(book);
	return new ResponseEntity<Book>(book,HttpStatus.OK);
		
	}
	catch(ConstraintViolationException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	catch(BookCollectionException e) {
		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
	}
	
		
	}

@GetMapping("/Book/{id}")
public ResponseEntity<?> findbookbyid(@PathVariable("id") String id){
	
	Optional<Book> bookoptional=bookrepo.findById(id);
	
	if(bookoptional.isPresent()) {
		return new ResponseEntity<>(bookoptional.get(),HttpStatus.OK);
	}
	else {
		return new ResponseEntity<>("Book with id: "+id+" is not found",HttpStatus.NOT_FOUND);
	}
	
	
}


@PutMapping("/Book/{id}")
public ResponseEntity<?> updatebookbyid(@PathVariable("id") String id,@RequestBody Book books){
	
	Optional<Book> bookoptional=bookrepo.findById(id);
	
	if(bookoptional.isPresent()) {
		Book booksave=bookoptional.get(); //if the id is found we save all the informations of its using .get() and then save them in an object of type Book(class Book)
		booksave.setAuthorname(books.getAuthorname()!=null ? books.getAuthorname(): booksave.getAuthorname());
		booksave.setDescription(books.getDescription()!=null ? books.getDescription(): booksave.getDescription());
		booksave.setAvailableinstorenow(books.getAvailableinstorenow()!=null ? books.getAvailableinstorenow(): booksave.getAvailableinstorenow());
		booksave.setDateofpublication(books.getDateofpublication()!=null ? books.getDateofpublication(): booksave.getDateofpublication());
		booksave.setPrice(books.getPrice()!=null ? books.getPrice(): booksave.getPrice());
		bookrepo.save(booksave);
		return new ResponseEntity<>(booksave,HttpStatus.OK);
	}
	else {
		return new ResponseEntity<>("Book with id: "+id+" :is not found",HttpStatus.NOT_FOUND);
	}
	
	
}

@DeleteMapping("/Book/{id}")
 public ResponseEntity<?> deletebookbyid(@PathVariable("id") String id){
	try {
		Optional<Book> bookoptional=bookrepo.findById(id);
		if(bookoptional.isPresent()) {
		bookrepo.deleteById(id);
		return new ResponseEntity("Book with id: "+id+" :is successfully deleted",HttpStatus.OK);
		}
		else {
			return new ResponseEntity("Book with id: "+id+" :is not found",HttpStatus.NOT_FOUND);
		}
	}
	
	catch(Exception e) {
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
}

//@RequestParam Integer pageNumber,@RequestParam Integer pageSize
@GetMapping("/Bookp")
public ResponseEntity<?> getAllBookpaginated(Pageable p){
//p= PageRequest.of(p.getPageNumber(), p.getPageSize(), Direction.DESC);
Page<Book> Book=bookrepo.findAll(p);
return new ResponseEntity<Page<Book>>(Book,HttpStatus.OK);


}

@GetMapping("/Books/filterByPrice")
public ResponseEntity<?> getBookByPrice(@RequestParam String price){
	return new ResponseEntity<List<Book>>(bookservice.getBookByPrice(price),HttpStatus.OK);
}

@GetMapping("/Bookss/filterByAuthorname")
public ResponseEntity<List<Book>> getBookByAuthorname(@RequestParam String authorname){
	return new ResponseEntity<List<Book>>(bookservice.getBookByAuthorname(authorname),HttpStatus.OK);
}

@PostMapping("/Booksort/SortbyPriceDSEC")
public ResponseEntity<?> SortbookbyPriceDESC(){
	List<Book> Bookss=bookrepo.findAll();
	Collections.sort(Bookss, Collections.reverseOrder(new SortPrice()));
	bookrepo.deleteAll();
	bookrepo.saveAll(Bookss);
	
	return new ResponseEntity<>(Bookss,HttpStatus.OK);
}

@PostMapping("/Booksort/SortbyPriceASC")
public ResponseEntity<?> SortbookbyPriceASC(){
	List<Book> Bookss=bookrepo.findAll();
	Collections.sort(Bookss,new SortPrice());
	bookrepo.deleteAll();
	bookrepo.saveAll(Bookss);
	
	return new ResponseEntity<>(Bookss,HttpStatus.OK);
}

}
