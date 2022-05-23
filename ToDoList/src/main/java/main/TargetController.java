package main;

import main.model.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TargetController {

    @Autowired
    private TargetRepository targetRepository;

    @RequestMapping(value = "/targets/", method = RequestMethod.GET)
    public List<Target> list() {
        Iterable<Target> targetIterable = targetRepository.findAll();
        List<Target> targets = new ArrayList<>();
        for (Target target : targetIterable) {
            targets.add(target);
        }
        return targets;
    }

    @GetMapping("/targets/{id}")
    public ResponseEntity<Target> get(@PathVariable int id) {
        Optional<Target> optionalTarget = targetRepository.findById(id);
        return optionalTarget.isPresent() ?
            new ResponseEntity(optionalTarget.get(), HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/targets/", method = RequestMethod.POST)
    public int add(Target target) {
        Target newTarget = targetRepository.save(target);
        return newTarget.getId();
    }

    @PostMapping("/targets/{id}")
    public ResponseEntity addId() {
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/targets/")
    public ResponseEntity refreshAll() {
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/targets/{id}", method = RequestMethod.PUT)
    public ResponseEntity refresh(@PathVariable int id, Target target) {
        target.setId(id);
        Target newTarget = targetRepository.save(target);
        return new ResponseEntity(newTarget, HttpStatus.OK);
    }

    @DeleteMapping("/targets/")
    public ResponseEntity deleteAll() {
        targetRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/targets/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int id) {
        if (targetRepository.existsById(id)) {
            targetRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
