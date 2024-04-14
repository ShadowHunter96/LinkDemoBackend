package UnicornInterview.LinkDemo.service;

import UnicornInterview.LinkDemo.factory.LinkFactory;
import UnicornInterview.LinkDemo.model.LinkChangeDTO;
import UnicornInterview.LinkDemo.model.LinkDTO;
import UnicornInterview.LinkDemo.model.LinkEntity;
import UnicornInterview.LinkDemo.repository.LinkRepository;
import UnicornInterview.LinkDemo.utils.ImageUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.history.Revision;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

/**
 * Created by User: Vu
 * Date: 12.04.2024
 * Time: 21:24
 */
@Service
public class LinkService {
    private LinkRepository linkRepository;
    private final RevisionRepository<LinkEntity, Long, Integer> revisionRepository;

    private AuditReader auditReader;

    @PersistenceContext
    private EntityManager entityManager;



    public LinkService(LinkRepository linkRepository, RevisionRepository revisionRepository) {
        this.linkRepository = linkRepository;
        this.revisionRepository= revisionRepository;
    }

    @Transactional(readOnly = true)
    public List<LinkDTO> getAllNotDeletedLinks() {
        List<LinkEntity> linkEntities = linkRepository.findByDeletedFalse();
        List<LinkDTO> linkDTOS = linkEntities.stream().map(linkEntity -> {
            LinkDTO linkDTO = LinkFactory.fromEntity(linkEntity);
            if (linkEntity.getImage() != null) {
                try {
                    byte[] decompressedImage = ImageUtils.decompressImage(linkEntity.getImage());
                    String imageBase64 = Base64.getEncoder().encodeToString(decompressedImage);
                    linkDTO.setImageBase64(imageBase64);
                } catch (DataFormatException | IOException e) {
                    throw new RuntimeException("Failed to decompress image", e);
                }
            }
            return linkDTO;
        }).collect(Collectors.toList());
        return linkDTOS;
    }

    public List<LinkDTO> getAllLinks() {
        List<LinkEntity> linkEntities = linkRepository.findAll();
        List<LinkDTO> linkDTOS = linkEntities.stream().map(linkEntity -> {
            LinkDTO linkDTO = LinkFactory.fromEntity(linkEntity);
            if (linkEntity.getImage() != null) {
                try {
                    byte[] decompressedImage = ImageUtils.decompressImage(linkEntity.getImage());
                    String imageBase64 = Base64.getEncoder().encodeToString(decompressedImage);
                    linkDTO.setImageBase64(imageBase64);
                } catch (DataFormatException | IOException e) {
                    throw new RuntimeException("Failed to decompress image", e);
                }
            }
            return linkDTO;
        }).collect(Collectors.toList());
        return linkDTOS;
    }



    public LinkDTO saveLink(LinkDTO dto) {

        LinkEntity entity = new LinkEntity();
        LinkFactory.fillEntity(entity, dto);
        LinkEntity savedEntity = linkRepository.save(entity);
        return LinkFactory.fromEntity(savedEntity);
    }

    public LinkDTO updateLink(LinkDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Link ID must not be null for update");
        }
        Optional<LinkEntity> existingEntityOpt = linkRepository.findById(dto.getId());
        if (!existingEntityOpt.isPresent()) {
            throw new EntityNotFoundException("Link with ID " + dto.getId() + " not found");
        }
        LinkEntity existingEntity = existingEntityOpt.get();
        LinkFactory.fillEntity(existingEntity, dto);
        LinkEntity savedEntity = linkRepository.save(existingEntity);
        return LinkFactory.fromEntity(savedEntity);
    }




    @Transactional
    public void deleteLink(Long id) {
        Optional<LinkEntity> linkEntityOptional = linkRepository.findById(id);
        if (linkEntityOptional.isPresent()) {
            LinkEntity linkEntity = linkEntityOptional.get();
            LinkFactory.deleteLink(linkEntity);
            linkRepository.save(linkEntity);
        } else {
            throw new RuntimeException("Link with id: " + id + " not found.");
        }
    }
    public Optional<LinkDTO> findLinkById(Long id){
        Optional<LinkEntity> entityOptional = linkRepository.findById(id);
        if (entityOptional.isPresent()) {
            LinkEntity entity = entityOptional.get();
            LinkDTO dto = LinkFactory.fromEntity(entity);
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    public LinkDTO getLastChange(Long linkId) {
        Revision<Integer, LinkEntity> lastRevision = revisionRepository.findLastChangeRevision(linkId)
                .orElseThrow(() -> new EntityNotFoundException("Link with ID " + linkId + " not found or no changes have been made."));
        LinkEntity lastChangedEntity = lastRevision.getEntity();
        return LinkFactory.fromEntity(lastChangedEntity);
    }

    public LinkDTO getPenultimateChange(Long linkId) {
        List<Revision<Integer, LinkEntity>> revisions = revisionRepository.findRevisions(linkId).getContent();
        if (revisions.size() < 2) {
            throw new EntityNotFoundException("Less than two revisions found for link with ID " + linkId);
        }
        LinkEntity penultimateRevisionEntity = revisions.get(revisions.size() - 2).getEntity();
        return LinkFactory.fromEntity(penultimateRevisionEntity);
    }

    public List<LinkChangeDTO> getAllLinkChanges() {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(LinkEntity.class, false, true);
        List<Object[]> result = query.getResultList();

        return result.stream().map(this::mapRevisionToLinkChangeDTO).collect(Collectors.toList());
    }

    private LinkChangeDTO mapRevisionToLinkChangeDTO(Object[] tuple) {
        LinkEntity linkEntity = (LinkEntity) tuple[0];
        DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) tuple[1];
        RevisionType revisionType = (RevisionType) tuple[2];

        LinkChangeDTO dto = new LinkChangeDTO();
        dto.setId(linkEntity.getId());
        dto.setName(linkEntity.getName());
        dto.setUrl(linkEntity.getUrl());
        dto.setImage(linkEntity.getImage());
        dto.setImageBase64(linkEntity.getImageBase64());
        dto.setDescription(linkEntity.getDescription());
        dto.setAvailableInFirefox(linkEntity.isAvailableInFirefox());
        dto.setAvailableInChrome(linkEntity.isAvailableInChrome());
        dto.setActive(linkEntity.isActive());
        dto.setOpenInNewWindow(linkEntity.isOpenInNewWindow());
        dto.setDeleted(linkEntity.isDeleted());
        dto.setRevisionDate(revisionEntity.getRevisionDate());
        dto.setRevisionType(revisionType.ordinal());
        return dto;
    }

    public List<LinkChangeDTO> getAllRevisionsForLink(Long linkId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(LinkEntity.class, false, true);
        query.add(AuditEntity.id().eq(linkId));
        List<Object[]> revisions = query.getResultList();
        return revisions.stream().map(this::mapRevisionToLinkChangeDTO).collect(Collectors.toList());
    }





}
