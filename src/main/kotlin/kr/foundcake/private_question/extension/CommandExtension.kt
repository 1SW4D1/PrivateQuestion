package kr.foundcake.private_question.extension

import dev.minn.jda.ktx.events.onCommand
import dev.minn.jda.ktx.events.CoroutineEventListener
import dev.minn.jda.ktx.interactions.commands.slash
import kr.foundcake.private_question.command.Commands
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction

fun CommandListUpdateAction.slash(command: Commands, builder: SlashCommandData.() -> Unit) {
	this.slash(command.cmdName, command.description, builder)
}

fun JDA.onCommand(command: Commands, consumer: suspend CoroutineEventListener.(GenericCommandInteractionEvent) -> Unit) {
	this.onCommand(command.cmdName, null, consumer)
}