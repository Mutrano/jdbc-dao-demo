package application;

import java.time.LocalDate;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		Department dep = new Department(1, "Books");
		System.out.println(dep);
		Seller seller = new Seller(21, "bob", "Bob@gmail.com",LocalDate.now(), 3000.0, dep);
		System.out.println(seller);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
	}
}
