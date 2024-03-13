package kr.foundcake.private_question.extension

import kr.foundcake.private_question.identifiers.Identifiers
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.interactions.modals.ModalMapping

fun ModalInteractionEvent.getValue(identifier: Identifiers): ModalMapping {
	return this.getValue(identifier.id)!!
}