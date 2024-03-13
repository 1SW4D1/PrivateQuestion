package kr.foundcake.private_question

import dev.minn.jda.ktx.interactions.commands.option
import dev.minn.jda.ktx.interactions.commands.restrict
import dev.minn.jda.ktx.interactions.commands.updateCommands
import dev.minn.jda.ktx.jdabuilder.default
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kr.foundcake.private_question.command.Commands
import kr.foundcake.private_question.database.DBManager
import kr.foundcake.private_question.extension.slash
import kr.foundcake.private_question.handler.CommandHandlers
import kr.foundcake.private_question.handler.handleKickHandler
import kr.foundcake.private_question.handler.handleModal
import kr.foundcake.private_question.handler.handleRemoveChannel
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel


fun main() = runBlocking{
	val dbSetup: Deferred<Unit> = async { DBManager.init() }

	val token: String = System.getenv("TOKEN")
	val jda: JDA = default(token, true)

	setupCommand(jda)

	val cmdHandlers = CommandHandlers(jda)
	cmdHandlers.registerChannelSetup()
	cmdHandlers.registerQuestion()
	cmdHandlers.registerCheck()

	handleModal(jda)
	handleRemoveChannel(jda)
	handleKickHandler(jda)

	dbSetup.await()

	jda.awaitReady()
	return@runBlocking
}

fun setupCommand(jda: JDA) {
	jda.updateCommands {
		slash(Commands.CHANNEL_SETUP) {
			restrict(true, Permission.MANAGE_SERVER)
			option<ForumChannel>("포럼채널", "질문이 올라갈 포럼 채널을 선택해주세요", true)
		}
		slash(Commands.CHECK_QUESTIONER) {
			restrict(true, Permission.MANAGE_THREADS)
		}
		slash(Commands.PRIVATE_QUESTION) {
			restrict(true)
		}
	}.queue()
}
