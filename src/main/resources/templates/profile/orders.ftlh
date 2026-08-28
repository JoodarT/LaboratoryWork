<#import "../layout.ftlh" as main>

<@main.layout title="EduFood — Мои заказы">
    <div class="row justify-content-center">
        <div class="col-lg-9">
            <h1 class="h3 fw-bold mb-4">
                <i class="bi bi-clock-history text-warning me-2"></i>Мои заказы
            </h1>

            <#if orders?size == 0>
                <div class="card border-0 shadow-sm rounded-4 p-5 text-center bg-white">
                    <div class="my-3">
                        <i class="bi bi-bag-x text-muted" style="font-size: 4rem;"></i>
                    </div>
                    <h4 class="fw-bold text-dark">Заказов пока нет</h4>
                    <p class="text-muted mb-4">Оформите первый заказ из каталога заведений.</p>
                    <div>
                        <a href="/restaurants" class="btn btn-warning px-4 fw-semibold">
                            <i class="bi bi-shop me-2"></i> Перейти к ресторанам
                        </a>
                    </div>
                </div>
            <#else>
                <#list orders as order>
                    <div class="card border-0 shadow-sm rounded-4 mb-3 bg-white">
                        <div class="card-header bg-white border-0 d-flex justify-content-between align-items-center pt-3 px-4">
                            <div>
                                <span class="fw-bold text-dark">Заказ №${order.id}</span>
                                <span class="text-muted small ms-2">${order.createdAtFormatted}</span>
                            </div>
                            <span class="badge bg-light text-dark border">
                                <i class="bi bi-shop me-1"></i> ${order.restaurant.name}
                            </span>
                        </div>
                        <div class="card-body px-4">
                            <ul class="list-unstyled mb-3">
                                <#list order.items as item>
                                    <li class="d-flex justify-content-between py-1 border-bottom">
                                        <span class="text-dark">${item.dish.name}
                                            <span class="text-muted small">× ${item.quantity}</span>
                                        </span>
                                        <span class="text-secondary">${item.priceAtOrder * item.quantity} сом</span>
                                    </li>
                                </#list>
                            </ul>
                            <div class="d-flex justify-content-between align-items-center">
                                <a href="/orders/${order.id}" class="btn btn-sm btn-outline-secondary">Подробнее</a>
                                <span class="fs-5 fw-bold text-dark">Итого: ${order.totalPrice} сом</span>
                            </div>
                        </div>
                    </div>
                </#list>
            </#if>
        </div>
    </div>
</@main.layout>
