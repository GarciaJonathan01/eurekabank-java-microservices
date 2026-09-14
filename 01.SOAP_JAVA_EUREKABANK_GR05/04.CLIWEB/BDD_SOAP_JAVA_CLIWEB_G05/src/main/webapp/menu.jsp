<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="light" lang="es">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <title>EurekaBank - Dashboard</title>
    <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@400;500;700;800&display=swap" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet"/>
    <script>
    tailwind.config = {
      darkMode: "class",
      theme: {
        extend: {
          colors: {
            "primary": "#3ab4d9",
            "accent-coral": "#f67e80",
            "accent-yellow": "#f6de88",
            "accent-purple": "#9e7cc5",
            "background-light": "#afe0f8",
            "background-dark": "#121d20",
          },
          fontFamily: {
            "display": ["Manrope", "sans-serif"]
          },
          borderRadius: {
            "DEFAULT": "0.25rem",
            "lg": "0.5rem",
            "xl": "0.75rem",
            "full": "9999px"
          },
        },
      },
    }
  </script>
<style>
    .material-symbols-outlined {
      font-variation-settings:
        'FILL' 0,
        'wght' 400,
        'GRAD' 0,
        'opsz' 24
    }
  </style>
</head>
<body class="font-display bg-gray-50 dark:bg-background-dark text-[#0f171a] dark:text-gray-200">
<div class="relative flex h-auto min-h-screen w-full flex-col group/design-root overflow-x-hidden">
<div class="layout-container flex h-full grow flex-col">
<!-- Header -->
<header class="flex items-center justify-between whitespace-nowrap border-b border-solid border-gray-200 dark:border-white/10 px-6 md:px-10 lg:px-20 py-3 bg-white dark:bg-black/20 backdrop-blur-sm sticky top-0 z-50">
<div class="flex items-center gap-4 text-[#0f171a] dark:text-white">
<div class="size-8 text-primary">
<svg fill="currentColor" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
<path clip-rule="evenodd" d="M24 18.4228L42 11.475V34.3663C42 34.7796 41.7457 35.1504 41.3601 35.2992L24 42V18.4228Z" fill-rule="evenodd"></path>
<path clip-rule="evenodd" d="M24 8.18819L33.4123 11.574L24 15.2071L14.5877 11.574L24 8.18819ZM9 15.8487L21 20.4805V37.6263L9 32.9945V15.8487ZM27 37.6263V20.4805L39 15.8487V32.9945L27 37.6263ZM25.354 2.29885C24.4788 1.98402 23.5212 1.98402 22.646 2.29885L4.98454 8.65208C3.7939 9.08038 3 10.2097 3 11.475V34.3663C3 36.0196 4.01719 37.5026 5.55962 38.098L22.9197 44.7987C23.6149 45.0671 24.3851 45.0671 25.0803 44.7987L42.4404 38.098C43.9828 37.5026 45 36.0196 45 34.3663V11.475C45 10.2097 44.2061 9.08038 43.0155 8.65208L25.354 2.29885Z" fill-rule="evenodd"></path>
</svg>
</div>
<h2 class="text-[#0f171a] dark:text-white text-xl font-bold leading-tight tracking-[-0.015em]">EurekaBank</h2>
</div>
<div class="flex flex-1 justify-end gap-8 items-center">
<nav class="hidden md:flex items-center gap-6">
<a class="text-primary dark:text-primary text-sm font-bold leading-normal relative py-2 after:content-[''] after:absolute after:bottom-0 after:left-0 after:w-full after:h-0.5 after:bg-primary" href="${pageContext.request.contextPath}/menu">Inicio</a>
<a class="text-[#0f171a] dark:text-gray-300 text-sm font-medium leading-normal hover:text-primary dark:hover:text-primary transition-colors py-2" href="${pageContext.request.contextPath}/movimientos">Movimientos</a>
<a class="text-[#0f171a] dark:text-gray-300 text-sm font-medium leading-normal hover:text-primary dark:hover:text-primary transition-colors py-2" href="${pageContext.request.contextPath}/transferencia">Transferencias</a>
</nav>
<button onclick="openAyudaModal()" class="text-[#0f171a] dark:text-gray-300 text-sm font-medium hover:text-primary transition-colors py-2">Ayuda</button>
<button onclick="openSalirModal()" class="text-[#0f171a] dark:text-gray-300 text-sm font-medium hover:text-accent-coral transition-colors py-2">Salir</button>
</div>
</header>

