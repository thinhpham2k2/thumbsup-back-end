package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.chart.SaleDTO;
import com.thumbsup.thumbsup.dto.chart.TitleAdminDTO;
import com.thumbsup.thumbsup.dto.chart.TitleDTO;
import com.thumbsup.thumbsup.entity.Order;
import com.thumbsup.thumbsup.entity.TransactionOrder;
import com.thumbsup.thumbsup.mapper.AdvertisementMapper;
import com.thumbsup.thumbsup.repository.*;
import com.thumbsup.thumbsup.service.interfaces.IChartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChartService implements IChartService {

    private final StoreRepository storeRepository;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final AdvertisementRepository advertisementRepository;

    private final TransactionOrderRepository transactionOrderRepository;

    @Override
    public TitleAdminDTO getTitle() {
        LocalDate now = LocalDate.now();
        List<TransactionOrder> transList = transactionOrderRepository.findAllByStatusAndDateCreatedBetween(true,
                now.atStartOfDay(),
                now.plusDays(1).atStartOfDay());

        return new TitleAdminDTO(orderRepository.countAllByStatus(true),
                customerRepository.countAllByStatus(true),
                storeRepository.countAllByStatus(true),
                BigDecimal.valueOf((transList.stream()
                        .mapToDouble(t -> Double.parseDouble(t.getAmount().toString())).sum() / 0.95) * 0.05)
                        .setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public TitleDTO getTitleByStoreId(Long storeId) {
        return new TitleDTO(orderRepository.countAllByStatusAndStoreId(true, storeId),
                advertisementRepository.countClickAdsByStoreId(true, storeId),
                storeRepository.getBalanceByStoreId(true, storeId),
                AdvertisementMapper.INSTANCE.toDTO
                        (advertisementRepository.
                                findFirstByStatusAndDateExpiredAfter(true, LocalDateTime.now()).orElse(null)));
    }

    @Override
    public List<SaleDTO> getSale(String sort, LocalDateTime fromDate, LocalDateTime toDate) {
        if (toDate.isBefore(fromDate)) throw new InvalidParameterException("Invalid date");

        List<SaleDTO> result = new ArrayList<>();
        List<TransactionOrder> transList =
                transactionOrderRepository.findAllByStatusAndDateCreatedBetween(true, fromDate, toDate);
        List<Order> orderList =
                orderRepository.findAllByStatusAndDateCreatedBetween(true, fromDate, toDate);

        while (!(fromDate.isAfter(toDate))) {
            LocalDateTime finalFromDate = fromDate;
            result.add(new SaleDTO(fromDate.toLocalDate(),
                    BigDecimal.valueOf(transList.stream().filter(
                            t -> t.getDateCreated().toLocalDate().isEqual(finalFromDate.toLocalDate())).mapToDouble(
                            t -> Double.parseDouble(t.getAmount().toString())).sum()),
                    orderList.stream().filter(
                            o -> o.getDateCreated().toLocalDate().isEqual(finalFromDate.toLocalDate())).count()));

            fromDate = fromDate.plusDays(1);
        }

        if (sort.equals("asc")) {
            result = result.stream().sorted(Comparator.comparingDouble(
                    s -> Double.parseDouble(s.getAmount().toString()))).toList();
        } else if (sort.equals("desc")) {
            result = result.stream()
                    .sorted(Comparator.comparingDouble(
                            s -> Double.parseDouble(((SaleDTO) s).getAmount().toString())).reversed())
                    .toList();
        }

        return result;
    }

    @Override
    public List<SaleDTO> getSaleByStoreId(String sort, Long storeId, LocalDateTime fromDate, LocalDateTime toDate) {
        if (toDate.isBefore(fromDate)) throw new InvalidParameterException("Invalid date");

        List<SaleDTO> result = new ArrayList<>();
        List<TransactionOrder> transList =
                transactionOrderRepository.findAllByStatusAndStore_IdAndDateCreatedBetween(true, storeId, fromDate, toDate);
        List<Order> orderList =
                orderRepository.findByStatusAndDateCreatedBetween(true, storeId, fromDate, toDate);

        while (!(fromDate.isAfter(toDate))) {
            LocalDateTime finalFromDate = fromDate;
            result.add(new SaleDTO(fromDate.toLocalDate(),
                    BigDecimal.valueOf(transList.stream().filter(
                            t -> t.getDateCreated().toLocalDate().isEqual(finalFromDate.toLocalDate())).mapToDouble(
                            t -> Double.parseDouble(t.getAmount().toString())).sum()),
                    orderList.stream().filter(
                            o -> o.getDateCreated().toLocalDate().isEqual(finalFromDate.toLocalDate())).count()));

            fromDate = fromDate.plusDays(1);
        }

        if (sort.equals("asc")) {
            result = result.stream().sorted(Comparator.comparingDouble(
                    s -> Double.parseDouble(s.getAmount().toString()))).toList();
        } else if (sort.equals("desc")) {
            result = result.stream()
                    .sorted(Comparator.comparingDouble(
                            s -> Double.parseDouble(((SaleDTO) s).getAmount().toString())).reversed())
                    .toList();
        }

        return result;
    }
}
