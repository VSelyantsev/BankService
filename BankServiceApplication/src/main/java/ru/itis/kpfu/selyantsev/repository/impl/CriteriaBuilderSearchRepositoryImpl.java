package ru.itis.kpfu.selyantsev.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.itis.kpfu.selyantsev.exceptions.NotFoundFilterByFieldException;
import ru.itis.kpfu.selyantsev.exceptions.ParseDateFormatException;
import ru.itis.kpfu.selyantsev.model.*;
import ru.itis.kpfu.selyantsev.repository.CriteriaBuilderSearchRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ru.itis.kpfu.selyantsev.model.User_.*;


@Repository
@RequiredArgsConstructor
public class CriteriaBuilderSearchRepositoryImpl implements CriteriaBuilderSearchRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Page<User> searchUsersByFilters(
            Optional<String> dateBirth,
            Optional<String> phoneNumber,
            Optional<String> fullName,
            Optional<String> email,
            Optional<String> order,
            Pageable pageable
    ) throws ParseDateFormatException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = builder.createQuery(User.class);
        Root<User> root = userCriteriaQuery.from(User.class);

        List<Predicate> filters = new ArrayList<>();

        orderBySelectedFieldIfPresent(order, builder, userCriteriaQuery, root);

        fullName.ifPresent(name -> filters.add(builder.like(root.get(User_.fullName), name + "%")));

        if (dateBirth.isPresent()) {
            Date extractedDate = parseDate(dateBirth.get());
            filters.add(builder.greaterThan(root.get(birthDate), extractedDate));
        }

        if (phoneNumber.isPresent()) {
            Join<User, PhoneNumber> phoneNumberJoin = root.join(User_.phoneNumbers);
            filters.add(builder.equal(
                            phoneNumberJoin.get(PhoneNumber_.number),
                            phoneNumber.get()
            ));
        }

        if (email.isPresent()) {
            Join<User, EmailAddress> userEmailAddressJoin = root.join(User_.emailAddresses);
            filters.add(builder.equal(
                    userEmailAddressJoin.get(EmailAddress_.email),
                    email.get()
            ));
        }

        userCriteriaQuery.where(filters.toArray(new Predicate[0]));

        TypedQuery<User> userTypedQuery = entityManager.createQuery(userCriteriaQuery);

        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        userTypedQuery.setFirstResult(offset);
        userTypedQuery.setMaxResults(pageable.getPageSize());

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(User.class)));

        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<User> resultUserList = entityManager.createQuery(userCriteriaQuery).getResultList();
        return new PageImpl<>(resultUserList, pageable, totalCount);
    }

    private void orderBySelectedFieldIfPresent(
            Optional<String> order,
            CriteriaBuilder builder,
            CriteriaQuery<User> userCriteriaQuery,
            Root<User> root
    ) {
        if (order.isPresent()) {
            String sortField = order.get();
            switch (sortField) {
                case FULL_NAME -> userCriteriaQuery.orderBy(builder.asc(root.get(User_.fullName)));
                case ACTIVITY_STATUS -> userCriteriaQuery.orderBy(builder.asc(root.get(User_.activityStatus)));
                case BIRTH_DATE -> userCriteriaQuery.orderBy(builder.asc(root.get(User_.birthDate)));
                default -> throw new NotFoundFilterByFieldException(order.get());
            }
        }
    }

    private Date parseDate(String extractDate) throws ParseDateFormatException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(extractDate);
        } catch (ParseException exception) {
            throw new ParseDateFormatException(extractDate, exception.getErrorOffset());
        }
    }
}
