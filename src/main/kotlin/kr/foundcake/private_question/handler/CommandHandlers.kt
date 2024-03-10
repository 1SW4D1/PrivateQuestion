package kr.foundcake.private_question.handler

import dev.minn.jda.ktx.interactions.components.TextInput
import kr.foundcake.private_question.command.Commands
import kr.foundcake.private_question.database.DBManager
import kr.foundcake.private_question.entity.ServerSetting
import kr.foundcake.private_question.extension.onCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.api.interactions.modals.Modal

class CommandHandlers(private val jda: JDA) {

	fun registerChannelSetup() {
		jda.onCommand(Commands.CHANNEL_SETUP) {
			val channel: ForumChannel
			try {
				channel = it.getOption("포럼채널")!!.asChannel.asForumChannel()
			} catch (e: IllegalStateException) {
				it.reply("포럼 채널이 아닙니다.").queue()
				return@onCommand
			}
			DBManager.SeverSettingRepo.save(ServerSetting(it.guild!!.idLong, channel.idLong))
			it.reply("설정되었습니다").queue()
		}
	}

	fun registerQuestion() {
		jda.onCommand(Commands.PRIVATE_QUESTION) {
			it.replyModal(
				Modal
				.create(
					"private-question",
					"질문작성",
				)
				.addActionRow(
					TextInput(
					id = "title",
					label = "제목",
					style = TextInputStyle.SHORT,
					required = true,
					requiredLength = IntRange(1, 100)
				)
				)
				.addActionRow(
					TextInput(
					id = "content",
					label = "내용",
					style = TextInputStyle.PARAGRAPH,
					required = true,
					requiredLength = IntRange(1, 2000)
				)
				)
				.build()
			).queue()
		}
	}

	fun registerCheck() {
		jda.onCommand(Commands.CHECK_QUESTIONER) {
			it.reply("${it.channel!!.type}").queue()
		}
	}
}