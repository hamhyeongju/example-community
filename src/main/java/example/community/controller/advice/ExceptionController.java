package example.community.controller.advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(annotations = Controller.class)
public class ExceptionController {

    @ExceptionHandler
    public String ex(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("error", "bad-request");
        return "redirect:/";
    }
}
