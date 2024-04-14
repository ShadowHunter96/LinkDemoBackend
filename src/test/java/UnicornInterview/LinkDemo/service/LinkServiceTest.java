package UnicornInterview.LinkDemo.service;

import UnicornInterview.LinkDemo.factory.LinkFactory;
import UnicornInterview.LinkDemo.model.LinkDTO;
import UnicornInterview.LinkDemo.model.LinkEntity;
import UnicornInterview.LinkDemo.repository.LinkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
/**
 * Created by User: Vu
 * Date: 13.04.2024
 * Time: 11:05
 */
class LinkServiceTest {
    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkService linkService;

    @Test
    void testSaveLink() {

        LinkDTO dto = new LinkDTO();
        dto.setName("Test Link");
        LinkEntity savedEntity = new LinkEntity();
        savedEntity.setId(1L);

        when(linkRepository.save(any(LinkEntity.class))).thenReturn(savedEntity);

        LinkDTO result = linkService.saveLink(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(linkRepository).save(any(LinkEntity.class));
    }

    @Test
    void testUpdateLink_Success() {
        LinkDTO dto = new LinkDTO();
        dto.setId(1L);
        dto.setName("Updated Name");

        LinkEntity existingEntity = new LinkEntity();
        existingEntity.setId(1L);

        when(linkRepository.findById(dto.getId())).thenReturn(Optional.of(existingEntity));
        when(linkRepository.save(any(LinkEntity.class))).thenReturn(existingEntity);

        LinkDTO result = linkService.updateLink(dto);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(linkRepository).save(any(LinkEntity.class));
    }

    @Test
    void testUpdateLink_NotFound() {
        LinkDTO dto = new LinkDTO();
        dto.setId(96L);

        when(linkRepository.findById(dto.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            linkService.updateLink(dto);
        });
    }

    @Test
    void testDeleteExistingLink() {
        Long linkId = 1L;
        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setId(linkId);

        when(linkRepository.findById(linkId)).thenReturn(Optional.of(linkEntity));

        linkService.deleteLink(linkId);

        verify(linkRepository).findById(linkId);
        verify(linkRepository).save(linkEntity);
        LinkFactory.deleteLink(linkEntity);
    }





}