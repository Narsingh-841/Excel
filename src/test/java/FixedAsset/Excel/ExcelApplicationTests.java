package FixedAsset.Excel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExcelApplicationTests {

    @GetMapping("/")
    public String home() {
        return "index"; // This will look for index.html in the static folder
    }
}
