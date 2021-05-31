package com.demo.springcrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springcrud.exception.ResourceNotFoundException;
import com.demo.springcrud.model.Employee;
import com.demo.springcrud.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long eid) throws ResourceNotFoundException{
		Employee employee=employeeRepository.findById(eid)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this ID :: "+eid));
		return ResponseEntity.ok().body(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long eid,@RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee=employeeRepository.findById(eid)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this ID :: "+eid));
		employee.setName(employeeDetails.getName());
		employee.setEmailId(employeeDetails.getEmailId());
		employeeRepository.save(employee);
		return ResponseEntity.ok().body(employee);
	}
	
	@DeleteMapping("employees/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id") long eid) throws ResourceNotFoundException {
		employeeRepository.findById(eid)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this ID :: "+eid));
		employeeRepository.deleteById(eid);
		return ResponseEntity.ok().build();
	}
	
}