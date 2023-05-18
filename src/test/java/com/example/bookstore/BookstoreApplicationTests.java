package com.example.bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Order;

import com.example.bookstore.repository.Bookrepository;
import com.example.bookstore.model.Book;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookstoreApplicationTests {
@Autowired
	Bookrepository bookrepo2;


	@Test
	@Order(1)
	public void testCreate() {
		Book bo= new Book();
		bo.setBookname("Becoming");
		bo.setAuthorname("michelle obama");
		bo.setDescription("good");
		String dateofpub= "2022-11-21";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
			date = dateFormat.parse(dateofpub);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		bo.setDateofpublication(date);
		Boolean p= true;
		bo.setAvailableinstorenow(p);
		bo.setPrice("100");
		
		bookrepo2.save(bo);
		
		assertNotNull(bookrepo2.findById("Becoming").get());
		
		
	}
	
	@Test
	@Order(2)
	public void testReadAll() {
	List<Book> listb=	bookrepo2.findAll();
	
	assertThat(listb).size().isGreaterThan(0);
	
	}
	
	@Test
	@Order(3)
	public void testSingleProduct() {
		Book bk=bookrepo2.findById("Becoming").get();
		
		assertEquals("100",bk.getPrice());
		
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		Book bok= bookrepo2.findById("Becoming").get();
		bok.setPrice("180");
		bookrepo2.save(bok);
		
		assertNotEquals("100",bookrepo2.findById("Becoming").get().getPrice());
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		bookrepo2.deleteById("Becoming");
		assertThat(bookrepo2.existsById("Becoming")).isFalse();
	}

}
