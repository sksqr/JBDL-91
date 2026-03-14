package com.example.L15_UTandITdemo.repo;

import com.example.L15_UTandITdemo.dto.EmployeeDetailReqDto;
import com.example.L15_UTandITdemo.entity.Address;
import com.example.L15_UTandITdemo.entity.Branch;
import com.example.L15_UTandITdemo.entity.Employee;
import com.example.L15_UTandITdemo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        }
)
public class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BranchRepo branchRepo;




    private Branch branch;

    private Employee employee;

    private Address address;

    @BeforeEach
    public void setup(){


        branch = new Branch();
        branch.setName("Noida Branch");


        branchRepo.save(branch);

        address = new Address();
        address.setCity("Delhi");
        address.setLine1("H 120");
        address.setLine2("Sector 40");

        employee = new Employee();
        employee.setEmail("ravi@yopmail.com");
        employee.setName("Ravi");
        employee.setBranch(branch);
        employee.setAddress(address);

        employee = employeeRepo.save(employee);


    }



    @Test
    public void testFindByEmail(){

        String email = "ravi@yopmail.com";

        Employee employee1 = employeeRepo.findByEmail(email);
        assertThat(employee1.getId()).isEqualTo(1l);
        assertThat(employee1.getName()).isEqualTo("Ravi");
    }



    @Test
    public void testFindByEmailEmployeeDoesNotExist(){

        String email = "rahul@yopmail.com";

        Employee employee1 = employeeRepo.findByEmail(email);
        assertThat(employee1).isEqualTo(null);

    }
}
