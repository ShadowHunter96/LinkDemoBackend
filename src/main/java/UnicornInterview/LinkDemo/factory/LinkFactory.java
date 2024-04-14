package UnicornInterview.LinkDemo.factory;

import UnicornInterview.LinkDemo.model.LinkDTO;
import UnicornInterview.LinkDemo.model.LinkEntity;

/**
 * Created by User: Vu
 * Date: 12.04.2024
 * Time: 21:22
 */
public class LinkFactory {
    public LinkFactory() {
    }

    public static LinkDTO fromEntity(LinkEntity link ){
        LinkDTO dto = new LinkDTO();
        dto.setId(link.getId());
        dto.setName(link.getName());
        dto.setUrl(link.getUrl());
        dto.setImage(link.getImage());
        dto.setImageBase64(link.getImageBase64());
        dto.setDescription(link.getDescription());
        dto.setAvailableInFirefox(link.isAvailableInFirefox());
        dto.setAvailableInChrome(link.isAvailableInChrome());
        dto.setActive(link.isActive());
        dto.setOpenInNewWindow(link.isOpenInNewWindow());
        dto.setDeleted(link.isDeleted());
        return dto;
    }

    public static void fillEntity(LinkEntity link, LinkDTO dto){
        link.setId(dto.getId());
        link.setName(dto.getName());
        link.setUrl(dto.getUrl());
        link.setImage(dto.getImage());
        link.setImageBase64(dto.getImageBase64());
        link.setDescription(dto.getDescription());
        link.setAvailableInFirefox(dto.isAvailableInFirefox());
        link.setAvailableInChrome(dto.isAvailableInChrome());
        link.setActive(dto.isActive());
        link.setOpenInNewWindow(dto.isOpenInNewWindow());
        link.setDeleted(dto.isDeleted());
    }

    public static void deleteLink(LinkEntity link) {
        link.setDeleted(true);
    }


}
