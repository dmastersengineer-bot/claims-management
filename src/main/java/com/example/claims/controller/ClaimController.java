package com.example.claims.controller;

import com.example.claims.model.Claim;
import com.example.claims.repository.ClaimRepository;
import com.example.claims.service.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimRepository claimRepository;
    private final FileStorageService fileStorageService;

    public ClaimController(ClaimRepository claimRepository, FileStorageService fileStorageService) {
        this.claimRepository = claimRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public String listClaims(Model model) {
        model.addAttribute("claims", claimRepository.findAll());
        return "claims/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("claim", new Claim());
        return "claims/form";
    }

    @PostMapping
    public String saveClaim(@Valid @ModelAttribute("claim") Claim claim,
                            BindingResult result,
                            @RequestParam("file") MultipartFile file,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "claims/form";
        }

        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            claim.setFileName(fileName);
        }

        if (claim.getStatus() == null || claim.getStatus().isEmpty()) {
            claim.setStatus("SUBMITTED");
        }

        claimRepository.save(claim);
        redirectAttributes.addFlashAttribute("message", "Claim successfully saved!");
        return "redirect:/claims";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Claim claim = claimRepository.findById(id)
                .orElse(null);
        if (claim == null) {
            redirectAttributes.addFlashAttribute("error", "Claim not found!");
            return "redirect:/claims";
        }
        model.addAttribute("claim", claim);
        return "claims/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteClaim(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Claim claim = claimRepository.findById(id).orElse(null);
        if (claim != null) {
            claimRepository.delete(claim);
            redirectAttributes.addFlashAttribute("message", "Claim deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Claim not found!");
        }
        return "redirect:/claims";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found with id " + id));

        if (claim.getFileName() == null) {
            throw new RuntimeException("No file attached to this claim.");
        }

        Resource resource = fileStorageService.loadFileAsResource(claim.getFileName());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"\" + resource.getFilename() + \"\"")
                .body(resource);
    }
}
