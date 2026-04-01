package com.atria.billingservice.validation;

import com.atria.billingservice.entity.Folio;
import com.atria.billingservice.entity.FolioItem;

import java.util.List;

public interface InvoiceValidationService {
    void validateInvoiceGeneration(Folio folio, List<FolioItem> items);
}
