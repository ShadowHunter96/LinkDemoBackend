package UnicornInterview.LinkDemo.controller;

import UnicornInterview.LinkDemo.model.LinkChangeDTO;
import UnicornInterview.LinkDemo.model.LinkDTO;
import UnicornInterview.LinkDemo.model.LinkEntity;
import UnicornInterview.LinkDemo.service.LinkService;
import UnicornInterview.LinkDemo.utils.ImageUtils;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by User: Vu
 * Date: 12.04.2024
 * Time: 21:25
 */
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/links-api")
public class LinkController {
    LinkService linkService;
    private final AuditReader auditReader;

    public LinkController(LinkService linkService, AuditReader auditReader) {
        this.linkService = linkService;
        this.auditReader = auditReader;
    }

    @GetMapping("/links")
    public ResponseEntity<List<LinkDTO>> getAllLinks() {
        List<LinkDTO> links = linkService.getAllLinks();
        return new ResponseEntity<>(links, HttpStatus.OK);
    }

    @GetMapping("/links/active")
    public ResponseEntity<List<LinkDTO>> getAllActive() {
        List<LinkDTO> links = linkService.getAllNotDeletedLinks();
        return new ResponseEntity<>(links, HttpStatus.OK);
    }

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LinkDTO> createLink(
            @RequestParam("name") String name,
            @RequestParam("url") String url,
            @RequestParam("description") String description,
            @RequestParam("availableInFirefox") boolean availableInFirefox,
            @RequestParam("availableInChrome") boolean availableInChrome,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("openInNewWindow") boolean openInNewWindow,
            @RequestPart("image") MultipartFile image) throws IOException, SQLException {

        LinkDTO linkDTO = new LinkDTO();
        linkDTO.setName(name);
        linkDTO.setUrl(url);
        linkDTO.setDescription(description);
        linkDTO.setAvailableInFirefox(availableInFirefox);
        linkDTO.setAvailableInChrome(availableInChrome);
        linkDTO.setActive(isActive);
        linkDTO.setOpenInNewWindow(openInNewWindow);

        if (image != null && !image.isEmpty()) {
            linkDTO.setImage(ImageUtils.compressImage(image.getBytes()));
        }

        LinkDTO savedLink = linkService.saveLink(linkDTO);
        return ResponseEntity.ok(savedLink);
    }

    @PutMapping(path = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LinkDTO> updateLink(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("url") String url,
            @RequestParam("description") String description,
            @RequestParam("availableInFirefox") boolean availableInFirefox,
            @RequestParam("availableInChrome") boolean availableInChrome,
            @RequestParam("active") boolean active,
            @RequestParam("openInNewWindow") boolean openInNewWindow,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException, SQLException {

        LinkDTO linkDTO = linkService.findLinkById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));
        if (linkDTO == null) {
            return ResponseEntity.notFound().build();
        }

        linkDTO.setName(name);
        linkDTO.setUrl(url);
        linkDTO.setDescription(description);
        linkDTO.setAvailableInFirefox(availableInFirefox);
        linkDTO.setAvailableInChrome(availableInChrome);
        linkDTO.setActive(active);
        linkDTO.setOpenInNewWindow(openInNewWindow);

        if (image != null && !image.isEmpty()) {
            linkDTO.setImage(ImageUtils.compressImage(image.getBytes()));
        }

        LinkDTO updatedLink = linkService.updateLink(linkDTO);
        return ResponseEntity.ok(updatedLink);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/link/{id}")
    public ResponseEntity<LinkDTO> getLinkById(@PathVariable Long id) {
        Optional<LinkDTO> linkDTO = linkService.findLinkById(id);
        return linkDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/history/last")
    public ResponseEntity<?> getLastLinkChange(@PathVariable Long id) {
        try {
            LinkDTO lastChange = linkService.getLastChange(id);
            return new ResponseEntity<>(lastChange, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/history/penultimate")
    public ResponseEntity<?> getPenultimateLinkChange(@PathVariable Long id) {
        try {
            LinkDTO penultimateChange = linkService.getPenultimateChange(id);
            return new ResponseEntity<>(penultimateChange, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/revisions")
    public ResponseEntity<List<LinkChangeDTO>> getAllLinkRevisions() {
        List<LinkChangeDTO> revisions = linkService.getAllLinkChanges();
        return new ResponseEntity<>(revisions, HttpStatus.OK);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<LinkChangeDTO>> getLinkHistory(@PathVariable Long id) {
        try {
            List<LinkChangeDTO> history = linkService.getAllRevisionsForLink(id);
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

