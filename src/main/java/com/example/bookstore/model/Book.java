package com.example.bookstore.model;


	import java.util.Date;

	import org.springframework.data.annotation.Id;
	import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
	import lombok.Getter;
	import lombok.NoArgsConstructor;
	import lombok.Setter;

	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Document(collection="Books")
	public class Book {
		
		

		
		//private String id;
		
		
		@Id @NotNull(message="book name cannot be null")
		private String bookname;
		
		@NotNull(message="author name cannot be null")
		private String authorname;
		
		@NotNull(message="description cannot be null")
		private String description;
		
		@NotNull(message="available in store cannot be null")
		private Boolean availableinstorenow;
		
		@NotNull(message="date of publication cannot be null")
		private Date dateofpublication;
		
		private String price;
		
		
		public Book(String bookname,String authorname,String description, Boolean availableinstorenow,Date dateofpublication,String price) {
			this.bookname=bookname;
			this.authorname=authorname;
			this.description=description;
			this.availableinstorenow=availableinstorenow;
			this.dateofpublication=dateofpublication;
			this.price=price;
			
		}
		public Book() {
	    }
		/*public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}*/
		public String getBookname() {
			return bookname;
		}

		public void setBookname(String bookname) {
			this.bookname = bookname;
		}

		public String getAuthorname() {
			return authorname;
		}

		public void setAuthorname(String authorname) {
			this.authorname = authorname;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Boolean getAvailableinstorenow() {
			return availableinstorenow;
		}

		public void setAvailableinstorenow(Boolean availableinstorenow) {
			this.availableinstorenow = availableinstorenow;
		}

		public Date getDateofpublication() {
			return dateofpublication;
		}

		public void setDateofpublication(Date dateofpublication) {
			this.dateofpublication = dateofpublication;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}
		
		@Override
		public String toString() {
		    return "Book{" +
		            "bookname=" + this.bookname + "\n"+
		            ", authorname='" + this.authorname + "\n" +
		            ", description='" + this.description + "\n" +
		            ", availableatstorenow=" + this.availableinstorenow +"\n"+
		            ", dateofpublication="+this.dateofpublication+"\n"+
		            ", price="+this.price+"\n"+
		            '}';
		}

		
}
