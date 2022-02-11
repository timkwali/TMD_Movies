package com.timkwali.tmdmovies.common.utils

interface DomainMapper<DomainModel, Dto> {

    fun mapToDomain(entity: DomainModel): Dto
}