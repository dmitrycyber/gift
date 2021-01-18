package by.mjs.ivoninsky.gift.dao.impl;

import by.mjs.ivoninsky.gift.dao.exception.GiftNotFoundException;
import by.mjs.ivoninsky.gift.dao.exception.TagNotFoundException;
import by.mjs.ivoninsky.gift.model.entity.GiftCertificateEntity;
import by.mjs.ivoninsky.gift.model.entity.TagEntity;
import junit.framework.TestCase;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TagDaoImplTest {
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private TagDaoImpl tagDao;

    @Before
    public void setUp() {
        dataSource = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        tagDao = new TagDaoImpl(jdbcTemplate);
    }

    @Test
    public void testFindAllTags() {
        List<TagEntity> allTags = tagDao.findAllTags();
        assertNotNull(allTags);
        assertEquals(5, allTags.size());
    }

    @Test
    public void testFindTagById() {
        TagEntity expectedEntity = TagEntity.builder()
                .id(1L)
                .name("name1").build();

        TagEntity actualEntity = tagDao.findTagById(1L);

        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    public void successfulFindTagByName() {
        String expectedName = "name1";

        List<TagEntity> tagByName = tagDao.findTagByName(expectedName);
        int size = tagByName.size();
        assertEquals(1, size);
    }

    @Test
    public void negativeFindTagByName() {
        String expectedName = "name12";
        List<TagEntity> tagByName = tagDao.findTagByName(expectedName);
        int size = tagByName.size();
        assertEquals(0, size);
    }


    @Test
    public void successfulTestFindTagByPartName() {
        String expectedName = "name";

        List<TagEntity> tagByName = tagDao.findTagByPartName(expectedName);
        int size = tagByName.size();
        assertEquals(5, size);
    }

    @Test
    public void negativeTestFindTagByPartName() {
        String expectedName = "notName";

        List<TagEntity> tagByName = tagDao.findTagByPartName(expectedName);
        int size = tagByName.size();
        assertEquals(0, size);
    }


    @Test
    public void testCreateTag() {
        TagEntity entityToSave = TagEntity.builder()
                .name("testName").build();

        TagEntity savedEntity = tagDao.createTag(entityToSave);
        assertNotNull(savedEntity);
        assertEquals(6, tagDao.findAllTags().size());
    }


    @Test
    public void testDeleteTagById() {
        tagDao.deleteTagById(1L);

        assertEquals(4, tagDao.findAllTags().size());
    }

    @Test(expected = TagNotFoundException.class)
    public void giftNotFoundExceptionCheck(){
        tagDao.findTagById(6L);
    }
}