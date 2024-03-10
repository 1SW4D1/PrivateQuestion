package kr.foundcake.private_question.handler

import dev.minn.jda.ktx.events.listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import kotlin.time.Duration.Companion.seconds

fun modalHandler(jda: JDA) {
	jda.listener<ModalInteractionEvent>(2.seconds) {
		if(it.modalId != "private-question") {
			return@listener
		}
		it.reply("질문이 추가되었습니다.").queue()
	}
}