package com.api.shared.infrastructure.persistence


interface Repository<T> {
    fun save(aggregateRoot: T)
    fun get(id: String): T
    fun getAll(): List<T>
    fun delete(id: String)
    fun update(aggregateRoot: T)
}