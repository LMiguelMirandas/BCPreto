Feature: Inicio

  Scenario Outline: ingresar datos iniciales
    Given iniciar chrome browser
    When iniciar web requerida
    Then Ingresamos información de destino <varDestino> ruta <varRuta> seleccionar Tren <varTren> y solo ida <varSoloIda> fecha de salida <varFecSalida> fecha de retorno <varFecRegreso>
    And selecciona boton Buscar
    And Seleccioanar Boletos
    And Selecciona boton Continuar
    Then Ingresar Información de Pasajeros
    And Selecciona boton ContinuarPago
    And Validar monto de pago
    And Cerrar browser


  Examples:
    |varSoloIda|varDestino|varRuta|varTren|varFecSalida|varFecRegreso|
  # |  "Ignore"   |"Cusco"|"Puno > Cusco"|"PeruRail Titicaca Train"|"09/12/2021"|"Ignore"|
  ## |  "Ignore"   |"Cusco"|"Puno > Cusco"|"Andean Explorer, A Belmond Train"|"08/12/2021"|"Ignore"|
   ## |   "Ignore"    |"Cusco"|"Arequipa > Puno > Cusco"|"Ignore"|"27/11/2021"|"Ignore"|
    |  "Ignore"     |"Machu Picchu"|"Cusco > Machu Picchu"|"Ignore"|"29/11/2021"|"29/11/2021"|
##    |"SI"|"Machu Picchu"|"Cusco > Machu Picchu"|"Ignore"|"27/11/2021"|"Ignore"|

