package kr.foundcake.private_question.handler

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.generics.getChannel
import kr.foundcake.private_question.database.DBManager
import kr.foundcake.private_question.dto.ServerSetting
import kr.foundcake.private_question.dto.Writer
import kr.foundcake.private_question.extension.getValue
import kr.foundcake.private_question.identifiers.Identifiers
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.utils.messages.MessageCreateData

fun handleModal(jda: JDA) {
	jda.listener<ModalInteractionEvent> {
		if(Identifiers.QUESTION_MODAL not it.modalId) {
			return@listener
		}
		val setting: ServerSetting? = DBManager.ServerSettingRepo.find(it.guild!!.idLong)
		if(setting === null) {
			it.reply("설정이 안되어 있습니다. 서버 관리자에게 문의하십쇼.")
				.setEphemeral(true)
				.queue()
			return@listener
		}

		val channel: GuildChannel? = jda.getChannel(setting.channel)
		if(channel !is ForumChannel) {
			it.reply("기존 채널이 삭제되었습니다. 서버 관리자에게 문의하십쇼")
				.setEphemeral(true)
				.queue()
			DBManager.ServerSettingRepo.remove(setting)
			return@listener
		}

		val post = channel.createForumPost(
			it.getValue(Identifiers.QUESTION_MODAL_INPUT_TITLE).asString,
			MessageCreateData.fromContent(it.getValue(Identifiers.QUESTION_MODAL_INPUT_CONTENT).asString)
		).await()

		DBManager.WriterRepo.save(Writer(
			post.threadChannel.idLong,
			it.user.asMention,
			it.guild!!.idLong
		))

		it.reply("질문이 추가되었습니다.")
			.setEphemeral(true)
			.queue()
	}
}
