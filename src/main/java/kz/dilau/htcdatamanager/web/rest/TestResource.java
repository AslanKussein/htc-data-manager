package kz.dilau.htcdatamanager.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestResource {

    @GetMapping("")
    public String test() {
        return "test success";
    }
}
