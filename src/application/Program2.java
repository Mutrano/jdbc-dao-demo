package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		System.out.println("=== Test 1 : department insert ===");
		Department dep = new Department(null,"Music");
		departmentDao.insert(dep);
		System.out.println("Inserted! New id: "+dep.getId());

		System.out.println("\n=== Test 2 : department update ===");
		dep = new Department(6,"Games");
		departmentDao.update(dep);
		System.out.println("Update completed");
		
		System.out.println("\n=== Test 3 : department findById ===");
		dep = departmentDao.findById(3);
		System.out.println(dep);
		
		System.out.println("\n=== Test 4 : department findAll ===");
		List<Department> departments = new ArrayList<>();
		departments = departmentDao.findAll();
		departments.stream().forEach(System.out::println);
	}

}
