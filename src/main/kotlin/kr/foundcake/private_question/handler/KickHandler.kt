package kr.foundcake.private_question.handler

import dev.minn.jda.ktx.events.listener
import kr.foundcake.private_question.database.DBManager
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.guild.GuildBanEvent
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent

fun handleKickHandler(jda: JDA) {
	jda.listener<GuildLeaveEvent> {
		DBManager.ServerSettingRepo.remove(it.guild.idLong)
		DBManager.WriterRepo.removeByServerId(it.guild.idLong)
	}

	jda.listener<GuildBanEvent> {
		DBManager.ServerSettingRepo.remove(it.guild.idLong)
		DBManager.WriterRepo.removeByServerId(it.guild.idLong)
	}
}