<!-- Main Content -->
<main class="flex-1 w-full max-w-7xl mx-auto px-6 md:px-10 lg:px-20 py-8">
<div class="mb-6">
<h1 class="text-[#0f171a] dark:text-white text-3xl font-black leading-tight tracking-[-0.033em]">Bienvenido, ${sessionScope.usuario}!</h1>
<p class="text-gray-500 dark:text-gray-400 text-base mt-2">Gestiona tus finanzas de forma rápida y segura</p>
</div>

<div class="grid grid-cols-1 lg:grid-cols-12 gap-6">
<!-- Left Column: Main Actions -->
<div class="lg:col-span-8 flex flex-col gap-6">
<!-- Quick Actions Section -->
<div class="p-6 bg-white dark:bg-gray-800/50 rounded-xl shadow-lg">
<h2 class="text-[#0f171a] dark:text-white text-xl font-bold leading-tight tracking-[-0.015em] mb-6">Operaciones Disponibles</h2>
<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
<!-- Consultar Historial -->
<a href="${pageContext.request.contextPath}/movimientos" class="flex items-center p-5 rounded-lg bg-gray-50 dark:bg-gray-900/50 hover:bg-primary/10 dark:hover:bg-primary/20 transition-all group border border-gray-200 dark:border-gray-700 hover:border-primary">
<div class="flex items-center justify-center size-12 rounded-lg bg-primary/10 text-primary dark:bg-primary/20 mr-4 group-hover:scale-110 transition-transform">
<span class="material-symbols-outlined text-2xl">history</span>
</div>
<div class="flex-1">
<p class="text-base font-bold text-[#0f171a] dark:text-white">Consultar Historial</p>
<p class="text-sm text-gray-500 dark:text-gray-400">Revisa tus movimientos bancarios</p>
</div>
<span class="material-symbols-outlined text-gray-400 group-hover:text-primary transition-colors">chevron_right</span>
</a>

<!-- Realizar Depósito -->
<a href="${pageContext.request.contextPath}/deposito" class="flex items-center p-5 rounded-lg bg-gray-50 dark:bg-gray-900/50 hover:bg-accent-yellow/10 dark:hover:bg-accent-yellow/20 transition-all group border border-gray-200 dark:border-gray-700 hover:border-accent-yellow">
<div class="flex items-center justify-center size-12 rounded-lg bg-accent-yellow/10 text-accent-yellow dark:bg-accent-yellow/20 mr-4 group-hover:scale-110 transition-transform">
<span class="material-symbols-outlined text-2xl">account_balance_wallet</span>
</div>
<div class="flex-1">
<p class="text-base font-bold text-[#0f171a] dark:text-white">Realizar Depósito</p>
<p class="text-sm text-gray-500 dark:text-gray-400">Agrega fondos a tu cuenta</p>
</div>
<span class="material-symbols-outlined text-gray-400 group-hover:text-accent-yellow transition-colors">chevron_right</span>
</a>

<!-- Realizar Retiro -->
<a href="${pageContext.request.contextPath}/retiro" class="flex items-center p-5 rounded-lg bg-gray-50 dark:bg-gray-900/50 hover:bg-accent-coral/10 dark:hover:bg-accent-coral/20 transition-all group border border-gray-200 dark:border-gray-700 hover:border-accent-coral">
<div class="flex items-center justify-center size-12 rounded-lg bg-accent-coral/10 text-accent-coral dark:bg-accent-coral/20 mr-4 group-hover:scale-110 transition-transform">
<span class="material-symbols-outlined text-2xl">local_atm</span>
</div>
<div class="flex-1">
<p class="text-base font-bold text-[#0f171a] dark:text-white">Realizar Retiro</p>
<p class="text-sm text-gray-500 dark:text-gray-400">Retira dinero de tu cuenta</p>
</div>
<span class="material-symbols-outlined text-gray-400 group-hover:text-accent-coral transition-colors">chevron_right</span>
</a>

