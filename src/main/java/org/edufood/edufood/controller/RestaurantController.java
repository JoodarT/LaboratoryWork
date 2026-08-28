package org.edufood.edufood.controller;

import lombok.RequiredArgsConstructor;
import org.edufood.edufood.entities.Dish;
import org.edufood.edufood.entities.Restaurant;
import org.edufood.edufood.service.service_interface.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/")
    public String index() {
        return "redirect:/restaurants";
    }

    @GetMapping("/restaurants")
    public String getRestaurants(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model
    ) {
        int pageSize = 5;
        Page<Restaurant> restaurantPage = restaurantService.getRestaurants(search, page, pageSize);

        model.addAttribute("restaurants", restaurantPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", restaurantPage.getTotalPages());
        model.addAttribute("searchParam", search != null ? search : "");

        return "restaurants/all-restaurants";
    }

    @GetMapping("/restaurants/{id}")
    public String getRestaurant(
            @PathVariable("id") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model
    ) {
        int pageSize = 10;
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        Page<Dish> dishPage = restaurantService.getDishesByRestaurant(id, page, pageSize);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("dishes", dishPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", dishPage.getTotalPages());

        return "restaurants/restaurant";
    }
}