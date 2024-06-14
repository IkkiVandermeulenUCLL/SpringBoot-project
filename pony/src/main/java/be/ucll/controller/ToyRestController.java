package be.ucll.controller;

import org.springframework.web.bind.annotation.RestController;

import be.ucll.model.Toy;
import be.ucll.service.ToyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/toys")
public class ToyRestController {
    @Autowired
    private ToyService toyService;

    public ToyRestController(ToyService toyService){
        this.toyService = toyService;
    }

    @GetMapping
    public List<Toy> getToys() {
        return toyService.getToys();
    }
    
    @GetMapping("/{name}")
    public List<Toy> getToysByName(@PathVariable String name) {
        return toyService.getToysByName(name);
    }
    
    @PostMapping("/{name}")
    public Toy addToy(@PathVariable String name) {        
        return toyService.addToy(name);
    }

    @PostMapping("/{name}/animal/{animalName}")
    public void addAnimalToToy(@PathVariable String name, @PathVariable String animalName) {
        toyService.addAnimal(name, animalName);
    }
    
    
}
