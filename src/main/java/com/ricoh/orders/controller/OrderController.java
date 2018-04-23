package com.ricoh.orders.controller;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ricoh.orders.model.Order;
import com.ricoh.orders.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/order")
@Api(value = "order", description = "Operations over order objects")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@ApiOperation(value = "Creates a new order", response = Order.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Order created correctly"),
			@ApiResponse(code = 201, message = "Order created correctly"),
			@ApiResponse(code = 401, message = "You are not authorized to create an order"),
			@ApiResponse(code = 403, message = "Forbidden order"),
			@ApiResponse(code = 404, message = "Order not found") })
	@PostMapping() // @RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Order> create(@RequestBody @Valid Order order) {
		Order newOrder = orderService.create(order);
		return newOrder == null ? ResponseEntity.badRequest().build() :
			ResponseEntity.ok(newOrder);
	}
	
	@ApiOperation(value = "Show an order based on the given id", response = Order.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Order obtained correctly"),
			@ApiResponse(code = 401, message = "You are not authorized to obtain an order"),
			@ApiResponse(code = 403, message = "Forbidden order"),
			@ApiResponse(code = 404, message = "Order not found") })
	@GetMapping("/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Order> read(@PathVariable("id") Long id) {
		Order order = orderService.read(id);
		return order == null ? ResponseEntity.notFound().build() :
			ResponseEntity.ok(order);
	}
	
	@ApiOperation(value = "Updates an order", response = Order.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Order updated correctly"),
			@ApiResponse(code = 201, message = "Order updated correctly"),
			@ApiResponse(code = 401, message = "You are not authorized to updated an order"),
			@ApiResponse(code = 403, message = "Forbidden order"),
			@ApiResponse(code = 404, message = "Order not found") })
	@PutMapping("/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Order> update(@PathVariable("id") Long id, @RequestBody @Valid Order order) {
		Order updatedOrder = orderService.update(id, order);
		return updatedOrder == null ? ResponseEntity.badRequest().build() :
			ResponseEntity.ok(updatedOrder);
	}
	
	@ApiOperation(value = "Delete an order based on the given id", response = Order.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Order deleted correctly"),
			@ApiResponse(code = 204, message = "No content order"),
			@ApiResponse(code = 401, message = "You are not authorized to delete an order"),
			@ApiResponse(code = 403, message = "Forbidden order") })
	@DeleteMapping("/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Order> delete(@PathVariable("id") Long id) {
		Order order = orderService.delete(id);
		return order == null ? ResponseEntity.notFound().build() :
			ResponseEntity.ok(order);
	}
	
	@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { EmptyResultDataAccessException.class })
	public void handleBadRequest() {
		//TODO Error response with ApiError
	}
	
	@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { EntityNotFoundException.class })
	public void handleNotFound() {
		//TODO Error response with ApiError
	}

}
