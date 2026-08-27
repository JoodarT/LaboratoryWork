<#import "/layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout title="EduFood — Регистрация">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white">
                <h1 class="h4 fw-bold mb-3 text-center">Создание аккаунта</h1>

                <form action="/auth/register" method="post">
                    <@main.csrf/>

                    <div class="mb-3">
                        <label class="form-label">Имя и фамилия</label>
                        <@spring.formInput "user.fullName", 'class="form-control"'/>
                        <@spring.showErrors "<br>", "text-danger small d-block mt-1"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <@spring.formInput "user.email", 'class="form-control"', "email"/>
                        <@spring.showErrors "<br>", "text-danger small d-block mt-1"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Пароль</label>
                        <@spring.formPasswordInput "user.password", 'class="form-control"'/>
                        <@spring.showErrors "<br>", "text-danger small d-block mt-1"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Повторите пароль</label>
                        <@spring.formPasswordInput "user.confirmPassword", 'class="form-control"'/>
                        <@spring.showErrors "<br>", "text-danger small d-block mt-1"/>
                    </div>

                    <button type="submit" class="btn btn-warning w-100 fw-semibold">
                        <i class="bi bi-person-plus me-1"></i> Зарегистрироваться
                    </button>
                </form>

                <p class="text-center text-muted small mt-3 mb-0">
                    Уже есть аккаунт? <a href="/auth/login">Войти</a>
                </p>
            </div>
        </div>
    </div>
</@main.layout>
