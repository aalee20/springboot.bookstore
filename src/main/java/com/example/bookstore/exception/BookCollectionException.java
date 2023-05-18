package com.example.bookstore.exception;

public class BookCollectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public BookCollectionException(String message) {
		super(message);
	}
	public static String NotFoundException(String id) {
		return "Book with name: "+id+" is not found";
	}
	
	public static String BookAlreadyExists() {
		return "Book with given name is already exists";
	}
}
