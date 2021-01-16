package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn=conn;
	}
	
	@Override
	public void insert(Seller obj) {
	}

	@Override
	public void update(Seller obj) {
	}

	@Override
	public void deleteById(Integer id) {
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Seller obj = new Seller();
		Department dep = new Department();
		try {
			st = conn.prepareStatement("SELECT\n"
					+ "	seller.*,\n"
					+ "	department.Name as DepName\n"
					+ "FROM\n"
					+ "	seller\n"
					+ "INNER JOIN department ON\n"
					+ "	seller.DepartmentId = department.Id\n"
					+ "WHERE\n"
					+ "	seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				dep = instantiateDepartment(rs);
				obj = instantiateSeller(rs,dep);
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			return obj;
		}
		
	}

	private Seller instantiateSeller(ResultSet rs,Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBirthDate(rs.getDate("BirthDate").toLocalDate());
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setDepartment(dep);
		return obj;
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		List<Seller> list = new ArrayList<Seller>();
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			rs = st.executeQuery();
			Map<Integer,Department> map= new HashMap<Integer, Department>();
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep==null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			return list;
		}
	}
	
	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;

		List<Seller> list = new ArrayList<Seller>();
		try {
			st = conn.prepareStatement("SELECT\n"
					+ "	seller.*,\n"
					+ "	department.Name as DepName\n"
					+ "FROM\n"
					+ "	seller\n"
					+ "INNER JOIN department ON\n"
					+ "	seller.DepartmentId = department.Id\n"
					+ "	WHERE DepartmentId = ?\n"
					+ "	ORDER BY Name");
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			Map<Integer,Department> map= new HashMap<Integer, Department>();
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep==null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			return list;
		}
	}
}
