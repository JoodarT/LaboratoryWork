<#import "/layout.ftlh" as main>

<@main.layout title="EduFood — Заказ оформлен">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5 bg-white text-center mb-4">
                <div class="mb-3">
                    <i class="bi bi-check-circle-fill text-success" style="font-size: 4rem;"></i>
                </div>
                <h1 class="h3 fw-bold text-dark">Заказ №${order.id} оформлен</h1>
                <p class="text-muted mb-0">
                    ${order.createdAtFormatted} · ${order.restaurant.name}
                </p>
            </div>

            <div class="card border-0 shadow-sm rounded-4 overflow-hidden bg-white">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead class="table-light">
                        <tr>
                            <th class="ps-4">Блюдо</th>
                            <th class="text-center">Цена</th>
                            <th class="text-center">Кол-во</th>
                            <th class="text-center pe-4">Сумма</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list order.items as item>
                            <tr>
                                <td class="ps-4 fw-medium text-dark">${item.dish.name}</td>
                                <td class="text-center">${item.priceAtOrder} сом</td>
                                <td class="text-center">${item.quantity}</td>
                                <td class="text-center pe-4 fw-bold">${item.priceAtOrder * item.quantity} сом</td>
                            </tr>
                        </#list>
                        </tbody>
                        <tfoot class="table-light">
                        <tr>
                            <td colspan="3" class="ps-4 fw-bold text-end">Итого:</td>
                            <td class="text-center pe-4 fs-5 fw-bolder text-dark">${order.totalPrice} сом</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <div class="d-flex gap-2 mt-4">
                <a href="/profile/orders" class="btn btn-outline-secondary">
                    <i class="bi bi-clock-history me-1"></i> Мои заказы
                </a>
                <a href="/restaurants" class="btn btn-warning fw-semibold">
                    <i class="bi bi-shop me-1"></i> Вернуться в каталог
                </a>
            </div>
        </div>
    </div>
</@main.layout>
