
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Videojuegos</title>

    <style>
        
        #loginButtonContainer {
            position: fixed;
            top: 10px;
            right: 10px;
        }
        .game-item {
            border: 1px solid #ccc;
            padding: 10px;
            margin: 10px;
            width: 30%;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            transition: transform 0.3s ease-in-out;
        }
        body {
            margin: 0;
            padding: 0;
            position: relative;
        }

        .game-item {
            font-weight: 800;
        }

        .game-item:hover {
            transform: scale(1.1);
        }

        .game-item img {
            width: 100%;
            height: 600px;
            margin-bottom: 10px;
            
        }

        .game-info {
            text-transform: uppercase;
            font-size: 1.5em;
            margin-top: 10px;
            font-weight: bold;
        }

        .availability {
            font-weight: bold;
            margin: 10px 0;
        }

        .checkbox-yes {
            color: green;
        }

        .checkbox-no {
            color: red;
        }
           
        #loginButtonContainer {
            position: fixed;
            top: 10px;
            right: 10px;
        }
 

        .name-console {
            text-transform: uppercase;
            font-weight: bold;
            font-family: 'Metropolis', Arial, Helvetica, sans-serif;
        }
        #headerContainer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            background-color: #ffffff;
        }

        #loginLogoutContainer {
            display: flex;
            gap: 10px;
        }

        .button {
            padding: 8px 16px;
            text-decoration: none;
            color: #fff;
            background-color: #007bff; 
            border-radius: 4px;
            font-weight: bold;
        }

        #welcomeMessage {
            font-size: 1.5em;
            font-weight: bold;
        }


    </style>

    <script>
        function submitForm() {
            document.getElementById('filterForm').submit();
        }
        
        

        function redirectToGameDetails(gameName) {
        // Crear un formulario dinámicamente
            var form = document.createElement("form");
            form.method = "GET";
            form.action = "${mvc.uri('game-details')}";

            // Crear un campo oculto con el nombre del juego
            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "name";
            input.value = gameName;

            // Agregar el campo oculto al formulario
            form.appendChild(input);

            // Agregar el formulario al cuerpo del documento
            document.body.appendChild(form);

            // Enviar el formulario
            form.submit();
        }
        document.addEventListener("DOMContentLoaded", function() {
            

            // Obtener valores seleccionados después de recargar la página
            var typeFilter = "${filtered.typeFilter}";
            var consoleFilter = "${filtered.consoleFilter}";

            // Establecer las opciones seleccionadas en los elementos select
            document.getElementById("typeFilter").value = typeFilter;
            document.getElementById("consoleFilter").value = consoleFilter;
        });

        document.addEventListener("DOMContentLoaded", function() {
            var availabilityElements = document.querySelectorAll('.availability');

            availabilityElements.forEach(function(element) {
                var availability = element.dataset.availability;

                if (availability === 'yes') {
                    element.style.color = 'green';
                    element.innerHTML = '<input type="checkbox" disabled="true" class="checkbox-yes"/> En stock';
                } else {
                    element.style.color = 'red';
                    element.innerHTML = '<input type="checkbox" disabled="true" class="checkbox-no"/> No está en stock';
                }
            });
        });
    </script>
</head>
<body>
    <div class="filtros">
        <div id="headerContainer">
            <h1 id="welcomeMessage">
                <!-- Si hay un usuario logeado, muestra el nombre en un h1 -->
                <c:if test="${not empty sessionScope.usernameCredentials}">
                    Bienvenido, ${sessionScope.usernameCredentials}!
                </c:if>
            </h1>

            <div id="loginLogoutContainer">
               
                <c:choose>
                    <c:when test="${empty sessionScope.usernameCredentials}">
                        <a class="button" href="${mvc.uri('log-in-get')}">Login</a>
                    </c:when>
                    <c:otherwise>
                        <a class="button" href="${mvc.uri('carrito-details')}">Carrito</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <form id="filterForm" action="${mvc.uri('front-page')}" method="post">
            <label for="typeFilter">Filtrar por Tipo:</label>
            <select id="typeFilter" name="typeFilter" onchange="submitForm()">
                <option value="">Todos</option>
                <option value="PLATAFORMA">Plataforma</option>
                <option value="ACCION">Acción</option>
                <option value="AVENTURA">Aventura</option>
                <option value="ESTRATEGIA">Estrategia</option>
                <option value="CARRERES">Carreras</option>
                <option value="PUZZLE">Puzzle</option>
                <option value="MMORPG">MMORPG</option>
                <option value="DEPORTES">Deportes</option>
                <option value="BAILE">Baile</option>
                <option value="MUSICA">Música</option>
                <option value="PARTY">Party</option>
                <option value="EDUCATIVO">Educativo</option>
            </select>

            <label for="consoleFilter">Filtrar por Videoconsola:</label>
            <select id="consoleFilter" name="consoleFilter" onchange="submitForm()">
                <option value="">Todas</option>
                <option value="SUPER_NINTENDO">Super Nintendo</option>
                <option value="GAME_BOY">Game Boy</option>
                <option value="Xbox Series X">Xbox Series X</option>
                <option value="PS4">PS4</option>
                <option value="PS5">PS5</option>
                <option value="Nintendo Switch">Nintendo Switch</option>
            </select>
        </form>

        <script>
            function submitForm() {
                document.getElementById('filterForm').submit();
            }
        </script>
   </div>

    <div id="gamesContainer" style="display: flex; flex-wrap: wrap;">
        <!-- Espacio para mostrar los videojuegos -->
        <c:forEach var="videoGame" items="${videoGames}">
            <div class="game-item">
                <img class="game-image" src="<c:url value="${videoGame.imageURL}" />" alt="" width="200" height="300" data-name="${videoGame.name}" onclick="redirectToGameDetails('${videoGame.name}')">
                <p class="name-console">${videoGame.name} - ${videoGame.console}</p>
                <p class="availability" data-availability="${videoGame.availability}"></p>
                <p>${videoGame.weeklyRentalPrice}€ / Week</p>
            </div>
        </c:forEach>
    </div>


</body>
</html>