<!-- Realizar Transferencia -->
<a href="${pageContext.request.contextPath}/transferencia" class="flex items-center p-5 rounded-lg bg-gray-50 dark:bg-gray-900/50 hover:bg-accent-purple/10 dark:hover:bg-accent-purple/20 transition-all group border border-gray-200 dark:border-gray-700 hover:border-accent-purple">
<div class="flex items-center justify-center size-12 rounded-lg bg-accent-purple/10 text-accent-purple dark:bg-accent-purple/20 mr-4 group-hover:scale-110 transition-transform">
<span class="material-symbols-outlined text-2xl">swap_horiz</span>
</div>
<div class="flex-1">
<p class="text-base font-bold text-[#0f171a] dark:text-white">Realizar Transferencia</p>
<p class="text-sm text-gray-500 dark:text-gray-400">Envía dinero a otras cuentas</p>
</div>
<span class="material-symbols-outlined text-gray-400 group-hover:text-accent-purple transition-colors">chevron_right</span>
</a>
</div>
</div>

<!-- Information Card -->
<div class="p-6 bg-gradient-to-br from-primary/10 to-accent-purple/10 dark:from-primary/20 dark:to-accent-purple/20 rounded-xl shadow-lg border border-primary/20">
<div class="flex items-start gap-4">
<div class="flex items-center justify-center size-12 rounded-lg bg-primary/20 text-primary">
<span class="material-symbols-outlined text-2xl">info</span>
</div>
<div class="flex-1">
<h3 class="text-[#0f171a] dark:text-white text-lg font-bold mb-2">Información Importante</h3>
<p class="text-gray-600 dark:text-gray-300 text-sm leading-relaxed">
Todas tus operaciones están protegidas con encriptación de nivel bancario. 
Puedes realizar depósitos, retiros y transferencias de forma segura las 24 horas del día.
</p>
</div>
</div>
</div>
</div>

<!-- Right Sidebar: Quick Stats -->
<aside class="lg:col-span-4 flex flex-col gap-6">
<div class="p-6 bg-white dark:bg-gray-800/50 rounded-xl shadow-lg">
<h2 class="text-[#0f171a] dark:text-white text-xl font-bold leading-tight tracking-[-0.015em] mb-6">Accesos Rápidos</h2>
<div class="flex flex-col gap-3">
<a href="${pageContext.request.contextPath}/movimientos" class="flex items-center gap-3 p-4 rounded-lg bg-gray-50 dark:bg-gray-900/50 hover:bg-primary/10 dark:hover:bg-primary/20 transition-all group">
<div class="flex items-center justify-center size-10 rounded-lg bg-primary/10 text-primary">
<span class="material-symbols-outlined">history</span>
</div>
<div class="flex-1">
<p class="text-sm font-semibold text-[#0f171a] dark:text-white">Ver Movimientos</p>
</div>
</a>
<a href="${pageContext.request.contextPath}/deposito" class="flex items-center gap-3 p-4 rounded-lg bg-gray-50 dark:bg-gray-900/50 hover:bg-accent-yellow/10 dark:hover:bg-accent-yellow/20 transition-all group">
<div class="flex items-center justify-center size-10 rounded-lg bg-accent-yellow/10 text-accent-yellow">
<span class="material-symbols-outlined">account_balance_wallet</span>
</div>
<div class="flex-1">
<p class="text-sm font-semibold text-[#0f171a] dark:text-white">Depositar</p>
</div>
</a>
<a href="${pageContext.request.contextPath}/retiro" class="flex items-center gap-3 p-4 rounded-lg bg-gray-50 dark:bg-gray-900/50 hover:bg-accent-coral/10 dark:hover:bg-accent-coral/20 transition-all group">
<div class="flex items-center justify-center size-10 rounded-lg bg-accent-coral/10 text-accent-coral">
<span class="material-symbols-outlined">local_atm</span>
</div>
<div class="flex-1">
<p class="text-sm font-semibold text-[#0f171a] dark:text-white">Retirar</p>
</div>
</a>
<a href="${pageContext.request.contextPath}/transferencia" class="flex items-center gap-3 p-4 rounded-lg bg-gray-50 dark:bg-gray-900/50 hover:bg-accent-purple/10 dark:hover:bg-accent-purple/20 transition-all group">
<div class="flex items-center justify-center size-10 rounded-lg bg-accent-purple/10 text-accent-purple">
<span class="material-symbols-outlined">swap_horiz</span>
</div>
<div class="flex-1">
<p class="text-sm font-semibold text-[#0f171a] dark:text-white">Transferir</p>
</div>
</a>
</div>
</div>

