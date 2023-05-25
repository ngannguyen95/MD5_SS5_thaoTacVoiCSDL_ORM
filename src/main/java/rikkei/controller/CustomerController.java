package rikkei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rikkei.model.entity.Customer;
import rikkei.service.ICustomerService;

import java.util.List;

@Controller

public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/")
    public String findAll(Model model) {
        List<Customer> list = customerService.findAll();
        model.addAttribute("list", list);
        return "home";
    }
    @GetMapping("/formCreate")
    public String formCreate(Model model){
        Customer customer = new Customer();
        model.addAttribute("Customer",customer);
        return "createCus";
    }
    @PostMapping("/create")
    public String createCustomer(@ModelAttribute("Customer") Customer customer){
        customerService.save(customer);
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")long id){
        customerService.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id")long id,Model model){
       Customer customer = customerService.findById(id);
       model.addAttribute("cusEdit",customer);
       return "edit";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("cusEdit")Customer customer){
        customerService.save(customer);
        return "redirect:/";
    }
}
