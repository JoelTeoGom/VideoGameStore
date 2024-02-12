<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito de Videojuegos</title>

    <style>
               

        body {
            position: relative;
            display: grid;
            grid-template-rows: auto 1fr; /* Primera fila para el encabezado, segunda fila para el contenido */
            align-items: flex-start;
            min-height: 100vh; 
        }

        .button-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
        }

        .name-welcome {
            font-weight: bold;
            margin-right: auto; /* Empuja el mensaje de bienvenida hacia la izquierda */
            color: #fff; /* Color de texto blanco */
        }

        .button-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .button-container-center {
            margin-left: auto;
            margin-right: auto;
        }

        .button-container-right {
            margin-left: auto;
        }

        .button {
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            color: #fff; /* Color de texto blanco */
        }

        .button-primary {
            background-color: #007bff;
        }

        .button-danger {
            background-color: #dc3545;
        }

        #cartContainer {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); /* Columnas automáticas con un ancho mínimo de 250px */
            gap: 20px;
            padding: 20px;
        }

        .cart-item {
            border: 1px solid #ccc;
            padding: 10px;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }

        .cart-item img {
            width: 100%; 
            max-height: 300px;
            margin-bottom: 10px;
        }

        .remove-button {
            padding: 8px 16px;
            text-decoration: none;
            color: #fff;
            background-color: #dc3545; /* Color rojo */
            border-radius: 4px;
            font-weight: bold; /* Negrita */
            cursor: pointer;
        }

        /* Alinea el formulario de eliminación al centro del contenedor */
        #cartContainer form {
            margin-top: auto;
            margin-bottom: 10px;
            display: flex;
            justify-content: center;
        }/* Estilos específicos para la página del carrito de juegos */
        /* Puedes agregar estilos según sea necesario */

        body {
            position: relative;
            display: grid;
            grid-template-rows: auto 1fr; /* Primera fila para el encabezado, segunda fila para el contenido */
            align-items: flex-start;
            min-height: 100vh; /* Asegura que la altura mínima sea al menos el 100% de la ventana */
        }

        .button-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
        }

        .name-welcome {
            font-weight: bold;
            margin-right: auto; /* Empuja el mensaje de bienvenida hacia la izquierda */
            color: #000; /* Color de texto negro */
        }

        .button-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .button-container-center {
            margin-left: auto;
            margin-right: auto;
        }

        .button-container-right {
            margin-left: auto;
        }

        .button {
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            color: #fff; /* Color de texto blanco */
        }

        .button-primary {
            background-color: #007bff;
        }

        .button-danger {
            background-color: #dc3545;
        }

        #cartContainer {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); /* Columnas automáticas con un ancho mínimo de 250px */
            gap: 20px;
            padding: 20px;
        }

        .cart-item {
            border: 1px solid #ccc;
            padding: 10px;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }

        .cart-item img {
            width: 100%; /* Ajusta la imagen al 100% del contenedor */
            max-height: 300px; /* Ajusta la altura máxima para evitar estirar demasiado la imagen */
            margin-bottom: 10px;
        }

        .remove-button {
            padding: 8px 16px;
            text-decoration: none;
            color: #fff;
            background-color: #dc3545; /* Color rojo */
            border-radius: 4px;
            font-weight: bold; /* Negrita */
            cursor: pointer;
        }

        /* Alinea el formulario de eliminación al centro del contenedor */
        #cartContainer form {
            margin-top: auto;
            margin-bottom: 10px;
            display: flex;
            justify-content: center;
        }

    </style>
</head>
<body>
    

    <div class="button-bar">
        <div class="button-container">
            <h1 class="name-welcome">Bienvenido, ${sessionScope.usernameCredentials}!</h1>
        </div>
        <div class="button-container button-container-center">
            <a href="${mvc.uri('front-page')}" class="button button-primary">Volver al Menú Principal</a>
        </div>
        <div class="button-container button-container-right">
            <a href="${mvc.uri('log-me-out')}" class="button button-danger">Logout</a>
        </div>
    </div>

    
    <div id="cartContainer" style="display: flex; flex-wrap: wrap;">
        <c:if test="${not empty sessionScope.cartItems}">
            <c:forEach var="cartItem" items="${sessionScope.cartItems}">
                <div class="cart-item">
                    <img src="<c:url value="${cartItem.imageURL}" />" alt="" width="200" height="300">
                    <p class="name-console">${cartItem.name} - ${cartItem.console}</p>
                    <p class="price">${cartItem.weeklyRentalPrice}€ / Week</p>
                    <form method="post" action="<c:url value='/Web/Carrito/remove' />" id="removeForm">
                        <input type="hidden" name="videoGameName" value="${cartItem.name}" />
                        <button type="submit" class="remove-button">Remove</button>
                    </form>
                </div>
            </c:forEach>     
        </c:if>
    </div>
    <form method="post" action="<c:url value='/Web/Carrito/carrito/checkout' />">
        <button type="submit" class="button button-primary">Checkout</button>
    </form>

    <p id="precioTotal">Precio total: 0.00€</p>

    <c:if test="${not empty rental}">
      <div style="background-color: beige; text-align: center; padding: 20px; margin: auto; margin-top: 20px; max-width: 600px;">
          <h2>Detalles del alquiler:</h2>
          <p>Rental ID: ${rental.id}</p>
          <p>Rental Date: ${rental.rentalDate}</p>
          <p>Return Date: ${rental.returnDate}</p>
          <p>Total Price: ${String.format('%.2f', rental.totalPrice)} €</p>
      </div>
  </c:if>





    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Función para calcular y mostrar el precio total
            function calcularYMostrarPrecioTotal() {
                var elementosCarrito = document.querySelectorAll('.cart-item');
                var precioTotal = 0;

                elementosCarrito.forEach(function (elemento) {
                    var precioSemanal = parseFloat(elemento.querySelector('.price').textContent.replace('€ / Week', ''));
                    precioTotal += precioSemanal;
                });

                // Muestra el precio total en algún lugar de tu página
                document.getElementById('precioTotal').textContent = 'Precio total: ' + precioTotal.toFixed(2) + '€';
            }

            // Llama a la función al cargar la página
            calcularYMostrarPrecioTotal();
        });
    </script>
    
    

</body>
</html>
