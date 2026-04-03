package com.atria.billingservice.validation;

import com.atria.billingservice.entity.Folio;
import com.atria.billingservice.entity.FolioItem;
import com.atria.billingservice.entity.FolioStatus;
import com.atria.billingservice.exception.InvalidFolioStateException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceValidationServiceImpl implements InvoiceValidationService {

    @Override
    public void validateInvoiceGeneration(Folio folio, List<FolioItem> items) {

        /*if (folio.getStatus() != FolioStatus.CLOSED) {
            throw new InvalidFolioStateException("Invoice allowed only for closed folios");
        }*/

        /*if (folio.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new InvalidFolioStateException("Pending balance exists");
        }*/

        /*if (folio.getBillNumber() == null) {
            throw new InvalidFolioStateException("Bill number not generated");
        }*/

        if (items == null || items.isEmpty()) {
            throw new InvalidFolioStateException("No transactions found");
        }
    }

}
