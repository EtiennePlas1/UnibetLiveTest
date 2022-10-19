package com.kindredgroup.unibetlivetest.api;

import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.exception.ExceptionHttpTranslator;
import com.kindredgroup.unibetlivetest.mapper.UnibetMapperService;
import com.kindredgroup.unibetlivetest.service.CustomerService;
import com.kindredgroup.unibetlivetest.v1.model.Customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Log4j2
@RequestMapping(Urls.BASE_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerApi implements com.kindredgroup.unibetlivetest.v1.api.CustomerApi{

    @Resource
    private CustomerService customerService;
    
    @Resource
    private UnibetMapperService mapper;
    
    @Resource 
    private ExceptionHttpTranslator translator;

    public ResponseEntity<Customer> fetchCustomer() {
    	
        try {
			return new ResponseEntity<Customer>(mapper.asCustomer(customerService.findCustomerByPseudo("unibest")), HttpStatus.OK);
		} catch (CustomException e) {
			log.error(e.getMessage());
			return translator.businessException(ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath(), e);
		} catch (Exception e) {
			log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    
    }


}
