package com.coin;

import com.TestCreationFactory;
import com.coin.model.Coin;
import com.trade.TradeRepository;
import com.user.RoleRepository;
import com.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CoinRepositoryTest {

    @Autowired
    private CoinRepository repository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void beforeEach() {
        tradeRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Coin coinSaved = repository.save(Coin.builder().pair("whatever").build());

        assertNotNull(coinSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Coin.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Coin> coins = TestCreationFactory.listOf(Coin.class);
        repository.saveAll(coins);
        List<Coin> all = repository.findAll();
        assertEquals(coins.size(), all.size());
    }

    /*@Test
    public void testSimpleLikeQuery() {
        final Coin coin1 = Coin.builder()
                .pair("Stewie")
                .top(22)
                .build();

        repository.save(coin1);

        final List<Coin> res1 = repository.findAllByNameLikeOrDescriptionLike("Stewie",
                "noooope");
        assertFalse(res1.isEmpty());
        assertEquals(1, res1.size());
        assertEquals(coin1.getId(), res1.get(0).getId());

        final List<Coin> res2 = repository.findAllByNameLikeOrDescriptionLike("%tew%",
                "noooope");
        assertFalse(res2.isEmpty());
        assertEquals(1, res2.size());
        assertEquals(coin1.getId(), res2.get(0).getId());

        // what if we wanted sorting by default on these queries?


        // what if we need more complex queries like ... give me all the coins with at least 1 excellent review?
    }*/

    /*@Test
    void testSortingLikeQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Coin.builder()
                            .name(title)
                            .build());
                }
            }
        }

        final List<Coin> bCoinsSortedDesc = repository.findAllByNameLikeOrderByNameDesc("%b%");
        final Coin firstCoin = bCoinsSortedDesc.get(0);
        bCoinsSortedDesc.remove(0);

        assertTrue(
                bCoinsSortedDesc.stream().anyMatch(coin ->
                        firstCoin.getName().compareTo(coin.getName()) > 0
                )
        );

        // what if you also want to search ascending...?
        final List<Coin> bCoinsSortedAsc = repository.findAllByNameLike("%b%", Sort.by(ASC, "name"));
        final Coin firstCoinAsc = bCoinsSortedDesc.get(0);
        bCoinsSortedAsc.remove(0);

        assertTrue(
                bCoinsSortedAsc.stream().anyMatch(coin ->
                        firstCoinAsc.getName().compareTo(coin.getName()) < 0
                )
        );

        // what if now, I only want to get the first 10 such results, not 1000+ ?
    }*/

    /*@Test
    void testPaginationQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Coin.builder()
                            .name(title)
                            .build());
                }
            }
        }

        final int page = 1;
        final int pageSize = 10;
        final PageRequest pageable = PageRequest.of(page, pageSize);
        final Page<Coin> pagedResult = repository.findAllByNameLike("%b%", pageable);

        assertTrue(pagedResult.hasContent());
        assertEquals(pageSize, pagedResult.getNumberOfElements());
        assertEquals(page, pagedResult.getNumber());
//    assertEquals(1951, pagedResult.getTotalElements());

        // what if now we'd also want to add sorting?

        final int sortedPage = 4;
        final int sortedPageSize = 100;
        final PageRequest first100AscByName = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "name"));
        final Page<Coin> pagedResultSortAsc = repository.findAllByNameLike("%b%", first100AscByName);
        assertTrue(pagedResultSortAsc.hasContent());
        assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
        assertEquals(sortedPage, pagedResultSortAsc.getNumber());

        final List<Coin> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
        assertEquals(sortedPageSize, pagedResultSortedContent.size());

        final Coin firstCoinAsc = pagedResultSortedContent.get(0);
        pagedResultSortedContent.remove(0);

        assertTrue(
                pagedResultSortedContent.stream().anyMatch(coin ->
                        firstCoinAsc.getName().compareTo(coin.getName()) < 0
                )
        );
    }*/

    /*@Test
    void testSimpleSpecificationQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Coin.builder()
                            .name(title)
                            .build());
                }
            }
        }

        final List<Coin> coins1 = repository.findAll(similarNames("%b%"));
        assertTrue(coins1.size() > 1000);

        final String newDescription = "New and flashy";
        repository.save(
                Coin.builder()
                        .name("Laptop")
                        .description(newDescription)
                        .dateCreated(LocalDateTime.now())
                        .build()
        );

        repository.save(
                Coin.builder()
                        .name("Laptop")
                        .description("Oldie goldie")
                        .dateCreated(LocalDateTime.now().minusYears(1))
                        .build()
        );

        final List<Coin> latestLaptops =
                repository.findAll(
                        createdAfter(LocalDateTime.now().minusMonths(3)).and(similarNames("%Lapt%"))
                );
        assertEquals(1, latestLaptops.size());
        assertEquals(newDescription, latestLaptops.get(0).getDescription());
    }*/

    /*@Test
    void testComplicatedSpecificationQuery() {

        final String qualityLaptop = "Quality laptop 1";
        final Coin excellent1 = repository.save(Coin.builder()
                .name(qualityLaptop)
                .dateCreated(LocalDateTime.now().minusMonths(3))
                .build());

        reviewRepository.save(Review.builder()
                .coin(excellent1)
                .text("Blanao")
                .rating(Rating.EXCELENT)
                .build());

        reviewRepository.save(Review.builder()
                .coin(excellent1)
                .text("SuP3r mega")
                .rating(Rating.EXCELENT)
                .build());


        final String qualityMac = "Quality mac 1";
        final Coin excellent2 = repository.save(Coin.builder()
                .name(qualityMac)
                .dateCreated(LocalDateTime.now().minusMonths(2))
                .build());

        reviewRepository.save(Review.builder()
                .coin(excellent2)
                .text("Incredibil")
                .rating(Rating.EXCELENT)
                .build());

        final Coin average1 = repository.save(Coin.builder()
                .name("Average laptop 2")
                .dateCreated(LocalDateTime.now().minusMonths(3))
                .build());

        reviewRepository.save(Review.builder()
                .coin(average1)
                .text("Incredibil")
                .rating(Rating.AVERAGE)
                .build());

        final List<Coin> excellentCoins = repository.findAll(onlyExcellentRated());
        assertEquals(2, excellentCoins.size());
        assertEquals(qualityLaptop, excellentCoins.get(0).getName());
        assertEquals(qualityMac, excellentCoins.get(1).getName());

        final List<Coin> onlyExcellentMacs = repository.findAll(onlyExcellentRated().and(similarNames("%mac%")));
        assertEquals(1, onlyExcellentMacs.size());
        assertEquals(qualityMac, onlyExcellentMacs.get(0).getName());
    }*/

    /*@Test
    void testWildlyComplicatedQuery() {
        buildRoles();
        final User admin = userRepository.save(User.builder()
                .username("admin")
                .email("admin@users.com")
                .password("stronk")
                .roles(Set.of(roleRepository.findByName(ERole.ADMIN).get()))
                .build());

        final User customer = userRepository.save(User.builder()
                .username("customer")
                .email("customer@users.com")
                .password("stronk")
                .roles(Set.of(roleRepository.findByName(ERole.CUSTOMER).get()))
                .build());

        final User manager = userRepository.save(User.builder()
                .username("manager")
                .email("manager@users.com")
                .password("stronk")
                .roles(Set.of(roleRepository.findByName(ERole.MANAGER).get()))
                .build());

        final String laptopName = "laptop";
        final Coin laptop = repository.save(Coin.builder()
                .name(laptopName)
                .build());

        final Coin mac = repository.save(Coin.builder()
                .name("mac")
                .build());

        final String webcamName = "webcam";
        final Coin webcam = repository.save(Coin.builder()
                .name(webcamName)
                .build());

        addRandomReview(laptop, admin);
        addRandomReview(laptop, manager);
        addRandomReview(mac, customer);
        addRandomReview(webcam, admin);

        final List<Coin> onlyReviewedByAdmins = repository.findAll(withAdministratorReviews());
        assertEquals(2, onlyReviewedByAdmins.size());
        assertEquals(laptopName, onlyReviewedByAdmins.get(0).getName());
    }

    private void addRandomReview(Coin coin, User user) {
        final Rating[] ratings = Rating.values();
        reviewRepository.save(
                Review.builder()
                        .text(TestCreationFactory.randomString())
                        .rating(ratings[new Random().nextInt(ratings.length - 1)])
                        .coin(coin)
                        .user(user)
                        .build()
        );
    }

    private void buildRoles() {
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }
    }*/

}
