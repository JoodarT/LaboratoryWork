<#import "/layout.ftlh" as main>

<@main.layout title="EduFood — Рестораны">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h3 fw-bold mb-1">Рестораны и кафе</h1>
            <#if searchParam?has_content>
                <p class="text-muted mb-0">Результаты поиска по запросу: <strong>"${searchParam}"</strong>
                    <a href="/restaurants" class="text-danger ms-2"><i class="bi bi-x-circle"></i> Сбросить</a>
                </p>
            <#else>
                <p class="text-muted mb-0">Выберите заведение для заказа любимых блюд</p>
            </#if>
        </div>
    </div>

    <#if restaurants?size == 0>
        <div class="alert alert-warning text-center py-5 shadow-sm" role="alert">
            <i class="bi bi-shop fs-1 text-warning d-block mb-3"></i>
            <h4 class="alert-heading">Заведения не найдены</h4>
            <p class="text-muted mb-0">Попробуйте изменить поисковый запрос или вернуться к общему списку.</p>
        </div>
    <#else>
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-4">
            <#list restaurants as r>
                <div class="col">
                    <div class="card h-100 border-0 shadow-sm restaurant-card rounded-3 overflow-hidden">
                        <#if r.imageUrl?has_content>
                            <img src="${r.imageUrl}" class="card-img-top" alt="${r.name}">
                        <#else>
                            <div class="card-img-top bg-secondary d-flex align-items-center justify-content-center text-white">
                                <i class="bi bi-image fs-1"></i>
                            </div>
                        </#if>
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title fw-bold text-dark mb-2">${r.name}</h5>
                            <p class="card-text text-muted small flex-grow-1">${(r.description)!''}</p>
                            <p class="card-text small text-secondary mb-3">
                                <i class="bi bi-geo-alt-fill text-danger me-1"></i> ${(r.address)!''}
                            </p>
                            <a href="/restaurants/${r.id}" class="btn btn-warning w-100 fw-semibold">
                                Смотреть меню <i class="bi bi-arrow-right ms-1"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

        <#if totalPages gt 1>
            <nav aria-label="Навигация по ресторанам">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if currentPage == 0>disabled</#if>">
                        <a class="page-link" href="/restaurants?page=${currentPage - 1}&search=${searchParam}">« Назад</a>
                    </li>
                    <#list 0..(totalPages - 1) as p>
                        <li class="page-item <#if p == currentPage>active</#if>">
                            <a class="page-link" href="/restaurants?page=${p}&search=${searchParam}">${p + 1}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if currentPage == (totalPages - 1)>disabled</#if>">
                        <a class="page-link" href="/restaurants?page=${currentPage + 1}&search=${searchParam}">Вперед »</a>
                    </li>
                </ul>
            </nav>
        </#if>
    </#if>
</@main.layout>