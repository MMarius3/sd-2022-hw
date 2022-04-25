package com.assignment2.book_store.service;

import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.marketplace.MarketplaceApproveRequestDto;
import com.assignment2.book_store.data.dto.marketplace.MarketplaceResponseDto;
import com.assignment2.book_store.data.dto.premarketplace.PreMarketplaceCreateOfferDto;
import com.assignment2.book_store.data.dto.premarketplace.PreMarketplaceResponseDto;
import com.assignment2.book_store.data.entity.jpa.*;
import com.assignment2.book_store.data.map.BookMapper;
import com.assignment2.book_store.data.map.MarketplaceMapper;
import com.assignment2.book_store.data.map.PreMarketplaceMapper;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.repository.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MarketplaceService {

    private final PremarketplaceRepository premarketplaceRepository;
    private final MarketplaceRepository marketplaceRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final QualityRepository qualityRepository;
    private final PreMarketplaceMapper preMarketplaceMapper;
    private final MarketplaceMapper marketplaceMapper;
    private final BookMapper bookMapper;

    public Page<PreMarketplaceResponseDto> getPreMarketplaceOffers(Pageable pageable) {
        return premarketplaceRepository.findAll(pageable)
                .map(preMarketplaceMapper::entityToDto);
    }

    public PreMarketplaceResponseDto getPreMarketplaceById(Long id) {
        return premarketplaceRepository.findById(id)
                .map(preMarketplaceMapper::entityToDto)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Id is not matching"));
    }

    public Page<MarketplaceResponseDto> getMarketplaceOffers(Pageable pageable) {
        return marketplaceRepository.findAll(pageable)
                .map(marketplaceMapper::entityToDto);
    }

    public MarketplaceResponseDto getMarketplaceOfferById(Long id) {
        return marketplaceRepository.findById(id)
                .map(marketplaceMapper::entityToDto)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Id is not matching"));
    }

    public PreMarketplaceResponseDto createOffer(String username, PreMarketplaceCreateOfferDto offer) {
        Book toSell = bookRepository.findById(offer.getBookId())
                .orElseThrow(() -> new RuntimeException("Book does not exist!"));
        if (!toSell.getOwner().getUsername().equals(username)) {
            throw ErrorFactory.getError(HttpStatus.FORBIDDEN, "You don't have the access to sell this book");
        }
        if (!(bookRepository.existsById(offer.getBookId()) && qualityRepository.existsById(offer.getQualityId()))) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Book id or quality id doesn't exist");
        }
        Premarketplace createdOffer = preMarketplaceMapper.createOffer(offer);
        return preMarketplaceMapper.entityToDto(fillPreMarketplaceFields(premarketplaceRepository.save(createdOffer)));
    }

    @Transactional
    public MarketplaceResponseDto approveSelling(String username, MarketplaceApproveRequestDto approveRequest) {
        Premarketplace preMarketplaceOffer = premarketplaceRepository.findById(approveRequest.getPremarketplaceId())
                .orElseThrow(() -> new RuntimeException("PreMarketplace offer doesn't exist!"));
        Marketplace marketplaceOffer = new Marketplace();
        User admin = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Something went wrong"));
        marketplaceOffer.setAdmin(admin);
        marketplaceOffer.setBook(preMarketplaceOffer.getBook());
        ofNullable(approveRequest.getPrice())
                .ifPresentOrElse(marketplaceOffer::setPrice, () -> marketplaceOffer.setPrice(preMarketplaceOffer.getPrice()));
        ofNullable(approveRequest.getQualityId())
                .ifPresentOrElse(qualityId -> {
                    qualityRepository.findById(qualityId)
                            .ifPresent(marketplaceOffer::setQuality);
                }, () -> marketplaceOffer.setQuality(preMarketplaceOffer.getQuality()));
        premarketplaceRepository.deleteById(approveRequest.getPremarketplaceId());
        return marketplaceMapper.entityToDto(marketplaceRepository.save(marketplaceOffer));
    }

    @Transactional
    public BookResponseDto buy(String username, Long marketplaceOfferId) {
        User buyer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username"));
        Marketplace marketplaceOffer = marketplaceRepository.getById(marketplaceOfferId);
        Book book = marketplaceOffer.getBook();
        User seller = book.getOwner();

        if (buyer.getAccountBalance() < marketplaceOffer.getPrice()) {
            throw new RuntimeException("Insufficient funds!");
        }

        seller.setAccountBalance(seller.getAccountBalance() + marketplaceOffer.getPrice());
        buyer.setAccountBalance(buyer.getAccountBalance() - marketplaceOffer.getPrice());
        book.setOwner(buyer);

        marketplaceRepository.deleteById(marketplaceOfferId);
        return bookMapper.entityToDto(book);
    }

    private Premarketplace fillPreMarketplaceFields(Premarketplace original) {
        original.setBook(bookRepository.getById(original.getBook().getId()));
        original.setQuality(qualityRepository.getById(original.getQuality().getId()));
        return original;
    }

}
