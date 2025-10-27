package com.example.evparcial2.util

object Validadores {

    fun validarNombre(nombre: String): String? {
        return when {
            nombre.isBlank() -> "El nombre es obligatorio"
            nombre.length < 3 -> "Debe haber mínimo 3 caracteres"
            nombre.length > 50 -> "Deben haber máximo 50 caracteres"
            else -> null
        }
    }

    fun validarPrecio(precio: String): String? {
        return when {
            precio.isBlank() -> "El precio es obligatorio"
            precio.toDoubleOrNull() == null -> "Precio inválido"
            precio.toDouble() < Constantes.PRECIO_MINIMO -> "El precio debe ser mayor a 0"
            else -> null
        }
    }

    fun validarStock(stock: String): String? {
        return when {
            stock.isBlank() -> "El stock es obligatorio"
            stock.toIntOrNull() == null -> "Stock inválido"
            stock.toInt() < Constantes.STOCK_MINIMO -> "El stock no puede ser negativo"
            else -> null
        }
    }

    fun validarEmail(email: String): String? {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return when {
            email.isBlank() -> "El email es obligatorio"
            !regex.matches(email) -> "Email inválido"
            else -> null
        }
    }
}