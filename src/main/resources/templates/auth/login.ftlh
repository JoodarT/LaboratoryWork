<#import "/layout.ftlh" as main>

<@main.layout title="EduFood — Вход">
    <div class="row justify-content-center">
        <div class="col-md-5 col-lg-4">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white">
                <h1 class="h4 fw-bold mb-3 text-center">Вход в аккаунт</h1>

                <#if loginError!false>
                    <div class="alert alert-danger py-2">Неверный email или пароль.</div>
                </#if>
                <#if justRegistered!false>
                    <div class="alert alert-success py-2">Регистрация завершена. Войдите в аккаунт.</div>
                </#if>
                <#if loggedOut!false>
                    <div class="alert alert-info py-2">Вы вышли из аккаунта.</div>
                </#if>

                <form action="/auth/login" method="post">
                    <@main.csrf/>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control" required autofocus>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Пароль</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-warning w-100 fw-semibold">
                        <i class="bi bi-box-arrow-in-right me-1"></i> Войти
                    </button>
                </form>

                <p class="text-center text-muted small mt-3 mb-0">
                    Нет аккаунта? <a href="/auth/register">Зарегистрироваться</a>
                </p>
            </div>
        </div>
    </div>
</@main.layout>