<!-- Security Card -->
<div class="p-6 bg-white dark:bg-gray-800/50 rounded-xl shadow-lg border-l-4 border-primary">
<div class="flex items-start gap-3">
<div class="flex items-center justify-center size-10 rounded-lg bg-primary/10 text-primary">
<span class="material-symbols-outlined">security</span>
</div>
<div>
<h3 class="text-[#0f171a] dark:text-white text-base font-bold mb-1">Seguridad</h3>
<p class="text-gray-600 dark:text-gray-300 text-xs leading-relaxed">
Tu sesión está protegida. Recuerda cerrar sesión cuando termines.
</p>
</div>
</div>
</div>
</aside>
</div>
</main>
</div>
</div>

<!-- Modal Ayuda -->
<div id="ayudaModal" class="hidden fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center p-4">
<div class="bg-white dark:bg-gray-800 rounded-xl shadow-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
<div class="p-6 border-b border-gray-200 dark:border-gray-700 flex items-center justify-between">
<h3 class="text-2xl font-bold text-[#0f171a] dark:text-white flex items-center gap-3">
<span class="material-symbols-outlined text-primary">help</span>
Centro de Ayuda
</h3>
<button onclick="closeAyudaModal()" class="text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
<span class="material-symbols-outlined">close</span>
</button>
</div>
<div class="p-6 space-y-6">
<div>
<h4 class="text-lg font-bold text-[#0f171a] dark:text-white mb-3 flex items-center gap-2">
<span class="material-symbols-outlined text-primary text-xl">info</span>
Información del Sistema
</h4>
<p class="text-gray-600 dark:text-gray-300 leading-relaxed">
EurekaBank es una plataforma bancaria segura que te permite gestionar tus finanzas de manera eficiente. 
Puedes realizar depósitos, retiros, transferencias y consultar el historial de tus movimientos.
</p>
</div>
<div>
<h4 class="text-lg font-bold text-[#0f171a] dark:text-white mb-3 flex items-center gap-2">
<span class="material-symbols-outlined text-primary text-xl">account_balance</span>
Operaciones Disponibles
</h4>
<ul class="space-y-2 text-gray-600 dark:text-gray-300">
<li class="flex items-start gap-2">
<span class="material-symbols-outlined text-accent-yellow text-sm mt-1">check_circle</span>
<span><strong>Consultar Historial:</strong> Revisa todos los movimientos de tus cuentas</span>
</li>
<li class="flex items-start gap-2">
<span class="material-symbols-outlined text-accent-yellow text-sm mt-1">check_circle</span>
<span><strong>Realizar Depósito:</strong> Agrega fondos a tu cuenta bancaria</span>
</li>
<li class="flex items-start gap-2">
<span class="material-symbols-outlined text-accent-yellow text-sm mt-1">check_circle</span>
<span><strong>Realizar Retiro:</strong> Retira dinero de tu cuenta</span>
</li>
<li class="flex items-start gap-2">
<span class="material-symbols-outlined text-accent-yellow text-sm mt-1">check_circle</span>
<span><strong>Realizar Transferencia:</strong> Envía dinero a otras cuentas</span>
</li>
</ul>
</div>
<div>
<h4 class="text-lg font-bold text-[#0f171a] dark:text-white mb-3 flex items-center gap-2">
<span class="material-symbols-outlined text-primary text-xl">security</span>
Seguridad
</h4>
<p class="text-gray-600 dark:text-gray-300 leading-relaxed">
Todas tus transacciones están protegidas con encriptación de nivel bancario. 
Recuerda mantener tu sesión segura y cerrar sesión cuando termines de usar el sistema.
</p>
</div>
<div>
<h4 class="text-lg font-bold text-[#0f171a] dark:text-white mb-3 flex items-center gap-2">
<span class="material-symbols-outlined text-primary text-xl">support_agent</span>
Soporte
</h4>
<p class="text-gray-600 dark:text-gray-300 leading-relaxed">
Si necesitas ayuda adicional, contacta con nuestro equipo de soporte. 
Estamos disponibles las 24 horas del día para asistirte.
</p>
</div>
</div>
<div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-end">
<button onclick="closeAyudaModal()" class="px-6 py-2 bg-primary text-white rounded-lg hover:bg-primary/90 transition-colors font-medium">
Cerrar
</button>
</div>
</div>
</div>

