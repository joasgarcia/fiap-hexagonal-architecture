package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.util.ItemTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ItemJpaRepositoryIT {

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveItem() {
            ItemJpa itemJpa = ItemTestUtil.generateJpa("Item 1", "Description 1", 17.5);
            ItemJpa persistedItemJpa = itemJpaRepository.save(itemJpa);
            assertThat(persistedItemJpa).isNotNull();
            assertThat(persistedItemJpa.getName()).isEqualTo(itemJpa.getName());
            assertThat(persistedItemJpa.getDescription()).isEqualTo(itemJpa.getDescription());
            assertThat(persistedItemJpa.getPrice()).isEqualTo(itemJpa.getPrice());
        }

        @Test
        @Rollback
        void mustDeleteItem() {
            ItemJpa itemJpa = ItemTestUtil.generateJpa("Item 1", "Description 1", 17.5);
            ItemJpa persistedItemJpa = itemJpaRepository.save(itemJpa);
            itemJpaRepository.delete(persistedItemJpa);

            Optional<ItemJpa> optionalItemJpa = itemJpaRepository.findById(itemJpa.getId());
            assertThat(optionalItemJpa).isNotPresent();
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindItemById() {
            ItemJpa itemJpa1 = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 17.5));
            Optional<ItemJpa> optionalItemJpa1 = itemJpaRepository.findById(itemJpa1.getId());

            ItemJpa itemJpa2 = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 2", "Description 2", 12.5));
            Optional<ItemJpa> optionalItemJpa2 = itemJpaRepository.findById(itemJpa2.getId());

            assertThat(optionalItemJpa1).isPresent();
            assertThat(optionalItemJpa2).isPresent();
        }

        @Test
        @Rollback
        void mustNotFindItemById() {
            Optional<ItemJpa> optionalItemJpa3 = itemJpaRepository.findById(0L);
            assertThat(optionalItemJpa3).isNotPresent();
        }

        @Test
        @Rollback
        void mustVerifyIfItemExistsById() {
            ItemJpa itemJpa1 = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 17.5));
            ItemJpa itemJpa2 = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 2", "Description 2", 12.5));

            assertThat(itemJpaRepository.existsById(itemJpa1.getId())).isTrue();
            assertThat(itemJpaRepository.existsById(itemJpa2.getId())).isTrue();
            assertThat(itemJpaRepository.existsById(0L)).isFalse();
        }
    }
}
