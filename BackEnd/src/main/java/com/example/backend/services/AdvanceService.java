package com.example.backend.services;

import com.example.backend.entities.Advance;
import com.example.backend.entities.User;
import com.example.backend.repositories.AdvanceRepository;
import com.example.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
@Slf4j
public class AdvanceService implements IAdvanceService {
    AdvanceRepository advrepo;
    UserRepository userRepository;
    private static final double MAX_ADVANCE_LIMIT = 1000.0;
    @Override
    public Advance addAdvance(Advance advance) {
        return advrepo.save(advance);
    }

    @Override
    public void deleteAdvance(int idAdvance) {
        advrepo.deleteById(idAdvance);

    }

    @Override
    public Advance updateAdvance(Advance advance) {
        return advrepo.save(advance);
    }

    @Override
    public List<Advance> allAdvances() {
        return advrepo.findAll();
    }

    @Override
    public Advance findAdvanceById(int idAdvance) {
        return advrepo.findById(idAdvance).get();
    }

    @Override
    public List<Advance> getAllAdvancesByUser(int userId) {
        return advrepo.findAdvancesByUserId(userId);
    }

    @Override

    @Transactional
    public boolean canApproveAdvance(int userId, int advanceId) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return false; // Utilisateur non trouvé
            }

            Advance advance = advrepo.findById(advanceId).orElse(null);
            if (advance == null) {
                return false; // Demande d'avance non trouvée
            }

            // Vérification si l'avance appartient à l'utilisateur
            if (advance.getUser().getId() != userId) {
                return false; // L'avance n'appartient pas à l'utilisateur
            }

            // 1. Vérification du montant de l'avance
            if (advance.getAmount_request() > MAX_ADVANCE_LIMIT) {
                return false; // Montant dépasse la limite
            }

            // 2. Vérification du solde du salaire
            if (user.getSalary() < advance.getAmount_request()) {
                return false; // Salaire insuffisant
            }

            // 3. Vérification du nombre d'avances antérieures
        /*
        long advanceCount = advrepo.countByUserdAndStatus(userId, Status.ACCEPTED);
        if (advanceCount >= 3) {
            return false; // Trop d'avances approuvées
        }
        */

            // 4. Vérification de la date de la dernière avance
            List<Date> lastAdvanceDates = advrepo.findLastAdvanceDatesByUserId(userId);
            Date lastAdvanceDate = lastAdvanceDates.isEmpty() ? null : lastAdvanceDates.get(0);
            logger.info("Last Advance Dates for User " + userId + ": " + lastAdvanceDates);
            logger.info("Selected Last Advance Date: " + lastAdvanceDate);

            if (lastAdvanceDate != null && isWithinSameMonth(lastAdvanceDate, new Date())) {
                return false; // Déjà une avance ce mois-ci
            }

            // Si tous les critères sont respectés, l'avance peut être approuvée
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return false; // En cas d'erreur imprévue, on retourne false
        }
    }


    private boolean isWithinSameMonth(Date date1, Date date2) {
        // Compare the month and year of both dates
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }


   /* @Override
    public Advance updateAdvanceStatus(int id, String status) {
        Advance advance = findAdvanceById(id);
        if (advance != null) {
            advance.setStatus(AdvanceStatus.valueOf(status));
            return advrepo.save(advance);
        }
        return null;
    }*/
}
