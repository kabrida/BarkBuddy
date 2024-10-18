package syksy2024.backend.web;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import syksy2024.backend.model.Dog;
import syksy2024.backend.model.Owner;
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


    @RequestMapping({"/", "/index"})
    public String showIndexPage() {
        return "index";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
}

    @RequestMapping(value = "/barkbuddy")
    public String showDogs(Model model, Principal principal) {
        Owner owner = ownerRepository.findByUsername(principal.getName());
        model.addAttribute("owner", owner);
        model.addAttribute("dogs", dogRepository.findAll());
        return "doglist";
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public String addDog(Model model) {
        model.addAttribute("dog", new Dog());
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        // Tarkistetaan käyttäjän rooli, jos ADMIN, haetaan kaikki omistajat
        if (authentication != null && authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
        model.addAttribute("owners", ownerRepository.findAll());
        } else {
        // Jos USER, hae vain nykyinen käyttäjä omistajaksi
        Owner owner = ownerRepository.findByUsername(authentication.getName());
        model.addAttribute("owners", List.of(owner));
        }

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteDog(@PathVariable("id") Long id, Model model) {
    	dogRepository.deleteById(id);
        return "redirect:/barkbuddy";
    }   

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("edit/{id}")
    public String editDog(@PathVariable("id") Long id, Model model) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
            if (optionalDog.isPresent()) {
        model.addAttribute("dog", optionalDog.get());
        model.addAttribute("owners", ownerRepository.findAll());
        model.addAttribute("breeds", breedService.findAllBreeds());
        return "editdog";
            } else {
        model.addAttribute("error", "Dog not found with ID: " + id);
        return "redirect:/barkbuddy";
    }
    }
    
    

}
