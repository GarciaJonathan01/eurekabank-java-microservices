<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <title>EurekaBank - Iniciar Sesión</title>
    <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@400;500;600;700;800&display=swap" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet"/>
    <script>
        tailwind.config = {
            darkMode: "class",
            theme: {
                extend: {
                    colors: {
                        "primary": "#3ab4d9",
                        "background-light": "#f6f7f8",
                        "background-dark": "#121d20",
                        "accent-coral": "#f67e80",
                        "accent-yellow": "#f6de88",
                        "accent-purple": "#9e7cc5",
                        "accent-light-blue": "#afe0f8"
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
<body class="font-display">
<div class="relative flex min-h-screen w-full flex-col bg-background-light dark:bg-background-dark group/design-root overflow-hidden">
<div class="flex flex-1 w-full">
<!-- Left Decorative Panel -->
<div class="hidden lg:flex w-1/2 bg-accent-light-blue p-10 relative items-center justify-center overflow-hidden">
<div class="absolute -top-20 -left-20 w-72 h-72 bg-accent-coral/50 rounded-full"></div>
<div class="absolute -bottom-24 right-4 w-96 h-96 bg-accent-purple/40 rounded-full"></div>
<div class="absolute bottom-1/4 -left-28 w-60 h-60 bg-accent-yellow/50 rounded-full"></div>
<div class="relative z-10 flex flex-col items-start text-left max-w-md">
<div class="flex items-center gap-3 mb-6">
<img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="EurekaBank Logo" class="h-12 w-12"/>
<span class="text-3xl font-bold text-[#212121]">EurekaBank</span>
</div>
<h2 class="text-4xl font-bold text-[#212121] leading-tight mb-4">Tu banca, más cerca que nunca.</h2>
<p class="text-lg text-gray-700">Gestiona tus finanzas de forma segura, rápida y sencilla desde cualquier lugar.</p>
</div>
</div>
<!-- Right Login Form Panel -->
<div class="w-full lg:w-1/2 flex items-center justify-center p-6 sm:p-12">
<div class="flex flex-col max-w-md w-full gap-6">
<!-- Logo for mobile -->
<div class="lg:hidden flex justify-center mb-6">
<div class="flex items-center gap-3">
<img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="EurekaBank Logo" class="h-10 w-10"/>
<span class="text-2xl font-bold text-gray-800 dark:text-gray-200">EurekaBank</span>
</div>
</div>
<!-- Page Heading -->
<div class="flex flex-col gap-2">
<p class="text-[#212121] dark:text-white text-4xl font-black leading-tight tracking-[-0.033em]">Bienvenido de vuelta</p>
<p class="text-gray-500 dark:text-gray-400 text-base font-normal leading-normal">Ingresa a tu cuenta para gestionar tus finanzas.</p>
</div>
<% if (request.getAttribute("error") != null) { %>
<div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
<span class="block sm:inline">${error}</span>
</div>
<% } %>
<!-- Form -->
<form method="POST" action="${pageContext.request.contextPath}/login" class="flex flex-col gap-4" accept-charset="UTF-8">
<!-- Username Field -->
<label class="flex flex-col w-full">
<p class="text-[#212121] dark:text-gray-200 text-base font-medium leading-normal pb-2">Usuario</p>
<div class="relative flex w-full items-center">
<span class="material-symbols-outlined absolute left-4 text-gray-400">person</span>
<input name="usuario" class="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-lg text-gray-800 dark:text-white focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 focus:border-primary h-14 placeholder:text-gray-400 pl-12 pr-4 py-3 text-base font-normal leading-normal" placeholder="Ingresa tu usuario" value="" required/>
</div>
</label>
<!-- Password Field -->
<label class="flex flex-col w-full">
<p class="text-[#212121] dark:text-gray-200 text-base font-medium leading-normal pb-2">Contraseña</p>
<div class="relative flex w-full items-center">
<span class="material-symbols-outlined absolute left-4 text-gray-400">lock</span>
<input name="password" type="password" class="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-lg text-gray-800 dark:text-white focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 focus:border-primary h-14 placeholder:text-gray-400 pl-12 pr-12 py-3 text-base font-normal leading-normal" placeholder="Ingresa tu contraseña" value="" required/>
<button type="button" class="absolute right-4 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300" onclick="togglePassword()">
<span class="material-symbols-outlined" id="eyeIcon">visibility</span>
</button>
</div>
</label>
<!-- Login Button -->
<button type="submit" class="flex min-w-[84px] w-full cursor-pointer items-center justify-center overflow-hidden rounded-lg h-12 px-5 mt-4 bg-primary text-white text-base font-bold leading-normal tracking-[0.015em] hover:bg-primary/90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary/70 transition-colors duration-200">
<span class="truncate">Iniciar Sesión</span>
    </button>
</form>
</div>
</div>
</div>
</div>
<script>
function togglePassword() {
    const passwordInput = document.querySelector('input[name="password"]');
    const eyeIcon = document.getElementById('eyeIcon');
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        eyeIcon.textContent = 'visibility_off';
    } else {
        passwordInput.type = 'password';
        eyeIcon.textContent = 'visibility';
    }
}
</script>
</body>
</html>

