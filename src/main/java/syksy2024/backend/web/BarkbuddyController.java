package syksy2024.backend.web;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import syksy2024.backend.model.Breed;
import syksy2024.backend.model.Dog;
import syksy2024.backend.model.Owner;
import syksy2024.backend.repository.BreedRepository;
import syksy2024.backend.repository.DogRepository;
import syksy2024.backend.repository.OwnerRepository;
import syksy2024.backend.service.BreedService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;


@Controller
public class BarkbuddyController {
    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private BreedService breedService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BreedRepository breedRepository;


    @RequestMapping({"/", "/index"})
    public String showIndexPage() {
        return "index";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
}

    @RequestMapping(value = "/barkbuddy")
    public String showDogs(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String breed,
        @RequestParam(required = false) String ownerName,
        @RequestParam(defaultValue = "name") String sortBy,
        Model model,
        Principal principal) { 

    
        Owner owner = ownerRepository.findByUsername(principal.getName());
            model.addAttribute("owner", owner);

        // Määritellään sivutuksen asetukset: 10 koiraa per sivu
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy).ascending()); // Lajitellaan valitun kentän mukaan

    // Haetaan koirat suodatettuna
        Page<Dog> dogPage;
            if (breed != null && !breed.isEmpty()) {
                dogPage = dogRepository.findByBreed_Name(breed, pageable); // Suodata rodun mukaan
            } else if (ownerName != null && !ownerName.isEmpty()) {
                dogPage = dogRepository.findByOwner_FirstNameContainingOrOwner_LastNameContaining(ownerName, ownerName, pageable); // Suodata omistajan nimen mukaan
            } else {
                dogPage = dogRepository.findAll(pageable); // Jos ei suodatusta, hae kaikki koirat
            }

            model.addAttribute("dogs", dogPage.getContent()); // Koirien lista
            model.addAttribute("currentPage", page); // Nykyinen sivu
            model.addAttribute("totalPages", dogPage.getTotalPages()); // Yhteensä sivuja
            model.addAttribute("breedFilter", breed); // Lisää rodun suodatusparametri malliin
            model.addAttribute("ownerFilter", ownerName); // Lisää omistajan suodatusparametri malliin
            model.addAttribute("sortBy", sortBy); // Lisää lajitteluparametri malliin
            return "doglist";
    }

    @GetMapping("/breed-info")
    public String getBreedInfo(@RequestParam(value = "breedId", required = false) Long breedId, Model model) {
        Breed[] breedsArray = breedService.findAllBreeds();
        List<Breed> breeds = Arrays.asList(breedsArray);
        model.addAttribute("breeds", breeds);

        if (breedId != null) {
            Breed breed = breedRepository.findById(breedId).orElse(null);
            model.addAttribute("breed", breed);
        }


        return "breed-info";
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
    public String saveDog(@Valid @ModelAttribute("dog") Dog dog, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        System.out.println("Error: " + dog);
        model.addAttribute("dog", dog);
        model.addAttribute("owners", ownerRepository.findAll());
        model.addAttribute("breeds", breedService.findAllBreeds());
        return "adddog";        
    }
    
    try {
        dogRepository.save(dog);
    } catch (DataIntegrityViolationException e) {
        bindingResult.rejectValue("regNum", "error.dog", "The registration number is already in use.");
        model.addAttribute("dog", dog);
        model.addAttribute("owners", ownerRepository.findAll());
        model.addAttribute("breeds", breedService.findAllBreeds());
        return "adddog";
    }

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
