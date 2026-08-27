package org.edufood.edufood.service.service_interface;

import org.edufood.edufood.entities.Dish;
import org.edufood.edufood.entities.Restaurant;
import org.springframework.data.domain.Page;

public interface RestaurantService {

    Page<Restaurant> getRestaurants(String search, int page, int size);

    Restaurant getRestaurantById(Long id);

    Page<Dish> getDishesByRestaurant(Long restaurantId, int page, int size);

    Dish getDishById(Long id);
}