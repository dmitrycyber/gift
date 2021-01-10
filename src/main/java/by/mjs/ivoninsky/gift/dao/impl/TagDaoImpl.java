package by.mjs.ivoninsky.gift.dao.impl;

import by.mjs.ivoninsky.gift.dao.TagDao;
import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.dao.exception.TagNotFoundException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagDaoImpl implements TagDao {
    private final List<TagDto> tagDtoList = new ArrayList<>();

    {
        for (long i = 1; i < 11; i++) {
            tagDtoList.add(TagDto.builder()
                    .id(i)
                    .name("name-" + i).build());
        }
    }


    @Override
    public List<TagDto> findAllTags() throws DaoException {
        return tagDtoList;
    }

    @Override
    public TagDto findTagById(Long tagId) throws DaoException {
        return tagDtoList.stream()
                .filter(tagDto -> tagDto.getId().equals(tagId))
                .findAny().orElseThrow(TagNotFoundException::new);
    }

    @Override
    public List<TagDto> findTagByName(String tagName) throws DaoException {
        List<TagDto> collect = tagDtoList.stream()
                .filter(tagDto -> tagDto.getName().contains(tagName))
                .collect(Collectors.toList());
        if (collect.isEmpty()){
            throw new TagNotFoundException();
        }
        return collect;
    }

    @Override
    public void createTag(List<TagDto> giftCertificateDtoList) throws DaoException {
        tagDtoList.addAll(giftCertificateDtoList);
    }

    @Override
    public void deleteTagById(Long tagId) throws DaoException {
        TagDto tagById = tagDtoList.stream()
                .filter(tagDto -> tagDto.getId().equals(tagId))
                .findAny().orElseThrow(TagNotFoundException::new);

        tagDtoList.remove(tagById);
    }

    @Override
    public Set<GiftCertificateDto> findGiftsByTagId(Long tagId) throws DaoException {
        return null;
    }
}
