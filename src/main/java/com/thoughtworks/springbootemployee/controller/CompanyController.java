package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequestDTO;
import com.thoughtworks.springbootemployee.dto.CompanyResponseDTO;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
        public List<CompanyResponseDTO> getCompanies(Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            return companyService.getCompaniesByPage(page, pageSize).toList();
        }
        return companyService.getCompanies();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompany(@PathVariable int id) {
        return companyService.getCompany(id);
    }

    @GetMapping("{id}/employees")
    @ResponseStatus(HttpStatus.OK)

    public List<Employee> getEmployeesOfCompany(@PathVariable int id) {
        return companyService.getEmployees(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
        return companyService.addCompany(companyRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@PathVariable int id, @RequestBody CompanyRequestDTO companyInfo) {
        return companyService.updateCompany(id, companyInfo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable int id) {
        companyService.deleteCompanyById(id);
    }
}
