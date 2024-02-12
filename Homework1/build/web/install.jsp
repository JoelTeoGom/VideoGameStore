<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Database SQL Load</title>
</head>
<style>
    .error {
        color: red;
    }
    pre {
        color: green;
    }
</style>
<body>
    <h2>Database SQL Load</h2>
    <%
        /* How to customize:
         * 1. Update the database name on dbname.
         * 2. Create the list of tables, under tablenames[].
         * 3. Create the list of table definition, under tables[].
         * 4. Create the data into the above table, under data[]. 
         * 
         * If there is any problem, it will exit at the very first error.
         */
        String dbname = "homework1";
        String schema = "ROOT";
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        /* this will generate database if not exist */
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbname, "root", "root");
        Statement stmt = con.createStatement();
        
        String ADRESS = "1234 Main St, City, Country"; // Dirección de la tienda donde se puede obtener el juego

        String data[] = new String[]{
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN, 'grand theft auto v', 'PS4', 'AVENTURA', 'Juego de acción-aventura de mundo abierto', 'yes', 6.99, '/resources/img/gta5.jpg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN, 'Minecraft', 'Xbox Series X', 'AVENTURA', 'Juego de construcción sandbox', 'yes', 8.99, '/resources/img/minecraft.jpg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN, 'Cars', 'Nintendo Switch', 'CARRERES', 'Carrera de alta velocidad', 'yes', 7.49, '/resources/img/cars.jpeg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN, 'it takes two', 'PC', 'PUZZLE', 'Desafía tu cerebro con rompecabezas', 'yes', 5.99, '/resources/img/ittakestwo.jpeg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN, 'League of legends', 'PC', 'MMORPG', 'Conquista la galaxia con otros jugadores', 'yes', 9.99, '/resources/img/leagueoflegends.jpg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN, 'Fortnite', 'Xbox Series X', 'ACCION', 'Compite en una arena deportiva futurista', 'yes', 7.99, '/resources/img/fortnite.jpg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN, 'Just Dance', 'Nintendo Switch', 'BAILE', 'Mueve el cuerpo al ritmo y muestra tus habilidades de baile', 'yes', 6.49, '/resources/img/justdance.jpeg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN, 'Assasins creed', 'PS4', 'AVENTURA', 'Crea y compón tu propio viaje musical', 'yes', 8.99, '/resources/img/assassins.jpg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN , 'Rabbits Party ', 'Xbox Series X', 'PARTY', 'Juego de fiesta con minijuegos y desafíos', 'yes', 7.99, '/resources/img/rabbits.jpg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".GAME (ID, NAME, CONSOLE, TYPE, DESCRIPTION, AVAILABILITY, WEEKLYRENTALPRICE, IMAGEURL, ADRESS) VALUES (NEXT VALUE FOR GAME_GEN , 'Overwatch 2', 'Xbox Series X', 'ACCION', 'Aprende divirtiéndote con misiones educativas', 'yes', 6.99, '/resources/img/overwatch.jpg', '" + ADRESS + "')",
           "INSERT INTO " + schema + ".CREDENTIALS (ID, USERNAME, PASSWORD) VALUES (NEXT VALUE FOR CREDENTIALS_GEN, 'sob', 'sob')"
        };






        for (String datum : data) {
            if (stmt.executeUpdate(datum) <= 0) {
                out.println("<span class='error'>Error inserting data: " + datum + "</span>");
            } else {
                out.println("<pre> -> " + datum + "<pre>");
            }
        }
    %>
    <button onclick="window.location='<%=request.getSession().getServletContext().getContextPath()%>'">Go home</button>
</body>
</html>
