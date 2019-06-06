package com.kentech.rest.webservice.todowebservice.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service //the Service annotation is used to inject this class and be autowireable
public class TodoHardCodedService {
	
	private static List<Todo> todos = new ArrayList();
	private static int idCounter = 0;
	
	static {
		todos.add(new Todo(++idCounter, "ken", "learn spring",  new Date(), false));
		todos.add(new Todo(++idCounter, "ken", "learn angular", new Date(), false));
		todos.add(new Todo(++idCounter, "ken", "learn Microservices", new Date(), false));
	}
	
	public List<Todo> findAll() {
		return todos;
	}
	
	public Todo save(Todo todo) {
		if(todo.getId()==-1 || todo.getId()==0) {
			//if todoId is -1 it will create a new todo else just update an existing one 
			todo.setId(++idCounter);
			todos.add(todo);
		} else {
			deleteById(todo.getId());
			todos.add(todo);
		}
		return todo;
	}

	public Todo deleteById(long todoId) {
		Todo todo = findByTodoId(todoId);
		
		if(todo==null) return null;//returns null if there are no existing id like that
		
		todos.remove(todo);//the .remove uses the equals method so you need to generate hashCode and equals in the Todo class for the id
		
		return todo;//if it exist then it will return the todo object and remove it
		
		//you can also enclose the todos.remove in an if() then return only if it returns true to be safe. like this if(todo.remove)return todo;
	}

	public Todo findByTodoId(long todoId) {
		for(Todo todo:todos) {
			if(todo.getId() == todoId) {
				return todo;
			}
		}
		
		return null;
	}
	
	
}
