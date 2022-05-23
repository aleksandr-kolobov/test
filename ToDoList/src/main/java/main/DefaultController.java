package main;

import java.util.ArrayList;
import java.util.List;

import main.model.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import main.model.Target;

@Controller
public class DefaultController {

    @Autowired
    private TargetRepository targetRepository;

    @Value("${greetings.value}")
    private String greetings;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Target> targetIterable = targetRepository.findAll();
        List<Target> targets = new ArrayList<>();
        for (Target target : targetIterable) {
            targets.add(target);
        }
        model.addAttribute("targets", targets);
        model.addAttribute("targetsCount", targets.size());
        model.addAttribute("greetings", greetings);
        return "index";
    }
}