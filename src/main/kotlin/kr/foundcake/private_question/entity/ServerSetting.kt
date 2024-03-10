package kr.foundcake.private_question.entity

import jakarta.persistence.*

@Entity
@Table(name = "settings")
open class ServerSetting(
	@Id
	@Column(nullable = false)
	open var serverId: Long,

	@Column(nullable = false)
	open var channel: Long
) {
	constructor() : this(-1, -1)
}