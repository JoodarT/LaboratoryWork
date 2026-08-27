package org.edufood.edufood.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edufood.edufood.entities.Dish;
import org.edufood.edufood.entities.Restaurant;
import org.edufood.edufood.repository.DishRepository;
import org.edufood.edufood.repository.RestaurantRepository;
import org.edufood.edufood.service.service_interface.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Override
    public Page<Restaurant> getRestaurants(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (search != null && !search.trim().isEmpty()) {
            log.info("Поиск ресторанов по запросу: '{}', страница: {}", search, page);
            return restaurantRepository.findByNameContainingIgnoreCase(search.trim(), pageable);
        }
        log.info("Запрос списка ресторанов, страница: {}", page);
        return restaurantRepository.findAll(pageable);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        log.info("Запрос информации о ресторане ID: {}", id);
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан с ID " + id + " не найден"));
    }

    @Override
    public Page<Dish> getDishesByRestaurant(Long restaurantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Запрос меню ресторана ID: {}, страница: {}", restaurantId, page);
        return dishRepository.findByRestaurantId(restaurantId, pageable);
    }

    @Override
    public Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Блюдо с ID " + id + " не найдено"));
    }
}