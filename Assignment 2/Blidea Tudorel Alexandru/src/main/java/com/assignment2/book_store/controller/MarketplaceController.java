package com.assignment2.book_store.controller;


import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.marketplace.MarketplaceApproveRequestDto;
import com.assignment2.book_store.data.dto.marketplace.MarketplaceResponseDto;
import com.assignment2.book_store.data.dto.premarketplace.PreMarketplaceCreateOfferDto;
import com.assignment2.book_store.data.dto.premarketplace.PreMarketplaceResponseDto;
import com.assignment2.book_store.security.details.UserDetailsImpl;
import com.assignment2.book_store.service.MarketplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.assignment2.book_store.UrlMapping.MARKETPLACE;

@RestController
@RequestMapping(MARKETPLACE)
@RequiredArgsConstructor
public class MarketplaceController {

    private final MarketplaceService marketplaceService;

    @GetMapping("/pre")
    public Page<PreMarketplaceResponseDto> getPreMarketplaceOffers(@PageableDefault Pageable pageable) {
        return marketplaceService.getPreMarketplaceOffers(pageable);
    }

    @GetMapping("/pre/{id}")
    public PreMarketplaceResponseDto getPreMarketplaceOfferById(@PathVariable Long id) {
        return marketplaceService.getPreMarketplaceById(id);
    }

    @GetMapping("/")
    public Page<MarketplaceResponseDto> getMarketplaceOffers(@PageableDefault Pageable pageable) {
        return marketplaceService.getMarketplaceOffers(pageable);
    }

    @GetMapping("/{id}")
    public MarketplaceResponseDto getMarketplaceOfferById(@PathVariable Long id) {
        return marketplaceService.getMarketplaceOfferById(id);
    }

    @PostMapping("/pre")
    @PreAuthorize("isAuthenticated")
    public PreMarketplaceResponseDto createOffer(Authentication authentication, @RequestBody PreMarketplaceCreateOfferDto createOfferRequest) {
        return marketplaceService.createOffer(getUsername(authentication), createOfferRequest);
    }

    @PutMapping("/offer/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MarketplaceResponseDto approveOffer(Authentication authentication, @RequestBody MarketplaceApproveRequestDto approveRequest) {
        return marketplaceService.approveSelling(getUsername(authentication), approveRequest);
    }

    @PutMapping("/buy/{id}")
    @PreAuthorize("isAuthenticated")
    public BookResponseDto buy(Authentication authentication, @PathVariable(name = "id") Long offerId) {
        return marketplaceService.buy(getUsername(authentication), offerId);
    }

    private String getUsername(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
    }


}
