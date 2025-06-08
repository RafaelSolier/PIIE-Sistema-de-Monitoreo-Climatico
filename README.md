** Endpoints con explicación, los asteriscos dan referencia a que son necesario para el frontend.
Autenticación / Usuarios / Personas

POST /api/auth/signup (Registro de nuevo usuario)

POST /api/auth/signin (Login → JWT)

GET /api/auth/me (Obtener datos del usuario actual)

GET /api/personas/{id} (Obtener datos personales en perfil)

PUT /api/usuarios/{correo} (Cambiar contraseña, etc.)

Gestión de estaciones y nodos

GET /api/estaciones → *Listar todas las estaciones para pintar el mapa

GET /api/estaciones/{id} (Ver detalle de una estación: nombre, dirección, etc.)

GET /api/nodos (Listar todos los nodos)

GET /api/nodos/{id} (Obtener datos de un nodo, p. ej. su estado y parámetro)

GET /api/nodos/estacion/{idEstacion} (Listar nodos dentro de una estación; si en el front quieres prefiltrar)

GET /api/nodos/parametro/{idParametro} (Listar nodos por parámetro; si necesitas ver dónde se mide “Temperatura”)

Parámetros ambientales

GET /api/parametros → *Obtener lista de parámetros para poblar select de “Variable”

GET /api/parametros/{id} (Detalle de un parámetro concreto)

Mediciones (lecturas de sensores)

GET /api/mediciones/nodo/{idNodo}/last → *Obtener los valores “últimos” de cada parámetro de ese nodo

GET /api/mediciones/nodo/{idNodo} → *Listar todas las mediciones de un nodo (para filtrar localmente o debugging)

POST /api/mediciones/filter → *Listado de mediciones filtradas por idNodo + idParametro + fechaInicio + fechaFin (Para el gráfico y descarga)

GET /api/mediciones/{id} (Detalle de una medición en particular, útil para debugging)

POST /api/mediciones (Crear medición; usado por servicios/sensores, no por el front web)

Historial de descargas

POST /api/historiales → *Registrar que un cliente descargó datos de (idNodo, idParametro, fechaInicio, fechaFin)

GET /api/historiales/persona/{idPersona} (Listar todas las descargas que realizó un usuario; útil para menú “Mis descargas”)

GET /api/historiales/{idHistorial} (Ver detalle de descarga específica)

DELETE /api/historiales/{idHistorial} (Administrar/eliminar registros de historial; solo ADMIN)

Alertas y registro de alertas

GET /api/alertas (Listar alertas configuradas; ADMIN)

GET /api/alertas/{idAlerta} (Detalle de alerta; ADMIN)

GET /api/alertas/nodo/{idNodo} (Listar alertas activas de un nodo – opcional en front si se quiere mostrar alertas habilitadas)

POST /api/alertas (Crear/Configurar alerta; ADMIN)

PUT /api/alertas/{idAlerta} (Actualizar alerta; ADMIN)

DELETE /api/alertas/{idAlerta} (Eliminar o desactivar alerta; ADMIN)

GET /api/registro-alertas/alerta/{idAlerta} (Listar todos los eventos disparados por esa alerta)

GET /api/registro-alertas/{idAlerta}/{fecha} (Obtener detalle de un registro de alerta en particular)

POST /api/registro-alertas (Crear manualmente un registro de alerta; ADMIN)
---
** ENDPOINT MÁS RESUMIDOS.
*** Cumplido (TODOS NECESARIOS)
/api/auth/signup             POST    → Registro de usuario (crea Persona + Usuario)
/api/auth/signin             POST    → Login (devuelve JWT)
/api/auth/me                 GET     → Datos de usuario actual (JWT Required)
*** Cumplido (TODOS NECESARIOS)
/api/personas                GET     → Listar Personas
/api/personas/{id}           GET     → Detalle Persona
/api/personas                POST    → Crear Persona
/api/personas/{id}           PUT     → Actualizar Persona
/api/personas/{id}           DELETE  → Eliminar Persona
*** No es necesario
# /api/usuarios/{correo}       PUT     → Actualizar Usuario (p. ej. password)
# /api/usuarios/{correo}       DELETE  → Eliminar Usuario
*** (Rafael-ver)
/api/estaciones              GET     → Listar Estaciones (ESTO ES PARA EL MAPITA)
/api/estaciones/{id}         GET     → Detalle Estación
/api/estaciones              POST    → Crear Estación  (ADMIN)
/api/estaciones/{id}         PATCH     → Actualizar Estación (ADMIN)
/api/estaciones/{id}         DELETE  → Eliminar Estación  (ADMIN)
*** (Rafael-ver)
/api/parametros              GET     → Listar Parámetros
/api/parametros/{id}         GET     → Detalle Parámetro
/api/parametros              POST    → Crear Parámetro  (ADMIN)
/api/parametros/{id}         PATCH     → Actualizar Parámetro (ADMIN)
/api/parametros/{id}         DELETE  → Eliminar Parámetro  (ADMIN)
*** (Rafael-ver)
/api/nodos                   GET     → Listar todos los Nodos
/api/nodos/{id}              GET     → Detalle Nodo
/api/nodos/estacion/{idEst}  GET     → Listar Nodos por Estación
/api/nodos/parametro/{idPar} GET     → Listar Nodos por Parámetro
/api/nodos                   POST    → Crear Nodo  (ADMIN)
/api/nodos/{id}              PUT     → Actualizar Nodo (ADMIN)
/api/nodos/{id}              DELETE  → Eliminar Nodo  (ADMIN)

/api/mediciones/nodo/{idNodo}/last    GET     → Últimas mediciones de un Nodo (por Parámetro)(ESTO PARA MOSTRAR EN LOS CUADRITOS EN TIEMPO REAL)
/api/mediciones/nodo/{idNodo}         GET     → Todas las mediciones de un Nodo
/api/mediciones/{id}                  GET     → Detalle de medición
/api/mediciones/filter                POST    → Filtrar mediciones (idNodo, idParametro, fechaInicio, fechaFin) (ESTO ES PARA LA GRÁFICA Y DESCARGA)
/api/mediciones                       POST    → Registrar medición (externo, no front-web) (VENDRÍA A SER LA MEDICIÓN DEL MÓDULO)

/api/historiales                       GET     → Listar todos los Historiales (ADMIN)
/api/historiales/persona/{idPersona}   GET     → Historial de un usuario (sus descargas)
/api/historiales/{idHistorial}         GET     → Detalle de un Historial
/api/historiales                      POST    → Crear un registro de descarga (CLIENTE)
/api/historiales/{idHistorial}        DELETE  → Eliminar Historial (ADMIN)

/api/alertas                          GET     → Listar todas las Alertas (ADMIN)
/api/alertas/{idAlerta}              GET     → Detalle de Alerta
/api/alertas/nodo/{idNodo}           GET     → Listar Alertas de un Nodo
/api/alertas                         POST    → Crear Alerta (ADMIN)
/api/alertas/{idAlerta}              PUT     → Actualizar Alerta (ADMIN)
/api/alertas/{idAlerta}              DELETE  → Eliminar Alerta (ADMIN)

/api/registro-alertas                 GET     → Listar Registro_Alertas (ADMIN)
/api/registro-alertas/{idAlerta}/{fecha}  GET → Detalle Registro_Alerta (PK compuesta)
/api/registro-alertas/alerta/{idAlerta}   GET → Listar registros de una Alerta dada
/api/registro-alertas                 POST    → Crear Registro_Alerta (ADMIN)
/api/registro-alertas/{idAlerta}/{fecha}  DELETE → Eliminar Registro_Alerta (ADMIN)
