
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalles del Juego</title>

    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: "metropolis", Arial, Helvetica, sans-serif;
        }

        #details-container {
            display: grid;
            grid-template-columns: 1fr 1fr; /* Divide en dos columnas con ancho igual */
            gap: 20px; /* Espacio entre las columnas */
            width: 80%;
            max-width: 1200px; /* Establece un ancho máximo para el contenedor principal */
            margin: 0 auto; /* Centra el contenedor en la página */
        }

        #left-container {
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
        }

        #right-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            width: 100%; /* Ocupa todo el ancho del contenedor */
        }

        #right-container-child {
            width: 80%; /* Ajusta el ancho del contenedor hijo según sea necesario */
            max-width: 600px; /* Establece un ancho máximo para el contenedor hijo */
            padding: 20px; /* Añade espacio interno alrededor del contenido */
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }

        #right-container-child h2 {
            font-size: 2.5em; /* Aumenta el tamaño del texto del encabezado */
            margin-bottom: 15px; /* Añade espacio debajo del encabezado */
        }

        #right-container-child p {
            font-size: 1.2em; /* Aumenta el tamaño del texto de los párrafos */
            margin: 10px 0; /* Añade espacio vertical entre párrafos */
        }

        img {
            width: 100%; /* La imagen ocupa el 100% del contenedor */
            max-width: 500px; /* Reduce el ancho máximo para hacerla un poco más pequeña */
            height: auto;
            margin-bottom: 20px; /* Añade espacio bajo la imagen */
        }

        button {
            margin-top: 20px; /* Añade espacio encima del botón */
            padding: 15px 30px; /* Ajusta el padding del botón */
            font-size: 1.5em; /* Aumenta el tamaño del texto del botón */
            background-color: #4285f4; /* Color de fondo azul (puedes cambiarlo según tu preferencia) */
            color: #ffffff; /* Color de texto blanco */
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease-in-out;
        }

        button:hover {
            background-color: #3c76e4; /* Cambia el color de fondo al pasar el ratón */
        }
        
        .success-message {
            color: #008000; /* Color verde para mensajes de éxito */
        }

        /* Estilo para mensajes de error */
        .error-message {
            color: #ff0000; /* Color rojo para mensajes de error */
        }
    </style>
</head>
<body>                <!-- Mostrar mensaje de éxito o error -->
    <c:if test="${not empty message}">
        <div>${message.text}</div>
    </c:if>
    <div id="details-container">
        <div id="left-container"> 
            <h1>Detalles del Juego</h1>
            <img src="<c:url value="${gameDetails.imageURL}" />" alt="" />
            <p>Description: ${gameDetails.description} </p>
        </div>
        <div id="right-container">
            <div id="right-container-child">
                <h2>${gameDetails.name} - ${gameDetails.console}</h2>
                <p>Disponibilidad: ${gameDetails.availability}</p>
                <p>Prize: ${gameDetails.weeklyRentalPrice}€ (IVA included)</p>
                <p>Tipo: ${gameDetails.type}</p>
                <p>Consola: ${gameDetails.console}</p>
                <p>Tiendas: ${gameDetails.adress}



                <button onclick="addToCart()">Add to Cart</button>
            </div>
        </div>
    </div>

    <script>
        function addToCart() {
            var form = document.createElement("form");
            form.method = "POST";
            form.action = "${mvc.uri('carrito/add')}";

            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "videoGameName";
            input.value = "${gameDetails.name}";
            form.appendChild(input);

            document.body.appendChild(form);
            form.submit();
        }
    </script>
</body>
</html>
