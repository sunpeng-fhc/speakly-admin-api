package com.speakly.api.admin.category.repository;


import com.speakly.api.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsBySlug(String slug);

    Page<Category> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Category> findByStatus(Boolean status, Pageable pageable);

    Page<Category> findByNameContainingIgnoreCaseAndStatus(String keyword, Boolean status, Pageable pageable);

    @Query(value = """
    SELECT *
    FROM categories c
    WHERE
        (:name IS NULL OR :name = '' OR c.name ILIKE '%' || CAST(:name AS TEXT) || '%')
    AND
        (:shortName IS NULL OR :shortName = '' OR c.short_name ILIKE '%' || CAST(:shortName AS TEXT) || '%')
    AND
        (:slug IS NULL OR :slug = '' OR c.slug ILIKE '%' || CAST(:slug AS TEXT) || '%')
    AND
        (:description IS NULL OR :description = '' OR c.description ILIKE '%' || CAST(:description AS TEXT) || '%')
    AND
        (:status IS NULL OR c.status = :status)
""",
            countQuery = """
    SELECT COUNT(*)
    FROM categories c
    WHERE
        (:name IS NULL OR :name = '' OR c.name ILIKE '%' || CAST(:name AS TEXT) || '%')
    AND
        (:shortName IS NULL OR :shortName = '' OR c.short_name ILIKE '%' || CAST(:shortName AS TEXT) || '%')
    AND
        (:slug IS NULL OR :slug = '' OR c.slug ILIKE '%' || CAST(:slug AS TEXT) || '%')
    AND
        (:description IS NULL OR :description = '' OR c.description ILIKE '%' || CAST(:description AS TEXT) || '%')
    AND
        (:status IS NULL OR c.status = :status)
""",
            nativeQuery = true)
    Page<Category> searchCategories(
            String name,
            String shortName,
            String slug,
            String description,
            Boolean status,
            Pageable pageable
    );

    List<Category> findByStatusTrueOrderBySortOrderAscCreatedAtDesc();

    Optional<Category> findBySlugAndStatusTrue(String slug);
}

