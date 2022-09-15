package com.books.BookApplication.repository;

import com.books.BookApplication.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, Integer> {
    Optional<Book> findByIsbnIgnoreCase(String isbn);
    Optional<Book> findByTitleIgnoreCase(String title);
    @Query("{ 'authors': { $elemMatch: { 'firstName' : :#{#name} } }}")
    Optional<Book> findBookWithAuthorName(@Param("name") String name);
    @Query("{ 'copies': { $elemMatch: { 'scanCode' : :#{#scanCode} } }}")
    Optional<Book> findBookWithScanCode(@Param("scanCode") String scanCode);
    @Query("{ 'copies': { $elemMatch: { 'isAvailable' :true} }}")
    List<Book> findBooksWithAvailableCopies();

}
