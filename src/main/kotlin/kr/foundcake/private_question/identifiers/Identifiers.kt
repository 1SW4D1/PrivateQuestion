package kr.foundcake.private_question.identifiers

enum class Identifiers(val id: String) {
	QUESTION_MODAL("private-question-modal"),
	QUESTION_MODAL_INPUT_TITLE("title"),
	QUESTION_MODAL_INPUT_CONTENT("content");

	infix fun not(id: String) : Boolean = this.id != id
}