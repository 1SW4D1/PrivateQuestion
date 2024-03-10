package kr.foundcake.private_question.database

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.EntityTransaction
import jakarta.persistence.Persistence
import kr.foundcake.private_question.entity.ServerSetting
import org.hibernate.cfg.AvailableSettings

object DBManager {

	private val emf: EntityManagerFactory

	private val em: EntityManager

	private val tx: EntityTransaction

	init {
		val name = System.getenv("DB_NAME")
		val pw = System.getenv("DB_PASSWORD")

		emf = Persistence.createEntityManagerFactory("kr.foundcake.private_question.jpa", mutableMapOf<String, String>(
			AvailableSettings.JAKARTA_JDBC_URL to "jdbc:mysql://mysql:3306/${name}",
			AvailableSettings.JAKARTA_JDBC_PASSWORD to pw
		))
		em = emf.createEntityManager()
		tx = em.transaction
	}

	fun init() {}

	fun close() {
		em.close()
		emf.close()
	}

	private inline fun commit(block: () -> Unit) {
		tx.begin()
		block()
		tx.commit()
	}

	object SeverSettingRepo {
		fun find(serverId: Long) : ServerSetting{
			var result: ServerSetting? = null
			commit {
				result = em.find(ServerSetting::class.java, serverId)
			}
			return result!!
		}

		fun save(setting: ServerSetting) {
			commit {
				try {
					em.find(ServerSetting::class.java, setting.serverId)
				}catch (e: Exception) {
					em.persist(setting)
					return@commit
				}
				em.merge(setting)
			}
		}
	}
}