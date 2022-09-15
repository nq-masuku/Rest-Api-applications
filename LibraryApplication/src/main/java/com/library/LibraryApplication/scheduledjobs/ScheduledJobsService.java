package com.library.LibraryApplication.scheduledjobs;

import com.library.LibraryApplication.domain.Customer;
import com.library.LibraryApplication.integration.BookApplicationRestClient;
import com.library.LibraryApplication.repository.CustomerRepository;
import com.library.LibraryApplication.service.Books;
import com.library.LibraryApplication.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledJobsService {

    @Autowired
    private BookApplicationRestClient client;

    @Autowired
    private CustomerRepository customerRepository;

    Logger logger= LoggerFactory.getLogger(ScheduledJobsService.class);
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Scheduled(initialDelay = 1000, fixedDelay = 20000)
    public void loadData(){
        Books response = client.getAllBooks();
        if(response != null){
            eventPublisher.publishEvent(response);
        }


        List<Customer> cust=customerRepository.findByAmountOwing();
        if(cust !=null){
            cust.forEach(customer-> {
                logger.info("Customer with outstanding amount: "+ customer.getCustomerNumber()+","+
                        customer.getFirstName()+" "+ customer.getLastName());
            });
        }
    }
}
