package com.atria.billingservice.validation;

import com.atria.billingservice.entity.Folio;
import com.atria.billingservice.entity.FolioItem;

public interface FolioValidationService {

    void validateFolioOpen(Folio folio);

    void validateFolioNotLocked(Folio folio);

    void validateReversalAllowed(FolioItem item, String userRole);

    void validateSufficientBalanceForClosure(Folio folio);
}