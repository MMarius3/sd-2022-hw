package com.lab4.demo.item;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Item;

import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.randomString;
import static com.lab4.demo.item.ItemSpecifications.*;
import static com.lab4.demo.user.model.ERole.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testMock() {
        Item itemSaved = repository.save(Item.builder().title("whatever").build());

        assertNotNull(itemSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Item.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        repository.saveAll(items);
        List<Item> all = repository.findAll();
        assertEquals(items.size(), all.size());
    }

  /*  @Test
    public void testSimpleLikeQuery() {
        final Item item1 = Item.builder()
                .title("Stewie")
                .author("Something, something, something ... dark side.")
                .build();

        repository.save(item1);

        final List<Item> res1 = repository.findAllByNameLikeOrDescriptionLike("Stewie",
                "noooope");
        assertFalse(res1.isEmpty());
        assertEquals(1, res1.size());
        assertEquals(item1.getId(), res1.get(0).getId());

        final List<Item> res2 = repository.findAllByNameLikeOrDescriptionLike("%tew%",
                "noooope");
        assertFalse(res2.isEmpty());
        assertEquals(1, res2.size());
        assertEquals(item1.getId(), res2.get(0).getId());

        // what if we wanted sorting by default on these queries?


        // what if we need more complex queries like ... give me all the items with at least 1 excellent review?
    }*/
/*
    @Test
    void testSortingLikeQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Item.builder()
                            .title(title)
                            .build());
                }
            }
        }
/*
        final List<Item> bItemsSortedDesc = repository.findAllByNameLikeOrderByNameDesc("%b%");
        final Item firstItem = bItemsSortedDesc.get(0);
        bItemsSortedDesc.remove(0);

        assertTrue(
                bItemsSortedDesc.stream().anyMatch(item ->
                        firstItem.getName().compareTo(item.getName()) > 0
                )
        );

        // what if you also want to search ascending...?
        final List<Item> bItemsSortedAsc = repository.findAllByNameLike("%b%", Sort.by(ASC, "name"));
        final Item firstItemAsc = bItemsSortedDesc.get(0);
        bItemsSortedAsc.remove(0);

        assertTrue(
                bItemsSortedAsc.stream().anyMatch(item ->
                        firstItemAsc.getName().compareTo(item.getName()) < 0
                )
        );

        // what if now, I only want to get the first 10 such results, not 1000+ ?
    }

    @Test
    void testPaginationQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Item.builder()
                            .title(title)
                            .build());
                }
            }
        }

        final int page = 1;
        final int pageSize = 10;
        final PageRequest pageable = PageRequest.of(page, pageSize);
        final Page<Item> pagedResult = repository.findAllByNameLike("%b%", pageable);

        assertTrue(pagedResult.hasContent());
        assertEquals(pageSize, pagedResult.getNumberOfElements());
        assertEquals(page, pagedResult.getNumber());
//    assertEquals(1951, pagedResult.getTotalElements());

        // what if now we'd also want to add sorting?

        final int sortedPage = 4;
        final int sortedPageSize = 100;
        final PageRequest first100AscByName = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "name"));
        final Page<Item> pagedResultSortAsc = repository.findAllByNameLike("%b%", first100AscByName);
        assertTrue(pagedResultSortAsc.hasContent());
        assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
        assertEquals(sortedPage, pagedResultSortAsc.getNumber());

        final List<Item> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
        assertEquals(sortedPageSize, pagedResultSortedContent.size());

        final Item firstItemAsc = pagedResultSortedContent.get(0);
        pagedResultSortedContent.remove(0);

        assertTrue(
                pagedResultSortedContent.stream().anyMatch(item ->
                        firstItemAsc.getName().compareTo(item.getName()) < 0
                )
        );
    }
*/
    /*
    @Test
    void testSimpleSpecificationQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Item.builder()
                            .title(title)
                            .build());
                }
            }
        }

        final List<Item> items1 = repository.findAll(similarNames("%b%"));
        assertTrue(items1.size() > 1000);

        final String newDescription = "New and flashy";
        repository.save(
                Item.builder()
                        .title("Laptop")
                        .author(newDescription)
                        .build()
        );

        repository.save(
                Item.builder()
                        .title("Laptop")
                        .author("Oldie goldie")
                        .build()
        );

        final List<Item> latestLaptops =
                repository.findAll(
                        createdAfter(LocalDateTime.now().minusMonths(3)).and(similarNames("%Lapt%"))
                );
        assertEquals(1, latestLaptops.size());
        assertEquals(newDescription, latestLaptops.get(0).getAuthor());
    }
*/
    /*
    @Test
    void testComplicatedSpecificationQuery() {

        final String qualityLaptop = "Quality laptop 1";
        final Item excellent1 = repository.save(Item.builder()
                .title(qualityLaptop)
                .build());



        final String qualityMac = "Quality mac 1";
        final Item excellent2 = repository.save(Item.builder()
                .title(qualityMac)
                .build());

        final Item average1 = repository.save(Item.builder()
                .title("Average laptop 2")
                .build());


        final List<Item> excellentItems = repository.findAll(onlyExcellentRated());
        assertEquals(2, excellentItems.size());
        assertEquals(qualityLaptop, excellentItems.get(0).getName());
        assertEquals(qualityMac, excellentItems.get(1).getName());

        final List<Item> onlyExcellentMacs = repository.findAll(onlyExcellentRated().and(similarNames("%mac%")));
        assertEquals(1, onlyExcellentMacs.size());
        assertEquals(qualityMac, onlyExcellentMacs.get(0).getName());
    }*/

    /*
    @Test
    void testWildlyComplicatedQuery() {
        buildRoles();
        final User admin = userRepository.save(User.builder()
                .username("admin")
                .email("admin@users.com")
                .password("stronk")
                .roles(Set.of(roleRepository.findByName(ADMIN).get()))
                .build());

        final User customer = userRepository.save(User.builder()
                .username("customer")
                .email("customer@users.com")
                .password("stronk")
                .roles(Set.of(roleRepository.findByName(CUSTOMER).get()))
                .build());

        final User manager = userRepository.save(User.builder()
                .username("manager")
                .email("manager@users.com")
                .password("stronk")
                .roles(Set.of(roleRepository.findByName(MANAGER).get()))
                .build());

        final String laptopName = "laptop";
        final Item laptop = repository.save(Item.builder()
                .title(laptopName)
                .build());

        final Item mac = repository.save(Item.builder()
                .title("mac")
                .build());

        final String webcamName = "webcam";
        final Item webcam = repository.save(Item.builder()
                .title(webcamName)
                .build());

        addRandomReview(laptop, admin);
        addRandomReview(laptop, manager);
        addRandomReview(mac, customer);
        addRandomReview(webcam, admin);

        final List<Item> onlyReviewedByAdmins = repository.findAll(withAdministratorReviews());
        assertEquals(2, onlyReviewedByAdmins.size());
        assertEquals(laptopName, onlyReviewedByAdmins.get(0).getName());
    }
*/


    private void buildRoles() {
        for (ERole value : values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }
    }

}
