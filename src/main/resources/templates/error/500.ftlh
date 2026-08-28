<#import "/layout.ftlh" as main>

<@main.layout title="EduFood — Ошибка сервера">
    <div class="text-center py-5">
        <i class="bi bi-exclamation-octagon text-danger" style="font-size: 5rem;"></i>
        <h1 class="h3 fw-bold mt-3">Что-то пошло не так</h1>
        <p class="text-muted">
            <#if message??>${message}<#else>Внутренняя ошибка сервера. Попробуйте повторить позже.</#if>
        </p>
        <a href="/restaurants" class="btn btn-warning fw-semibold mt-2">
            <i class="bi bi-house me-1"></i> Вернуться в каталог
        </a>
    </div>
</@main.layout>
