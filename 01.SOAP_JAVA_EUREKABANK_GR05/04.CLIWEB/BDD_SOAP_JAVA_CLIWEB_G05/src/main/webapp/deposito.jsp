<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <title>EurekaBank - Realizar Depósito</title>
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
                        "background-light": "#afe0f8",
                        "accent-purple": "#9e7cc5",
                    },
                    fontFamily: {
                        "display": ["Manrope", "sans-serif"]
                    },
                },
            },
        }
    </script>
    <style>
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24
        }
    </style>
</head>
<body class="font-display bg-background-light">
<div class="min-h-screen">
<!-- Header -->
<header class="flex items-center justify-between px-6 md:px-10 lg:px-20 py-3 bg-white/50 backdrop-blur-sm border-b border-white/30">
<div class="flex items-center gap-4">
<div class="size-8 text-primary">
<svg fill="currentColor" viewbox="0 0 48 48"><path clip-rule="evenodd" d="M24 18.4228L42 11.475V34.3663C42 34.7796 41.7457 35.1504 41.3601 35.2992L24 42V18.4228Z" fill-rule="evenodd"></path><path clip-rule="evenodd" d="M24 8.18819L33.4123 11.574L24 15.2071L14.5877 11.574L24 8.18819ZM9 15.8487L21 20.4805V37.6263L9 32.9945V15.8487ZM27 37.6263V20.4805L39 15.8487V32.9945L27 37.6263ZM25.354 2.29885C24.4788 1.98402 23.5212 1.98402 22.646 2.29885L4.98454 8.65208C3.7939 9.08038 3 10.2097 3 11.475V34.3663C3 36.0196 4.01719 37.5026 5.55962 38.098L22.9197 44.7987C23.6149 45.0671 24.3851 45.0671 25.0803 44.7987L42.4404 38.098C43.9828 37.5026 45 36.0196 45 34.3663V11.475C45 10.2097 44.2061 9.08038 43.0155 8.65208L25.354 2.29885Z" fill-rule="evenodd"></path></svg>
</div>
<h2 class="text-xl font-bold">EurekaBank</h2>
</div>
<a href="${pageContext.request.contextPath}/menu" class="px-4 py-2 bg-primary text-white rounded-lg hover:bg-primary/90">Volver</a>
</header>
<!-- Main Content -->
<main class="px-6 md:px-10 lg:px-20 py-10 flex justify-center">
<div class="max-w-2xl w-full">
<div class="bg-white rounded-xl shadow-lg p-8">
<div class="flex justify-center mb-6">
<div class="w-20 h-20 rounded-full bg-accent-purple/20 flex items-center justify-center">
<span class="material-symbols-outlined text-5xl text-accent-purple">account_balance_wallet</span>
</div>
</div>
<h1 class="text-3xl font-black text-center mb-2">Realizar Depósito</h1>
<p class="text-gray-500 text-center mb-8">Agrega fondos a tu cuenta bancaria</p>
<% if (request.getAttribute("mensaje") != null) { %>
<div class="mb-6 p-4 rounded-lg ${tipoMensaje == 'success' ? 'bg-green-100 text-green-700 border border-green-400' : 'bg-red-100 text-red-700 border border-red-400'}">
${mensaje}
</div>
<% } %>
<form method="POST" action="${pageContext.request.contextPath}/deposito" class="space-y-6">
<div>
<label class="block text-sm font-medium mb-2">Número de cuenta</label>
<input type="text" name="cuenta" placeholder="Ingrese el número de cuenta" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary" required/>
</div>
<div>
<label class="block text-sm font-medium mb-2">Importe</label>
<input type="text" name="importe" placeholder="Ingrese el importe a depositar" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-primary" required/>
</div>
<button type="submit" class="w-full py-3 bg-accent-purple text-white rounded-lg hover:bg-accent-purple/90 font-bold text-lg">
Realizar Depósito
</button>
</form>
</div>
</div>
</main>
</div>
</body>
</html>

