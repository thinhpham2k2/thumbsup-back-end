package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.payment.CreatePaymentAccountDTO;
import com.thumbsup.thumbsup.dto.payment.PaymentAccountDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPaymentAccountService {

    Page<PaymentAccountDTO> getPaymentList(boolean status, List<Long> storeIds, String search, String sort, int page, int limit);

    Page<PaymentAccountDTO> getPaymentListByStoreId(boolean status, long storeId, String search, String sort, int page, int limit);

    PaymentAccountDTO getPaymentById(long id);

    PaymentAccountDTO createPayment(CreatePaymentAccountDTO create);
}