<!-- Modal Salir -->
<div id="salirModal" class="hidden fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center p-4">
<div class="bg-white dark:bg-gray-800 rounded-xl shadow-2xl max-w-md w-full">
<div class="p-6 border-b border-gray-200 dark:border-gray-700 flex items-center justify-between">
<h3 class="text-2xl font-bold text-[#0f171a] dark:text-white flex items-center gap-3">
<span class="material-symbols-outlined text-accent-coral">logout</span>
Cerrar Sesión
</h3>
<button onclick="closeSalirModal()" class="text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
<span class="material-symbols-outlined">close</span>
</button>
</div>
<div class="p-6">
<div class="flex items-start gap-4 mb-6">
<div class="flex items-center justify-center size-12 rounded-full bg-accent-coral/10 text-accent-coral">
<span class="material-symbols-outlined text-2xl">warning</span>
</div>
<div>
<p class="text-gray-600 dark:text-gray-300 leading-relaxed">
¿Estás seguro de que deseas cerrar sesión? 
Tendrás que iniciar sesión nuevamente para acceder a tu cuenta.
</p>
</div>
</div>
</div>
<div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-end gap-3">
<button onclick="closeSalirModal()" class="px-6 py-2 bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200 rounded-lg hover:bg-gray-300 dark:hover:bg-gray-600 transition-colors font-medium">
Cancelar
</button>
<a href="${pageContext.request.contextPath}/logout" class="px-6 py-2 bg-accent-coral text-white rounded-lg hover:bg-accent-coral/90 transition-colors font-medium">
Confirmar Salida
</a>
</div>
</div>
</div>

<script>
function openAyudaModal() {
    document.getElementById('ayudaModal').classList.remove('hidden');
    document.body.style.overflow = 'hidden';
}

function closeAyudaModal() {
    document.getElementById('ayudaModal').classList.add('hidden');
    document.body.style.overflow = 'auto';
}

function openSalirModal() {
    document.getElementById('salirModal').classList.remove('hidden');
    document.body.style.overflow = 'hidden';
}

function closeSalirModal() {
    document.getElementById('salirModal').classList.add('hidden');
    document.body.style.overflow = 'auto';
}

// Cerrar modales al hacer clic fuera
document.getElementById('ayudaModal').addEventListener('click', function(e) {
    if (e.target === this) {
        closeAyudaModal();
    }
});

document.getElementById('salirModal').addEventListener('click', function(e) {
    if (e.target === this) {
        closeSalirModal();
    }
});

// Cerrar modales con ESC
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        closeAyudaModal();
        closeSalirModal();
    }
});
</script>
</body>
</html>
