package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Test
    public void newDepartment_departmentInstantiatesCorrectly() {
        Department department = setupDepartment();
        assertTrue(department instanceof Department);
    }

    @Test
    public void getName_departmentIstantiatesWithNameCorrectly() {
        Department department = setupDepartment();
        assertEquals("History", department.getName());
    }

    @Test
    public void getDescription_departmentIstantiatesWithDescriptionCorrectly() {
        Department department = setupDepartment();
        assertEquals("History subjects", department.getDescription());
    }

    @Test
    public void getNoOfEmployees_departmentIstantiatesWithNoOfEmployeesCorrectly() {
        Department department = setupDepartment();
        assertEquals(5, department.getNoOfEmployees());
    }

    @Test
    public void getId_departmentIstantiatesWithIdCorrectly() {
        Department department = setupDepartment();
        department.setId(1);
        assertEquals(1, department.getId());
    }

    @Test
    public void setName_nameCanBeSetCorrectly() {
        Department department = setupDepartment();
        department.setName("Government");
        assertEquals("Government", department.getName());
    }

    @Test
    public void setDescription_descriptionCanBeSetCorrectly() {
        Department department = setupDepartment();
        department.setDescription("Government");
        assertEquals("Government", department.getDescription());
    }

    @Test
    public void setNoOfEmployees_noOfEmployeesCanBeSetCorrectly() {
        Department department = setupDepartment();
        department.setNoOfEmployees(1);
        assertEquals(1, department.getNoOfEmployees());
    }

    @Test
    public void setId_idCanBeSetCorrectly() {
        Department department = setupDepartment();
        department.setId(1);
        assertEquals(1, department.getId());
    }

    @Test
    public void equal_departmentsWithSameNameAndDescriptionAreEqual() {
        Department department = setupDepartment();
        Department secondDepartment = setupDepartment();
        assertEquals(true, department.equals(secondDepartment));
    }

    //helpers
    public Department setupDepartment() {
        return new Department("History", "Government subjects", 5);
    }
}