package com.coin;

import com.BaseControllerTest;
import com.TestCreationFactory;
import com.UrlMapping;
import com.coin.model.Coin;
import com.coin.model.dto.CoinDTO;
import com.report.CSVReportService;
import com.report.PdfReportService;
import com.report.ReportServiceFactory;
import com.trade.TradeService;
import com.trade.model.dto.TradeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CoinControllerTest extends BaseControllerTest {

    @InjectMocks
    private CoinController controller;

    @Mock
    private CoinService coinService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @Mock
    private TradeService tradeService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new CoinController(coinService, reportServiceFactory, tradeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allCoins() throws Exception {
        List<CoinDTO> coins = TestCreationFactory.listOf(Coin.class);
        when(coinService.findAll()).thenReturn(coins);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.COINS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(coins));

    }

    /*@Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(ReportType.PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(ReportType.CSV)).thenReturn(csvReportService);
        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.COINS + UrlMapping.EXPORT_REPORT, ReportType.PDF.name()));
        ResultActions csvExport = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.COINS + UrlMapping.EXPORT_REPORT, ReportType.CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));
    }*/

    @Test
    void create() throws Exception {
        CoinDTO reqCoin = CoinDTO.builder().pair(TestCreationFactory.randomString())
                .top((int)Math.random() * 10)
                .build();

        when(coinService.create(reqCoin)).thenReturn(reqCoin);

        ResultActions result = performPostWithRequestBody(UrlMapping.COINS, reqCoin);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqCoin));
    }

    @Test
    void edit() throws Exception {
        long id = TestCreationFactory.randomLong();
        CoinDTO reqCoin = CoinDTO.builder()
                .id(id)
                .pair(TestCreationFactory.randomString())
                .top((int)Math.random() * 10)
                .build();

        when(coinService.edit(id, reqCoin)).thenReturn(reqCoin);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(UrlMapping.COINS + UrlMapping.ENTITY, reqCoin, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqCoin));
    }

    @Test
    void delete() throws Exception {
        long id = TestCreationFactory.randomLong();
        doNothing().when(coinService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(UrlMapping.COINS + UrlMapping.ENTITY, id);
        verify(coinService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }

    @Test
    void tradesForCoin() throws Exception {
        long id = TestCreationFactory.randomLong();
        Set<TradeDTO> tradeDTOs = new HashSet<>(TestCreationFactory.listOf(TradeDTO.class));

        when(tradeService.getTradesForCoin(id)).thenReturn(tradeDTOs);

        ResultActions result = performGetWithPathVariable(UrlMapping.COINS + UrlMapping.ENTITY + UrlMapping.TRADES, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(tradeDTOs));
    }

    /*
    @Test
    void filteredCoins() throws Exception {
        String nameFilter = "name filter";
        CoinFilterRequestDto filters = CoinFilterRequestDto.builder()
                .onlyExcellent(true)
                .name(nameFilter)
                .build();

        final int sortedPage = 4;
        final int sortedPageSize = 100;
        final PageRequest pagination = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, sortColumn));

        Page<CoinDTO> coins = new PageImpl<>(TestCreationFactory.listOf(CoinDTO.class));
        when(coinService.findAllFiltered(filters, pagination)).thenReturn(coins);

        ResultActions result = performGetWithModelAttributeAndParams(UrlMapping.COINS + UrlMapping.FILTERED, Pair.of("filter", filters), pairsFromPagination(pagination));

        verify(coinService, times(1)).findAllFiltered(filters, pagination);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(coins));
    }*/
}