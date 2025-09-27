# Android01 - Práctica 1: Toma de contacto con Android

Repositorio del proyecto de la **Práctica 1** de la asignatura **PAD (Programación de Aplicaciones para Dispositivos Móviles)** de la **UCM** (opcional).

## Objetivo

El objetivo de esta práctica es familiarizarse con **Android Studio**, crear una **primera aplicación sencilla** y aprender técnicas básicas de **depuración y testing** en Android.

---

## Descripción de la Aplicación

La aplicación desarrollada permite:

- Introducir **dos números** (enteros o decimales) en la pantalla principal (`MainActivity`).
- Pulsar un **botón de suma** para calcular la suma de ambos números.
- Mostrar el resultado en una nueva actividad (`CalculatorAddResultActivity`).

### Funcionalidades destacadas

- Uso de **ConstraintLayout** y **chains** para distribuir los campos de entrada y el botón.
- Soporte de **hint** en los campos de texto para guiar al usuario.
- Layout adaptado a **modo vertical y horizontal**.
- Localización en **español e inglés** para todos los literales de la interfaz.
- Implementación de **clase de lógica de negocio** (`CalculatorAdd`) para realizar la suma.
- Implementación de **pruebas unitarias** (`CalculatorAddUnitTest`) para verificar la correcta suma de enteros y decimales.
- Uso de **logs** en `MainActivity` con distintos niveles (VERBOSE, DEBUG, INFO, WARN, ERROR).
- Implementación de **breakpoints** y depuración con Android Studio.

---

