package kr.foundcake.private_question.entity

import jakarta.persistence.*

@Entity
@Table(name = "writers")
open class Writer(
	@Id
	@Column(nullable = false)
	open var channel: Long,

	@Column(nullable = false)
	open var user: String
) {
	constructor() : this(-1, "")
}