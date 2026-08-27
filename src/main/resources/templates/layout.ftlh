<#import "/spring.ftl" as spring>

<#macro page title="EduFood">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${title} — EduFood</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
</head>
<body class="d-flex flex-column min-vh-100">

<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="/">EduFood</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
                aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">

            <form class="d-flex flex-grow-1 mx-lg-4 my-2 my-lg-0" action="/restaurants" method="get">
                <input class="form-control" type="search" name="search" placeholder="Поиск заведения..."
                       aria-label="Поиск">
            </form>

            <ul class="navbar-nav ms-auto align-items-lg-center gap-2">
                <li class="nav-item">
                    <a href="/cart" class="btn btn-outline-dark position-relative">
                        <i class="bi bi-cart3"></i> Корзина
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                            <#if cartCount?? && cartCount gt 0>${cartCount}<#else>0</#if>
                        </span>
                    </a>
                </li>

                <#-- Блок профиля/входа. Заглушка -->
                <li class="nav-item dropdown">
                    <#if user??>
                        <a class="nav-link dropdown-toggle" href="#" id="userMenu" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-person-circle"></i> ${user.fullName}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userMenu">
                            <li><a class="dropdown-item" href="/profile/orders">Мои заказы</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form action="/logout" method="post" class="px-3">
                                    <button type="submit" class="btn btn-link dropdown-item p-0">Выйти</button>
                                </form>
                            </li>
                        </ul>
                    <#else>
                        <a href="/auth/login" class="btn btn-outline-primary">Войти</a>
                    </#if>
                </li>
            </ul>
        </div>
    </div>
</nav>

<main class="container my-4 flex-grow-1">
    <#nested>
</main>

<footer class="footer py-3 bg-light border-top text-center text-muted">
    <div class="container">
        &copy; 2026 EduFood
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
</#macro>
