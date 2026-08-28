<#import "../layout.ftlh" as main>

<@main.layout title="EduFood — Мои заказы">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 fw-bold mb-0">
            <i class="bi bi-clock-history text-warning me-2"></i>История моих заказов
        </h1>
        <a href="/restaurants" class="btn btn-outline-warning btn-sm">
            <i class="bi bi-shop me-1"></i> Перейти в меню ресторанов
        </a>
    </div>

    <#if orderCreated!false>
        <div class="alert alert-success alert-dismissible fade show rounded-3 shadow-sm mb-4" role="alert">
            <i class="bi bi-check-circle-fill me-2 fs-5"></i>
            <strong>Отлично!</strong> Ваш заказ успешно оформлен и передан на кухню.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </#if>

    <#if !orders?has_content || orders?size == 0>
        <div class="card border-0 shadow-sm rounded-4 p-5 text-center my-4 bg-white">
            <div class="my-3">
                <i class="bi bi-bag-x text-muted" style="font-size: 5rem;"></i>
            </div>
            <h4 class="fw-bold text-dark mt-2">У вас пока нет заказов</h4>
            <p class="text-muted mb-4">Самое время выбрать любимые блюда из каталога ресторанов!</p>
            <div>
                <a href="/restaurants" class="btn btn-warning px-4 py-2 fw-semibold">
                    <i class="bi bi-shop me-2"></i> Выбрать заведение
                </a>
            </div>
        </div>
    <#else>
        <div class="d-flex flex-column gap-4">
            <#list orders as order>
                <div class="card border-0 shadow-sm rounded-4 bg-white overflow-hidden">
                    <div class="card-header bg-white border-bottom p-3 d-flex flex-wrap justify-content-between align-items-center gap-2">
                        <div>
                            <span class="badge bg-warning text-dark me-2 fs-6">Заказ #${order.id}</span>
                            <span class="text-muted small">
                                <i class="bi bi-calendar3 me-1"></i>${order.createdAt.toString()?replace('T', ' ')?keep_before('.')}
                            </span>
                        </div>
                        <div class="fs-5 fw-bold text-dark">
                            Итого: <span class="text-warning text-dark">${order.totalPrice} сом</span>
                        </div>
                    </div>

                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead class="table-light">
                                <tr>
                                    <th scope="col" class="ps-4">Блюдо</th>
                                    <th scope="col" class="text-center">Ресторан</th>
                                    <th scope="col" class="text-center">Цена за шт.</th>
                                    <th scope="col" class="text-center">Количество</th>
                                    <th scope="col" class="text-end pe-4">Сумма</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list order.items as item>
                                    <tr>
                                        <td class="ps-4">
                                            <div class="fw-semibold text-dark">${item.dish.name}</div>
                                        </td>
                                        <td class="text-center text-muted small">
                                            ${item.dish.restaurant.name}
                                        </td>
                                        <td class="text-center">
                                            ${item.priceAtOrder} сом
                                        </td>
                                        <td class="text-center">
                                            <span class="badge bg-light text-dark border px-2 py-1">${item.quantity} шт.</span>
                                        </td>
                                        <td class="text-end pe-4 fw-bold text-dark">
                                            ${item.priceAtOrder * item.quantity} сом
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</@main.layout>