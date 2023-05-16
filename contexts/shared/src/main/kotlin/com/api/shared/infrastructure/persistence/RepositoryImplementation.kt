package com.api.shared.infrastructure.persistence

import com.api.shared.domain.AggregateRoot
import com.api.shared.infrastructure.exceptions.BadRequestException
import com.api.shared.infrastructure.exceptions.NotFoundException


abstract class RepositoryImplementation<T : AggregateRoot>(private val entity: String) : Repository<T> {
    private var entities = mutableMapOf<String, T>()

    private val formattedEntity = this.entity.split('.').last()

    override fun save(aggregateRoot: T) {
        when (exists(aggregateRoot.id)) {
            false -> entities[aggregateRoot.id] = aggregateRoot
            else -> throw BadRequestException("A record with id ${aggregateRoot.id} from $formattedEntity already exists")
        }
    }

    private fun exists(id: String): Boolean {
        entities[id].let {
            return it is T
        }
    }

    override fun getAll(): List<T> {
        val list = mutableListOf<T>()
        entities.forEach { list.add(it.value) }
        return list
    }

    override fun get(id: String): T {
        entities[id].let {
            if (it is T) return it
            throw NotFoundException("$formattedEntity id $id not found")
        }
    }

    override fun update(aggregateRoot: T) {
        when (exists(aggregateRoot.id)) {
            true -> entities[aggregateRoot.id] = aggregateRoot
            else -> throw throw NotFoundException("$aggregateRoot with ${aggregateRoot.id} not found")
        }
    }

    override fun delete(id: String) {
        entities.remove(id).let {
            if (it === null) throw NotFoundException("$formattedEntity with $id not found")
        }
    }

    fun reset() {
        entities = mutableMapOf()
    }
}