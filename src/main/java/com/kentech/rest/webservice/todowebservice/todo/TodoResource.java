package com.kentech.rest.webservice.todowebservice.todo;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoResource {

	@Autowired
	private TodoHardCodedService todoService;

	// get all todos
	@GetMapping("/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username) {

		return todoService.findAll();
	}

	// get a specific todo
	@GetMapping("/users/{username}/todos/{todoId}")
	public Todo getTodo(@PathVariable String username, @PathVariable long todoId) {

		return todoService.findByTodoId(todoId);
	}

	// we used a ResponseEntity return type so that we can define/specify what kind
	// of response should we give to the consumers of this Microservice
	@DeleteMapping("/users/{username}/todos/{todoId}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long todoId) {
		// ResponseEntity is a generic type you can use any type as a response body like
		// <String>, <T>(for ambiguous objects), <void>(mostly for header only response)
		Todo todo = todoService.deleteById(todoId);

		if (todo != null) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	// add a todo
	@PostMapping("/users/{username}/todos/{todoId}")
	public ResponseEntity<Void> addTodo(@PathVariable String username, @PathVariable long todoId) {

		Todo todo = todoService.findByTodoId(todoId);

		todo = todoService.save(todo);

		if (todo != null) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	// update a todo
	@PutMapping("/users/{username}/todos/{todoId}")
	public ResponseEntity<Todo> updateTodo(//used a <Todo> data type for the response entity body
			// the @RequestBody annotation is used to retrieve the data that is in the body as oppose to the @PathVariable which retrieves the data on the URL
			@PathVariable String username, @PathVariable long todoId, @RequestBody Todo todo) {

		Todo updatedTodo = todoService.save(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
		
	}
	
	// create a todo
		@PostMapping("/users/{username}/todos")
		public ResponseEntity<Void> createTodo(//used a <Todo> data type for the response entity body
				// the @RequestBody annotation is used to retrieve the data that is in the body as oppose to the @PathVariable which retrieves the data on the URL
				@PathVariable String username, @RequestBody Todo todo) {
			

			Todo createdTodo = todoService.save(todo);
			
			// in a PostMapping you typically want to return for a created resource is it's location
			// get current resource URL then add the newly created ID = /users/{username}/todos/ + {todoId}		
			URI newUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
			
			// returns a ResponseEntity staus as created with the new URI
			return ResponseEntity.created(newUri).build();
			
		}

}
