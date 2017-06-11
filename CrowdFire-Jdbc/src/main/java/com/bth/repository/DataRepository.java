package com.bth.repository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bth.annotations.SQLColumn;
import com.bth.annotations.SQLEntity;
import com.bth.annotations.SQLKey;

public class DataRepository <T> {

	private Class<T> clazz;
	private Session session;
	
	private static final Logger LOG = Logger.getLogger(DataRepository.class);
	
	public DataRepository (Class<T> clazz, Session session) {
		this.clazz = clazz;
		this.session = session;
	}
	
	public T get(Object id) throws SQLException {
		
		String sql = "SELECT ("
				+ StringUtils.join(getFullProjection(), ", ") + ")"
				+ " FROM " + getTableName() 
				+ " WHERE " + getIdColumnName()
				+ " = " + id;
		
		PreparedStatement pst = session.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		T obj = null;
		
		if (rs.next()) {
			obj = mapResult(rs, getFullProjection());
		}
		
		return obj;
	}
	
	public List<T> get(String conditions) throws SQLException {
		
		String sql = "SELECT ("
				+ StringUtils.join(getFullProjection(), ", ")
				+ " FROM " + getTableName()
				+ "WHERE " + conditions;
		
		PreparedStatement pst = session.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		List<T> results = new ArrayList<>();
		
		while (rs.next()) {
			T obj = mapResult(rs, getFullProjection());
			if (obj != null) {
				results.add(obj);
			}
		}
		
		return results;
	}
	
	public T insert(T object) throws SQLException {
		
		String sql = "INSERT INTO " + getTableName() + "("
			+ StringUtils.join(getFullProjection(), ", ") + ")"
			+ " VALUES (" 
			+ StringUtils.join(mapValues(object), ", ") + ")";
		
		PreparedStatement pst = session.prepareStatementForKeyReturn(sql);
		
		if (pst.executeUpdate() > 0) {
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				Long id = rs.getLong(1);
				
				try {
					Field idField = getIdField();
					idField.setAccessible(true);
					idField.set(object, id);
				} catch (Exception ex) {
					LOG.error("Failed to set generated id", ex);
				}
				
			}
		}
		
		return object;
	}
	
	public boolean update(T object) throws SQLException {
		
		String sql = "UPDATE " + getTableName()
			+ " SET " + StringUtils.join(mapUpdates(object), ", ")
			+ " WHERE " + getIdColumnName() + " = " + getId(object);
		
		PreparedStatement pst = session.prepareStatement(sql);
		
		return pst.execute();
	}
	
	public boolean delete(T object) throws SQLException {
		
		String sql = "DELETE FROM " + getTableName()
			+ " WHERE " + getIdColumnName() + " = " + getId(object);
		
		PreparedStatement pst = session.prepareStatement(sql);
		
		return pst.execute();
	}
	
	private Object getId(T object) throws SQLException {
		
		Object id = null;
		Field field = getIdField();
		
		try {
			if (field != null) {
				field.setAccessible(true);
				id = field.get(object);
			}
		} catch (Exception ex) {
			throw new SQLException(ex);
		}
		
		return id;
		
	}
	
	private Field getIdField() {
		for (Field field : clazz.getDeclaredFields()) {
			SQLKey key = field.getAnnotation(SQLKey.class);
			
			if (key != null) {
				return field;
			}
		}
		
		return null;
	}
	
	private List<String> getFullProjection() {
		List<String> columns = new ArrayList<>();
		
		for (Field field : clazz.getDeclaredFields()) {
			for (Annotation annot : field.getAnnotations()) {
				if (annot instanceof SQLColumn) {
					SQLColumn column = (SQLColumn) annot;
					columns.add(column.name());
				}
			}
		}
		
		return columns;
	}
	
	private String getTableName() {
		for (Annotation annot : clazz.getAnnotations()) {
			if (annot instanceof SQLEntity) {
				SQLEntity entity = (SQLEntity) annot;
				return entity.tableName();
			}
		}
		return null;
	}
	
	private String getIdColumnName() {
		Field field = getIdField();
		if (field != null) {
			SQLColumn col = field.getAnnotation(SQLColumn.class);
			return col.name();
		}
		return null;
	}
	
	private T mapResult(ResultSet rs, List<String> projection)  {
		
		T obj = null;
		
		try {
			
			obj = clazz.newInstance();
			
			for (Field field : clazz.getFields()) {
				SQLColumn col = field.getAnnotation(SQLColumn.class);
				if (col != null && projection.contains(col.name())) {
					field.setAccessible(true);
					field.set(obj, rs.getObject(col.name()));
				}
			}
			
		} catch (Exception ex) {
			LOG.error("Failed to map result set to object", ex);
		}
		
		return obj;
	}
	
	private List<String> mapValues(T object) throws SQLException {
		
		List<String> values = new ArrayList<>();
		
		try {
			
			for (Field field : clazz.getFields()) {
				SQLColumn col = field.getAnnotation(SQLColumn.class);
				
				Object fieldObject = field.get(object);
				
				if (col != null) {
					
					if (fieldObject == null) {
						values.add("NULL");
						break;
					}
					
					if (field.getType() == Date.class) {
						Date date = (Date) fieldObject;
						values.add(Commons.postgresDateString.format(date));
						break;
					}
					
					values.add(fieldObject.toString());
					
				}
			}
			
		} catch (Exception ex) {
			throw new SQLException(ex);
		}
		
		return values;
	}
	
	private List<String> mapUpdates(T object) throws SQLException {
		
		List<String> updates = new ArrayList<>();
		
		try {
			
			for (Field field : clazz.getFields()) {
				SQLColumn col = field.getAnnotation(SQLColumn.class);
				if (col != null) {
					
					field.setAccessible(true);
					Object fieldValue = field.get(object);
					String updateValue = null;
					
					if (fieldValue == null) {
						updateValue = "NULL";
					} else if (field.getType() == Date.class) {
						Date date = (Date) fieldValue;
						updateValue = Commons.postgresDateString.format(date);
					} else {
						updateValue = fieldValue.toString();
					}
					
					updates.add(col.name() + " = " + updateValue);
				}
			}
			
		} catch (Exception ex) {
			throw new SQLException(ex);
		}
		
		return updates;
	}
	
}
