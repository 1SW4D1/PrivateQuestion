package kr.foundcake.private_question.database

import com.mysql.cj.jdbc.Driver
import kr.foundcake.private_question.dto.ServerSetting
import kr.foundcake.private_question.dto.Writer
import kr.foundcake.private_question.table.ServerSettings
import kr.foundcake.private_question.table.Writers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

object DBManager {

	private val logger = LoggerFactory.getLogger(DBManager::class.java)

	init {
		val name = System.getenv("DB_NAME")
		val pw = System.getenv("DB_PASSWORD")

		Database.connect(
			driver = Driver::class.qualifiedName!!,
			url = "jdbc:mysql://mysql:3306/${name}",
			user = "root",
			password = pw
		)

		logger.info("Connected Database")

		transaction {
			SchemaUtils.create(ServerSettings)
			SchemaUtils.create(Writers)
		}
		logger.info("initialize table")
	}

	fun init() {}

	object SeverSettingRepo {
		suspend fun find(serverId: Long): ServerSetting? {
			var setting: ServerSetting? = null
			newSuspendedTransaction {
				val result: ResultRow? = ServerSettings.selectAll().where {
					ServerSettings.serverId eq serverId
				}.limit(1).singleOrNull()
				if (result !== null) {
					setting = ServerSetting(result[ServerSettings.serverId], result[ServerSettings.channel])
				}
			}
			return setting
		}

		suspend fun save(setting: ServerSetting) {
			newSuspendedTransaction {
				val count: Long = ServerSettings.selectAll().where {
					ServerSettings.serverId eq setting.serverId
				}.limit(1).count()
				if (count < 1) {
					ServerSettings.insert {
						it[serverId] = setting.serverId
						it[channel] = setting.channel
					}
				} else {
					ServerSettings.update(where = { ServerSettings.serverId eq setting.serverId }) {
						it[channel] = setting.channel
					}
				}
			}
		}

		suspend fun remove(setting: ServerSetting) {
			newSuspendedTransaction {
				ServerSettings.deleteWhere {
					serverId eq setting.serverId
				}
			}
		}
	}

	object WriterRepo {
		suspend fun find(threadId: Long): Writer? {
			var writer: Writer? = null
			newSuspendedTransaction {
				val result: ResultRow? = Writers.selectAll().where {
					Writers.threadId eq threadId
				}.limit(1).singleOrNull()
				if (result !== null) {
					writer = Writer(result[Writers.threadId], result[Writers.user])
				}
			}
			return writer
		}

		suspend fun save(writer: Writer) {
			newSuspendedTransaction {
				Writers.insert {
					it[threadId] = writer.threadId
					it[user] = writer.user
				}
			}
		}

		suspend fun remove(writer: Writer) {
			newSuspendedTransaction {
				Writers.deleteWhere { threadId eq writer.threadId }
			}
		}
	}
}