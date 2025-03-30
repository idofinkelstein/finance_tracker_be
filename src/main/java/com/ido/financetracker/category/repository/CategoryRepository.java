package com.ido.financetracker.category.repository;

import com.ido.financetracker.category.entity.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @NotNull
    public Optional<Category> findById(@NotNull Long id);
    @NotNull
    public Optional<Category> findByNameAndUserId(@NotNull String name, @NotNull Long userId);

    List<Category> findAllByUserId(Long userId);

    @NotNull
    Optional<Category> findByIdAndUserId(Long categoryId, Long UserId);
}
