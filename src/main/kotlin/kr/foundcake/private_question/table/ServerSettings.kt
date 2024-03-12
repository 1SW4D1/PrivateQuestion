package kr.foundcake.private_question.table

import org.jetbrains.exposed.sql.Table

object ServerSettings : Table("settings") {

	val serverId = long("serverid")

	val channel = long("channel")

	override val primaryKey = PrimaryKey(serverId)
}