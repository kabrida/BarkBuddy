package syksy2024.backend.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import syksy2024.backend.model.Owner;
import syksy2024.backend.repository.OwnerRepository;

@Controller
public class OwnerManagementController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/owner-management")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getOwnerManagement(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());
        return "owner-management";
    }

   @GetMapping("/owner/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editOwner(@PathVariable("id") Long id, Model model) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if (optionalOwner.isPresent()) {
            model.addAttribute("owner", optionalOwner.get());
            return "edit-owner";
        } else {
            model.addAttribute("error", "Owner not found with ID: " + id);
            return "redirect:/owner-management";
        }
    }

    @PostMapping("/owner/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveOwner(@ModelAttribute("owner") Owner owner, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("owner", owner);
            return "edit-owner";
        }
        
        Owner existingOwner = ownerRepository.findById(owner.getId()).orElseThrow(() -> new RuntimeException("Owner not found"));
        existingOwner.setFirstName(owner.getFirstName());
        existingOwner.setLastName(owner.getLastName());
        existingOwner.setRole(owner.getRole());

        ownerRepository.save(existingOwner);
        return "redirect:/owner-management";
    }

}
