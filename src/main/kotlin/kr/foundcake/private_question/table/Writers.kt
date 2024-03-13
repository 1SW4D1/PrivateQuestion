package kr.foundcake.private_question.table

import org.jetbrains.exposed.sql.Table

object Writers : Table("writers") {

	val threadId = long("threadId")

	val user = varchar("user", 40)

	val serverId = long("serverId")

	override val primaryKey = PrimaryKey(threadId)
}