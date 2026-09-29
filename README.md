# Parcial 1 - Aplicación Móvil Karting Team.

## Datos de la Entrega
- Materia: Aplicaciones Móviles
- Institución: Escuela Da Vinci
- Alumna: Micaela Bursese
- Proyecto: Karting Team App

## Descripción del Proyecto
Aplicación Android orientada a la gestión y seguimiento de equipos de Karting. La app permite a los usuarios registrar experiencias de carrera, visualizar métricas clave y calcular promedios de rendimiento de forma dinámica e interactiva haciendo de esta una aplicacion integral.

## Arquitectura y Tecnologías Utilizadas
- Lenguaje: Java
- Vistas y Diseño (UI):
  - `ConstraintLayout` para estructuras de pantalla adaptables.
  - `LinearLayout` generado dinámicamente desde código Java.
  - `ScrollView` para asegurar soporte en diferentes tamaños de pantalla.
- Estructura de Actividades:
  - `MainActivity`: Pantalla principal y menú de navegación.
  - `MetricasActivity`: Registro y cálculo de tiempos/métricas.
  - `ExperienciaActivity`: Cuestionario interactivo y balance de rendimiento.

---

## Funcionalidades Destacadas
- Generación de vistas dinámicas mediante código Java en respuesta a las selecciones del usuario.
- Validación de campos y control de errores (evitando fallos por componentes nulos).
- Interfaz clara y optimizada siguiendo guías de diseño responsivo (`dimens.xml` y `strings.xml`).
