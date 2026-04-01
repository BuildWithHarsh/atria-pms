package com.atria.billingservice.validation;

import com.atria.billingservice.entity.Folio;
import com.atria.billingservice.entity.FolioItem;
import com.atria.billingservice.entity.FolioStatus;
import com.atria.billingservice.exception.InvalidFolioStateException;
import com.atria.billingservice.exception.UnauthorizedTenantAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class FolioValidationServiceImpl implements FolioValidationService {

    @Override
    public void validateFolioOpen(Folio folio) {
        if (folio.getStatus() == FolioStatus.CLOSED) {
            throw new InvalidFolioStateException("Folio is already closed");
        }
    }

    @Override
    public void validateFolioNotLocked(Folio folio) {
        if (folio.isLocked()) {
            throw new InvalidFolioStateException("Folio is locked");
        }
    }

    @Override
    public void validateReversalAllowed(FolioItem item, String userRole) {

        LocalDate today = LocalDate.now();
        LocalDate itemDate = item.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate();

        // Same day → allow
        if (today.equals(itemDate)) {
            return;
        }

        // Different day → only admin
        if (!"ADMIN".equalsIgnoreCase(userRole)) {
            throw new UnauthorizedTenantAccessException(
                    "Only admin can reverse entries after day closure"
            );
        }
    }

    @Override
    public void validateSufficientBalanceForClosure(Folio folio) {
        if (folio.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new InvalidFolioStateException(
                    "Pending amount must be cleared before closing folio"
            );
        }
    }
}