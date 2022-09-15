package com.books.BookApplication;

import com.books.BookApplication.domain.Author;
import com.books.BookApplication.domain.Book;
import com.books.BookApplication.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJms
public class BookApplication implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(BookApplication.class);

	@Autowired
	BookRepository bookRepository;


	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("###################################################################################################");
		LOG.info("          BookApplication Spring Boot Application command-line initiated");
		LOG.info("###################################################################################################");

	}
}
