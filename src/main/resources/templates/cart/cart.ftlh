<#import "/layout.ftlh" as main>

<@main.layout title="EduFood — Корзина">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 fw-bold mb-0">
            <i class="bi bi-cart3 text-warning me-2"></i>Ваша корзина
        </h1>
        <#if (cart.items?size > 0)>
            <form action="/cart/clear" method="post" onsubmit="return confirm('Вы действительно хотите очистить корзину?');">
                <button type="submit" class="btn btn-outline-danger btn-sm">
                    <i class="bi bi-trash3 me-1"></i> Очистить корзину
                </button>
            </form>
        </#if>
    </div>

    <#if !cart.items?has_content || cart.items?size == 0>
        <div class="card border-0 shadow-sm rounded-4 p-5 text-center my-4 bg-white">
            <div class="my-3">
                <i class="bi bi-cart-x text-muted" style="font-size: 5rem;"></i>
            </div>
            <h4 class="fw-bold text-dark mt-2">Ваша корзина пуста</h4>
            <p class="text-muted mb-4">Выберите вкусные блюда из каталога заведений прямо сейчас!</p>
            <div>
                <a href="/restaurants" class="btn btn-warning px-4 py-2 fw-semibold">
                    <i class="bi bi-shop me-2"></i> Перейти к ресторанам
                </a>
            </div>
        </div>
    <#else>
        <div class="alert alert-warning border-0 shadow-sm d-flex align-items-center mb-4" role="alert">
            <i class="bi bi-info-circle-fill fs-4 me-3 text-warning"></i>
            <div>
                Заказ формируется из ресторана:
                <strong class="text-dark">${cart.items[0].restaurantName}</strong>.
                <span class="text-muted small ms-1">(При добавлении блюда из другого заведения корзина обновится)</span>
            </div>
        </div>

        <div class="row g-4">
            <div class="col-lg-8">
                <div class="card border-0 shadow-sm rounded-4 overflow-hidden bg-white">
                    <div class="table-responsive">
                        <table class="table align-middle mb-0">
                            <thead class="table-light">
                            <tr>
                                <th scope="col" class="ps-4">Блюдо</th>
                                <th scope="col" class="text-center">Цена</th>
                                <th scope="col" class="text-center" style="width: 160px;">Количество</th>
                                <th scope="col" class="text-center">Сумма</th>
                                <th scope="col" class="text-center pe-4">Удалить</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list cart.items as item>
                                <tr>
                                    <td class="ps-4">
                                        <div class="d-flex align-items-center gap-3">
                                            <#if item.imageUrl?has_content>
                                                <img src="${item.imageUrl}" alt="${item.dishName}"
                                                     class="rounded-3 object-fit-cover" style="width: 54px; height: 54px;">
                                            <#else>
                                                <div class="bg-secondary rounded-3 d-flex align-items-center justify-content-center text-white"
                                                     style="width: 54px; height: 54px;">
                                                    <i class="bi bi-egg"></i>
                                                </div>
                                            </#if>
                                            <div>
                                                <h6 class="fw-bold text-dark mb-0">${item.dishName}</h6>
                                                <small class="text-muted">${item.restaurantName}</small>
                                            </div>
                                        </div>
                                    </td>

                                    <td class="text-center fw-medium">
                                        ${item.price} сом
                                    </td>

                                    <td class="text-center">
                                        <form action="/cart/update" method="post" class="d-flex justify-content-center align-items-center">
                                            <input type="hidden" name="dishId" value="${item.dishId}">
                                            <div class="input-group input-group-sm" style="max-width: 120px;">
                                                <input type="number" name="quantity" value="${item.quantity}"
                                                       min="1" max="50" class="form-control text-center" onchange="this.form.submit()">
                                                <button class="btn btn-outline-secondary" type="submit" title="Пересчитать">
                                                    <i class="bi bi-arrow-repeat"></i>
                                                </button>
                                            </div>
                                        </form>
                                    </td>

                                    <td class="text-center fw-bold text-dark">
                                        ${item.price * item.quantity} сом
                                    </td>

                                    <td class="text-center pe-4">
                                        <form action="/cart/remove" method="post">
                                            <input type="hidden" name="dishId" value="${item.dishId}">
                                            <button type="submit" class="btn btn-outline-danger btn-sm rounded-circle p-2" title="Удалить позицию">
                                                <i class="bi bi-trash3"></i>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="mt-3">
                    <a href="/restaurants/${cart.items[0].restaurantId}" class="btn btn-link text-decoration-none text-secondary ps-0">
                        <i class="bi bi-arrow-left me-1"></i> Продолжить покупки в этом ресторане
                    </a>
                </div>
            </div>

            <div class="col-lg-4">
                <div class="card border-0 shadow-sm rounded-4 p-4 bg-white sticky-top" style="top: 80px;">
                    <h5 class="fw-bold text-dark mb-3">Сводка заказа</h5>

                    <div class="d-flex justify-content-between mb-2 text-muted">
                        <span>Количество блюд:</span>
                        <span class="fw-semibold text-dark">${cart.totalQuantity} шт.</span>
                    </div>

                    <div class="d-flex justify-content-between mb-3 text-muted">
                        <span>Доставка:</span>
                        <span class="text-success fw-semibold">Бесплатно</span>
                    </div>

                    <hr class="my-3">

                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <span class="fs-5 fw-bold">Итого к оплате:</span>
                        <span class="fs-4 fw-bolder text-warning text-dark">${cart.totalPrice} сом</span>
                    </div>

                    <form action="/orders/checkout" method="post">
                        <button type="submit" class="btn btn-warning w-100 py-3 fw-bold fs-6 shadow-sm">
                            <i class="bi bi-bag-check-fill me-2"></i> Оформить заказ
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </#if>
</@main.layout>