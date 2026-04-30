# 🥘 Sabor Andino - Delivery App

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-%236750A4.svg?style=for-the-badge&logo=materialdesign&logoColor=white)

**Sabor Andino** es una aplicación móvil moderna desarrollada en Android para la gestión de pedidos de un restaurante de comida tradicional. Ofrece una experiencia de usuario fluida y reactiva, permitiendo a los clientes explorar el menú, gestionar su carrito y finalizar pedidos de forma intuitiva.

---

## ✨ Características Actuales

- 🔐 **Autenticación Dinámica:** Flujo de login que personaliza la experiencia del usuario con su correo.
- 🏠 **Dashboard Principal:** Acceso rápido al menú, perfil y estado del carrito con indicadores en tiempo real.
- 📜 **Menú Digital Completo:** Catálogo de platos típicos con imágenes, precios y badges que muestran la cantidad de cada ítem en el carrito.
- 🍱 **Detalle de Plato:** Visualización profunda de cada opción con gestión de cantidades antes de agregar al pedido.
- 🛒 **Carrito de Compras Reactivo:** Gestión avanzada donde puedes añadir, quitar o eliminar platos, con actualización automática del total mediante `StateFlow`.
- 💳 **Flujo de Pago (Checkout):** Proceso de finalización que permite elegir entre entrega en "Mesa" o "Para llevar".
- ✅ **Confirmación de Orden:** Generación de un número de pedido único y resumen de los detalles de entrega.
- 👤 **Perfil de Usuario:** Espacio para ver los datos del usuario activo y gestionar la sesión.

---

## 🛠️ Stack Tecnológico

- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) con **Material 3**.
- **Arquitectura:** MVVM (Model-View-ViewModel) para una separación clara de responsabilidades.
- **Navegación:** Navigation Compose con rutas estructuradas y paso de parámetros *type-safe*.
- **Gestión de Estado:** `StateFlow` y `ViewModel` para una UI 100% reactiva.

---

## 📁 Estructura del Proyecto

```text
com.tecsup.sabor_andino/
├── data/          # Repositorios (PlatosRepository) y Modelos de datos (Plato)
├── navigation/    # Definición de rutas y NavGraph centralizado
├── screens/       # Pantallas UI (Login, Home, Menu, Detalle, Pedido, Checkout, Confirmación, Perfil)
├── viewmodel/     # Lógica de negocio y estado global (PedidoViewModel)
├── ui/theme/      # Configuración estética (Colores, Tipografía, Tema Material 3)
└── MainActivity.kt # Punto de entrada y configuración de Edge-to-Edge
```

---

## 🚀 Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/ING-VladBill/sabor_andino.git
   ```
2. **Requisitos:**
   - Android Studio Ladybug o superior.
   - JDK 17 o superior.
3. **Ejecutar:**
   Sincroniza con Gradle y lanza la app en tu emulador o dispositivo físico.

---

## 🚧 Roadmap
- [x] Navegación completa entre todas las secciones (Login -> Home -> Menú -> Pedido -> Confirmación).
- [x] Lógica de carrito de compras funcional y reactiva.
- [x] Interfaz moderna basada en Material 3.
- [ ] Integración con Backend (API REST o Firebase).
- [ ] Persistencia de datos local con Room.
- [ ] Implementación de notificaciones sobre el estado del pedido.

---
Desarrollado con amor y empeño por [ING-VladBill](https://github.com/ING-VladBill) y [gllanos09](https://github.com/gllanos09)
