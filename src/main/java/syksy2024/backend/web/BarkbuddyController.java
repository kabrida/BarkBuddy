package syksy2024.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import syksy2024.backend.model.Dog;
import syksy2024.backend.repository.DogRepository;
import syksy2024.backend.repository.OwnerRepository;
import syksy2024.backend.service.BreedService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class BarkbuddyController {
    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private BreedService breedService;

    @Autowired
    private OwnerRepository ownerRepository;

    @RequestMapping({"/", "/barkbuddy"})
    public String showDogs(Model model) {
        model.addAttribute("dogs", dogRepository.findAll());
        return "doglist";
    }

    @RequestMapping(value = "/add")
    public String addDog(Model model) {
        model.addAttribute("dog", new Dog());
        model.addAttribute("owners", ownerRepository.findAll());
        model.addAttribute("breeds", breedService.findAllBreeds());
        return "adddog";
    }

    @PostMapping("/save")
    public String saveDog(@ModelAttribute("dog") Dog dog, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error: " + dog);
            model.addAttribute("dog", dog);
            model.addAttribute("owners", ownerRepository.findAll());
            model.addAttribute("breeds", breedService.findAllBreeds());
            return "adddog";        
        }
        dogRepository.save(dog);
        return "redirect:/barkbuddy";
    }

    @GetMapping("/delete/{id}")
    public String deleteDog(@PathVariable("id") Long id, Model model) {
    	dogRepository.deleteById(id);
        return "redirect:/barkbuddy";
    }   

    @GetMapping("edit/{id}")
    public String editDog(@PathVariable("id") Long id, Model model) {
        model.addAttribute("edit", dogRepository.findById(id));
        model.addAttribute("owners", ownerRepository.findAll());
        model.addAttribute("breeds", breedService.findAllBreeds());
        return "editdog";
    }
    
    

}
