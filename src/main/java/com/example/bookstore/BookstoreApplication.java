package com.example.bookstore;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import com.example.bookstore.model.Book;
import com.example.bookstore.controller.Bookcontroller;
import com.example.bookstore.repository.Bookrepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
		
		Bookcontroller n= new Bookcontroller();
		Bookrepository repo = null;
		String end2;
		do {
		Scanner sc = new Scanner(System.in); 
		
		System.out.println("To Get (get all books) enter G.."+"\n"+"TO Post(To create book) enter P.."+"\n"+"To Put (update an existing book) by id enter PUI "+"\n"
				+"To Get by id enter GI"+"\n"+"To Delete by id enter DI"+"\n"+"To Get the book Paginated enter PAG"+"\n"
				+"To Get the books filtered by price enter GFP"+"\n"+"To Get the books filtered by authorname enter GFA"+"\n"+"To Sort By Price in Descending Order enter SD"+"\n"+"To Sort By Price in Ascending Order enter SA");
		String choice= sc.nextLine();
		
		if(choice.equals("G")) {
			RestTemplate restTemplate = new RestTemplate();
	        String resourceUrl
	          = "http://localhost:8080/Book";
	        ResponseEntity<List> getbooks= restTemplate.getForEntity(resourceUrl, List.class);
	        List<Book> books = getbooks.getBody();
	        System.out.println(books);
			
		}
		else if(choice.equals("P")) {
			Scanner b = new Scanner(System.in); 
			System.out.println("Enter the information of your book you want to add:");
			System.out.println("bookname: ");
			String bname= b.nextLine();
			System.out.println("authorname: ");
			String auname= b.nextLine();
			System.out.println("description: ");
			String descr= b.nextLine();
			System.out.println("availableinstorenow: ");
			Boolean available= b.nextBoolean();
			b.nextLine();
			System.out.println("dateofpublication in the format YYYY-MM-DD: ");
			String dateofpub= b.nextLine();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = null;
	        try {
				date = dateFormat.parse(dateofpub);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("price: ");
			String pr= b.nextLine();
			
			Book bk= new Book(bname,auname,descr,available,date,pr);
			
			RestTemplate restTemplate = new RestTemplate();

	        String resourceUrl
	          = "http://localhost:8080/Book";

	        HttpEntity<Book> request = new HttpEntity<Book>(bk);

	        // Send the request body in HttpEntity for HTTP POST request
	        String productCreateResponse = restTemplate
	               .postForObject(resourceUrl, request, String.class);
	        
	        System.out.println(productCreateResponse);
			
		}
		
		else if(choice.equals("PUI")) {
			
			
			Scanner Id = new Scanner(System.in); 
			System.out.println("Enter the book name you want to update: ");
			String id= Id.nextLine();
					
		
			RestTemplate restTemplate = new RestTemplate();
			String idUrl="http://localhost:8080/Book/";
			Book book = restTemplate.getForObject(idUrl+id, Book.class);
		Book toupdate= book;
		String end;
		do {
		System.out.println("To update author name press 1... \n to update description press 2... \n to update the avialibility press 3..."
				+ "\n to update the date of publication press 4 \n to update the price pess 5: ");
		Scanner c = new Scanner(System.in); 
		String choose= c.nextLine();
		
		if(choose.equals("1")) {
			System.out.println("Enter the author name:");
			String name= c.nextLine();
			toupdate.setAuthorname(name);
		}
		
		else if(choose.equals("2")) {
			System.out.println("Enter the description:");
			String desc= c.nextLine();
			toupdate.setDescription(desc);		}
		else if(choose.equals("3")) {
			System.out.println("Enter the avialibility:");
			Boolean av= c.nextBoolean();
			c.nextLine();
			toupdate.setAvailableinstorenow(av);
		}
		
		else if(choose.equals("4")) {
			System.out.println("dateofpublication in the format YYYY-MM-DD: ");
			String dateofpub= c.nextLine();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = null;
	        try {
				date = dateFormat.parse(dateofpub);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        toupdate.setDateofpublication(date);
		}
		
		else if(choose.equals("5")) {
			System.out.println("Enter the Price:");
			String pr= c.nextLine();
			toupdate.setPrice(pr);;
		}
		
		System.out.println("If you're done press y else Press n");
		end= c.nextLine();
		}while(!end.equals("y"));
		HttpEntity<Book> req = new HttpEntity<Book>(toupdate);
			
	        String resourceUrl = "http://localhost:8080/Book/";
	        
	        ResponseEntity<String> response = restTemplate.exchange(
	        		  resourceUrl + id,
	        		  HttpMethod.PUT,
	        		  req,
	        		  String.class
	        		);
	        
	        
	        System.out.println(response);
	        
		}
		
		else if(choice.equals("GI")) {
			Scanner Id = new Scanner(System.in); 
			System.out.println("Enter the book name you want to find: ");
			String id= Id.nextLine();
			RestTemplate restTemplate = new RestTemplate();
	        String resourceUrl = "http://localhost:8080/Book/";
	       // ResponseEntity<Book> book= restTemplate.getForEntity(resourceUrl+id, Book.class);
	        ResponseEntity<String> response = restTemplate.exchange(
	        		  resourceUrl + id,
	        		  HttpMethod.GET,
	        		  null,
	        		  String.class
	        		);
	        System.out.println("The book: "+"\n");
	        System.out.println(response);
		}
		
		else if(choice.equals("DI")) {
			Scanner Id = new Scanner(System.in); 
			System.out.println("Enter the book name you want to delete: ");
			String id= Id.nextLine();
			RestTemplate restTemplate = new RestTemplate();
	        String resourceUrl = "http://localhost:8080/Book/";
	        
	        ResponseEntity<String> response = restTemplate.exchange(
	        		  resourceUrl + id,
	        		  HttpMethod.DELETE,
	        		  null,
	        		  String.class
	        		);
	        System.out.println(response);
			
		}
		
		else if(choice.equals("PAG")) {
			Scanner G= new Scanner(System.in);
			System.out.println("Enter the size of the page: ");
			int size= G.nextInt();
			System.out.println("Enter the page number: ");
			int page= G.nextInt();
			RestTemplate restTemplate = new RestTemplate();
	        String resourceUrl = "http://localhost:8080/Bookp";
	        ResponseEntity<String> pag = restTemplate.exchange(
	        		  resourceUrl+"?"+"size="+size+"&"+"page="+page,
	        		  HttpMethod.GET,
	        		  null,
	        		  String.class
	        		);
	        System.out.println(pag); 
			
		}
		
		else if(choice.equals("GFP")) {
			Scanner G= new Scanner(System.in);
			System.out.println("Enter the price: ");
			int price= G.nextInt();
			RestTemplate restTemplate = new RestTemplate();
	        String resourceUrl = "http://localhost:8080/Books/filterByPrice";
	        ResponseEntity<String> pricefilter = restTemplate.exchange(
	        		  resourceUrl+"?"+"price="+price,
	        		  HttpMethod.GET,
	        		  null,
	        		  String.class
	        		);
	        System.out.println(pricefilter); 
		}
		
		else if(choice.equals("GFA")) {
			Scanner G= new Scanner(System.in);
			System.out.println("Enter the authorname: ");
			String authorname= G.nextLine();
			RestTemplate restTemplate = new RestTemplate();
	        String resourceUrl = "http://localhost:8080/Bookss/filterByAuthorname";
	        ResponseEntity<String> auhtorfilter = restTemplate.exchange(
	        		  resourceUrl+"?"+"authorname="+authorname,
	        		  HttpMethod.GET,
	        		  null,
	        		  String.class
	        		);
	        System.out.println(auhtorfilter); 
		}
		else if(choice.equals("SD")) {
			RestTemplate restTemplate = new RestTemplate();
	        String resourceUrl = "http://localhost:8080/Booksort/SortbyPriceDESC";
	        ResponseEntity<String> sortp = restTemplate.exchange(
	        		  resourceUrl,
	        		  HttpMethod.POST,
	        		  null,
	        		  String.class
	        		);
	        
	        System.out.println(sortp);
		}
		
		else if(choice.equals("SA")) {
			RestTemplate restTemplate = new RestTemplate();
	        String resourceUrl = "http://localhost:8080/Booksort/SortbyPriceASC";
	        ResponseEntity<String> sortp = restTemplate.exchange(
	        		  resourceUrl,
	        		  HttpMethod.POST,
	        		  null,
	        		  String.class
	        		);
	        
	        System.out.println(sortp);
		}
		Scanner S = new Scanner(System.in);
		System.out.println("If you're done press Y else Press N");
		end2= S.nextLine();
		}while(!end2.equals("Y"));
	}
	
}
