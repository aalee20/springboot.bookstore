package com.example.bookstore;

import java.util.Comparator;

import com.example.bookstore.model.Book;

public class SortPrice implements Comparator<Book> {

	@Override
	public int compare(Book A, Book B) {
		 return A.getPrice().compareTo(B.getPrice());
	}

}
