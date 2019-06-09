package com.kentech.rest.webservice.todowebservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//cross origin is used to allow specific web services to fulfill their http requests from this controller
@CrossOrigin(origins="http://localhost:4200")
@RestController
public class HelloWorldController {
	
	//GET method - "Hello World"
	//URI - /hello-world
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	
	//hello-world-bean
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello Ken Bean");
	}
	
	//hello-world-bean w/ path parameter
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloNameBean(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello %s", name));
	}


}
