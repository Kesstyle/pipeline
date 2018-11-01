package by.minsk.kes.pipeline;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

  @RequestMapping(method = RequestMethod.GET, path = "/api/ping")
  @ResponseBody
  public String ping() {
    return "Hello postgress app!";
  }
}
