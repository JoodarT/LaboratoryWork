<#import "/spring.ftl" as spring>

<#macro csrf><#if _csrf??><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"></#if></#macro>

<#macro layout title="EduFood — Доставка еды">
    <!DOCTYPE html>
    <html lang="ru" class="h-100">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
        <style>
            .restaurant-card:hover, .dish-card:hover {
                transform: translateY(-3px);
                transition: 0.2s ease-in-out;
                box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.12)!important;
            }
            .card-img-top {
                height: 200px;
                object-fit: cover;
            }
        </style>
    </head>
    <body class="d-flex flex-column h-100 bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold text-warning d-flex align-items-center gap-2" href="/restaurants">
                <i class="bi bi-egg-fried fs-3"></i> EduFood
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarContent">
                <form class="d-flex mx-auto my-2 my-lg-0 col-12 col-lg-5" action="/restaurants" method="get">
                    <div class="input-group">
                        <input class="form-control" type="search" name="search" placeholder="Поиск ресторанов..."
                               value="${(searchParam)!''}" aria-label="Поиск">
                        <button class="btn btn-warning" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </div>
                </form>

                <div class="d-flex align-items-center gap-3">
                    <a href="/cart" class="btn btn-outline-light position-relative">
                        <i class="bi bi-cart3 fs-5"></i>
                        <#if (cart.totalQuantity)?? && (cart.totalQuantity > 0)>
                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                ${cart.totalQuantity}
                            </span>
                        </#if>
                    </a>

                    <div class="dropdown">
                        <#if currentUser??>
                            <button class="btn btn-secondary dropdown-toggle d-flex align-items-center gap-1" type="button" data-bs-toggle="dropdown">
                                <i class="bi bi-person-circle"></i> ${currentUser.fullName}
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end shadow">
                                <li><a class="dropdown-item" href="/profile/orders"><i class="bi bi-clock-history me-2"></i>Мои заказы</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <form action="/logout" method="post" class="px-1">
                                        <@csrf/>
                                        <button type="submit" class="dropdown-item text-danger"><i class="bi bi-box-arrow-right me-2"></i>Выйти</button>
                                    </form>
                                </li>
                            </ul>
                        <#else>
                            <button class="btn btn-secondary dropdown-toggle d-flex align-items-center gap-1" type="button" data-bs-toggle="dropdown">
                                <i class="bi bi-person-circle"></i> Профиль
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end shadow">
                                <li><a class="dropdown-item text-primary" href="/auth/login"><i class="bi bi-box-arrow-in-right me-2"></i>Войти</a></li>
                                <li><a class="dropdown-item text-success" href="/auth/register"><i class="bi bi-person-plus me-2"></i>Регистрация</a></li>
                            </ul>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </nav>

    <main class="flex-shrink-0 my-4">
        <div class="container">
            <#nested/>
        </div>
    </main>

    <footer class="footer mt-auto py-3 bg-white border-top text-center text-muted">
        <div class="container">
            <small>© 2026 EduFood. Сервис заказа и быстрой доставки еды.</small>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
    </html>
</#macro>