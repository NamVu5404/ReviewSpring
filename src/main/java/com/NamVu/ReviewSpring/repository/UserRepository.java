package com.NamVu.ReviewSpring.repository;

import com.NamVu.ReviewSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    void deleteByIdIn(Set<Long> userIds);

    // Distinct
//    @Query("""
//            select distinct
//            from User u
//            where u.firstName=:firstName and u.lastName=:lastName
//            """)
    List<User> findDistinctByFirstNameAndLastName(String firstName, String lastName);

//    @Query("""
//            select u
//            from User u
//            where u.email=?1
//            """)
    List<User> findByEmail(String email);

    // Or
//    @Query("""
//            select u
//            form User u
//            where u.firstName=:name or u.lastName=:name
//            """)
    List<User> findByFirstNameOrLastName(String name);

    // Is, Equals
//    @Query("""
//            select u
//            from User u
//            where u.firstName=:firstName
//            """)
    List<User> findByFirstNameIs(String firstName);

    // Between
//    @Query("""
//            select u
//            from User u
//            where u.createdAt between ?1 and ?2
//            """)
    List<User> findByCreatedAtBetween(Date from, Date to);

    // Less than
//    @Query("""
//            select u
//            from User u
//            where u.age < :age
//            """)
    List<User> findByAgeLessThan(int age);

    // Before, After
//    @Query("""
//            select u
//            from User u
//            where u.createdAt < :date
//            """)
    List<User> findByCreatedAtBefore(Date date);

//    @Query("""
//            select u
//            from User u
//            where u.createdAt > :date
//            """)
    List<User> findByCreatedAtAfter(Date date);

    // IsNull, Null, IsNotNull, NotNull
//    @Query("""
//            select u
//            from User u
//            where u.age is null
//            """)
    List<User> findByAgeIsNull();

    // Like
    @Query("""
            select u
            from User u
            where u.firstName like :firstName
            """)
    List<User> findByFirstNameLike(String firstName);

    // Starting with
//    @Query("""
//            select u
//            from User u
//            where u.firstName like :firstName%
//            """)
    List<User> findByFirstNameStartingWith(String firstName);

    // Ending with
//    @Query("""
//            select u
//            from User u
//            where u.firstName like %:firstName
//            """)
    List<User> findByFirstNameEndingWith(String firstName);

    // Containing
//    @Query("""
//            select u
//            from User u
//            where u.firstName like %:firstName%
//            """)
    List<User> findByFirstNameContaining(String firstName);

    // Not
//    @Query("""
//            select u
//            from User u
//            where u.firstName <> :firstName
//            """)
    List<User> findByFirstNameNot(String firstName);

    // In, Not In
//    @Query("""
//            select u
//            from User u
//            where u.age in (15,20,25)
//            """)
    List<User> findByAgeIn(Collection<Integer> ages);

    // True, False
//    @Query("""
//            select u
//            from User u
//            where u.active = true
//            """)
    List<User> findByActiveTrue();
    List<User> findByActiveFalse();

    // IgnoreCase
//    @Query("""
//            select u
//            from User u
//            where lower(u.firstName) = lower(:firstName)
//            """)
    List<User> findByFirstNameIgnoreCase(String firstName);
    List<User> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

    // Order by
//    @Query("""
//            select u
//            from User u
//            order by u.createdAt desc
//            """)
    List<User> findByCreatedAtDesc();

//    @Query("""
//            select u
//            from User u
//            inner join Address a
//            on u.id = a.userId
//            where a.city = :city
//            """)
    List<User> getAllUser(String city);
}
