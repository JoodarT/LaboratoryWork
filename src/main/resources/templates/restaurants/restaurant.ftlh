<#import "/layout.ftlh" as main>

<@main.layout title="EduFood — ${restaurant.name}">
    <div class="card border-0 shadow-sm rounded-4 overflow-hidden mb-4 bg-white">
        <div class="row g-0">
            <div class="col-md-5">
                <#if restaurant.imageUrl?has_content>
                    <img src="${restaurant.imageUrl}" class="img-fluid h-100 w-100 object-fit-cover" style="max-height: 280px;" alt="${restaurant.name}">
                <#else>
                    <div class="bg-secondary h-100 d-flex align-items-center justify-content-center text-white" style="min-height: 220px;">
                        <i class="bi bi-image fs-1"></i>
                    </div>
                </#if>
            </div>
            <div class="col-md-7 d-flex flex-column justify-content-center p-4">
                <div class="mb-2">
                    <a href="/restaurants" class="text-decoration-none text-muted small"><i class="bi bi-arrow-left"></i> Все рестораны</a>
                </div>
                <h1 class="h2 fw-bold text-dark mb-2">${restaurant.name}</h1>
                <p class="text-muted mb-3">${(restaurant.description)!''}</p>
                <div class="text-secondary small">
                    <i class="bi bi-geo-alt-fill text-danger me-1"></i> ${(restaurant.address)!''}
                </div>
            </div>
        </div>
    </div>

    <h3 class="fw-bold mb-3">Меню блюд</h3>

    <#if dishes?size == 0>
        <div class="alert alert-info text-center py-4">В меню заведения пока нет доступных блюд.</div>
    <#else>
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-4">
            <#list dishes as d>
                <div class="col">
                    <div class="card h-100 border-0 shadow-sm dish-card rounded-3 overflow-hidden">
                        <#if d.imageUrl?has_content>
                            <img src="${d.imageUrl}" class="card-img-top" alt="${d.name}">
                        <#else>
                            <div class="card-img-top bg-secondary d-flex align-items-center justify-content-center text-white">
                                <i class="bi bi-egg fs-1"></i>
                            </div>
                        </#if>
                        <div class="card-body d-flex flex-column">
                            <div class="d-flex justify-content-between align-items-start mb-2">
                                <h5 class="card-title fw-bold text-dark mb-0">${d.name}</h5>
                                <span class="badge bg-warning text-dark fs-6">${d.price} сом</span>
                            </div>
                            <p class="card-text text-muted small flex-grow-1">${(d.description)!''}</p>

                            <form action="/cart/add" method="post" class="mt-3">
                                <input type="hidden" name="dishId" value="${d.id}">
                                <div class="input-group">
                                    <input type="number" name="quantity" class="form-control text-center" value="1" min="1" max="50">
                                    <button class="btn btn-warning fw-semibold px-3" type="submit">
                                        <i class="bi bi-cart-plus me-1"></i> В корзину
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

        <#if totalPages gt 1>
            <nav aria-label="Навигация по меню">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if currentPage == 0>disabled</#if>">
                        <a class="page-link" href="/restaurants/${restaurant.id}?page=${currentPage - 1}">« Назад</a>
                    </li>
                    <#list 0..(totalPages - 1) as p>
                        <li class="page-item <#if p == currentPage>active</#if>">
                            <a class="page-link" href="/restaurants/${restaurant.id}?page=${p}">${p + 1}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if currentPage == (totalPages - 1)>disabled</#if>">
                        <a class="page-link" href="/restaurants/${restaurant.id}?page=${currentPage + 1}">Вперед »</a>
                    </li>
                </ul>
            </nav>
        </#if>
    </#if>
</@main.layout>