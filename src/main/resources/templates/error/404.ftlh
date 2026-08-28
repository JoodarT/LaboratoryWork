<#import "/layout.ftlh" as main>

<@main.layout title="EduFood — Страница не найдена">
    <div class="text-center py-5">
        <i class="bi bi-compass text-warning" style="font-size: 5rem;"></i>
        <h1 class="h3 fw-bold mt-3">404 — не найдено</h1>
        <p class="text-muted">
            <#if message??>${message}<#else>Запрошенная страница или ресурс не существует.</#if>
        </p>
        <a href="/restaurants" class="btn btn-warning fw-semibold mt-2">
            <i class="bi bi-house me-1"></i> Вернуться в каталог
        </a>
    </div>
</@main.layout>
