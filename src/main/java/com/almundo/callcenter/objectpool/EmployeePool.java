package com.almundo.callcenter.objectpool;

import java.util.Enumeration;
import java.util.Hashtable;

import com.almundo.callcenter.config.EmployeeStatus;
import com.almundo.callcenter.model.employee.Employee;

public abstract class EmployeePool {

	protected Hashtable<Employee, Long> locked, unlocked;
	protected Integer ammount;

	public EmployeePool() {
		locked = new Hashtable<Employee, Long>();
		unlocked = new Hashtable<Employee, Long>();
	}

	protected abstract void loadPool();

	public synchronized Employee getEmployee() {
		Employee emp;
		if (unlocked.size() > 0) {
			Enumeration<Employee> e = unlocked.keys();
			while (e.hasMoreElements()) {
				emp = e.nextElement();
				if(emp.getStatus().equals(EmployeeStatus.FREE)){
					unlocked.remove(emp);
					locked.put(emp, emp.getId());
					return (emp);
				}else{
					unlocked.remove(emp);
					locked.put(emp, emp.getId());
				}
			}
		}
		if (locked.size() > 0) {
			Enumeration<Employee> e = locked.keys();
			while (e.hasMoreElements()) {
				emp = e.nextElement();
				if (emp.getStatus().equals(EmployeeStatus.FREE)) {
					return (emp);
				}
			}
		}
		return null;
	}

	public synchronized void releaseEmployee(Employee emp) {
		locked.remove(emp);
		unlocked.put(emp, emp.getId());
	}
	
	
}
